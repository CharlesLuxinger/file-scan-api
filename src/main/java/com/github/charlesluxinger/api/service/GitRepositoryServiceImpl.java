package com.github.charlesluxinger.api.service;

import com.github.charlesluxinger.api.model.DataByFileType;
import com.github.charlesluxinger.api.model.GitRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.charlesluxinger.api.config.CachingConfig.CACHE_NAME;
import static com.github.charlesluxinger.api.model.RegexPattern.BYTES_QUANTITY_REGEX_OR_K_BYTES_QUANTITY_REGEX;
import static com.github.charlesluxinger.api.model.RegexPattern.DOUBLE_REGEX;
import static com.github.charlesluxinger.api.model.RegexPattern.LINE_QUANTITY_REGEX;
import static com.github.charlesluxinger.api.model.RegexPattern.LINE_REGEX;
import static com.github.charlesluxinger.api.model.RegexPattern.NUMBER_REGEX;
import static com.github.charlesluxinger.api.model.UrlParts.HTTPS_GITHUB_COM;
import static com.github.charlesluxinger.api.model.UrlParts.TREE_PATH;

@Service
@AllArgsConstructor
@Slf4j
public class GitRepositoryServiceImpl implements GitRepositoryService {
	
	private final HtmlPageService htmlPageService;
	private final RegexPatternService regexService;

	@Cacheable(value = CACHE_NAME, key = "#repository.url")
	public Set<DataByFileType> findAllFilesGroup(final GitRepository repository)  {
		log.info("Finding in : " + repository.getUrl());

		isValidUrl(repository.getUrl());

		var treeFiles = Collections.synchronizedSet(new HashSet<String>());

		var filesPath = getPathsFilesByRootDirectory(treeFiles, repository.getUrl());

		var fileDataList = getDataByFilesExtensions(filesPath, repository.getUrl());

		return getFileDataGroup(fileDataList);
	}

	private Set<DataByFileType> getFileDataGroup(final List<DataByFileType> typeList) {
		var fileGroupData = Collections.synchronizedSet(new HashSet<DataByFileType>());

		typeList.forEach(type -> groupByFileExtensions(type, fileGroupData));

		return fileGroupData;
	}

	private void groupByFileExtensions(final DataByFileType type, final Set<DataByFileType> fileGroupData) {
		if (fileGroupData.contains(type)) {
			sumValuesToFileTypeExisted(type, fileGroupData);
		}
		fileGroupData.add(type);
	}

	private void sumValuesToFileTypeExisted(final DataByFileType newData, final Set<DataByFileType> actualData) {
		actualData.parallelStream()
				  .filter(newData::equals)
				  .findFirst()
			      .map(oldData -> updateDataValues(oldData, newData));
	}

	private DataByFileType updateDataValues(DataByFileType oldData, DataByFileType newData) {
		oldData.setBytes(newData.getBytes() + oldData.getBytes());
		oldData.setLines(newData.getLines() + oldData.getLines());
		return oldData;
	}

	private List<DataByFileType> getDataByFilesExtensions(final Set<String> filesPath, final String url) {
		return filesPath.parallelStream()
						.map(url::concat)
						.map(this::getDataByFileType)
						.collect(Collectors.toList());
	}

	private DataByFileType getDataByFileType(final String url) {
		var line = getDataByHTMLPage(url);
		var linesQuantity = getLinesQuantity(line);
		var bytesQuantity = getBytesQuantity(line);

		return DataByFileType.of(url, linesQuantity, bytesQuantity);
	}

	private long getBytesQuantity(final List<String> lines) {
		var bytesQtd = lines.parallelStream()
							.filter(l -> l.contains("Bytes") || l.contains("KB"))
							.findFirst()
							.orElse("");

		var bytesLine = regexService.getMatcher(BYTES_QUANTITY_REGEX_OR_K_BYTES_QUANTITY_REGEX, bytesQtd);

		if (bytesLine.contains("KB")){
			return getBytesFromKBytes(bytesLine);
		}

		return getNumberByLine(bytesLine);
	}

	private long getNumberByLine(final String line) {
		var number = regexService.getMatcher(NUMBER_REGEX, line);

		if (number.isBlank()){
			return 0L;
		}

		return Long.parseLong(number);
	}

	private long getBytesFromKBytes(final String line) {
		int K_BYTE = 1000;

		var doubleString = regexService.getMatcher(DOUBLE_REGEX, line);

		if (doubleString.isBlank()){
			return 0L;
		}

		var value = Double.valueOf(doubleString);
		value = value * K_BYTE;

		return value.longValue();
	}

	private long getLinesQuantity(final List<String> lines) {
		var lineQtd = lines.parallelStream()
						   .filter(l -> l.contains("lines"))
						   .findFirst()
						   .orElse("");

		var lineQuantity = regexService.getMatcher(LINE_QUANTITY_REGEX, lineQtd);

		return getNumberByLine(lineQuantity);
	}

	private List<String> getDataByHTMLPage(final String url) {
		return htmlPageService.getHTMLPageByURL(url)
							  .lines()
							  .parallel()
							  .map(this::findFileDataLineInHtmlPage)
							  .filter(StringUtils::isNotBlank)
							  .collect(Collectors.toList());
	}

	private String findFileDataLineInHtmlPage(final String line) {
		return regexService.getMatcher(LINE_REGEX, line);
	}

	private Set<String> getPathsFilesByRootDirectory(final Set<String> treeFiles, final String url){
		var pathsFromRoot = htmlPageService.getPathsByHTMLPage(url);

		treeFiles.addAll(pathsFromRoot);

		getFilesPathByChildDirectories(treeFiles, url, pathsFromRoot);

		return treeFiles;
	}

	private void getFilesPathByChildDirectories(final Set<String> treeFiles, final String url, Set<String> pathsFromRoot) {
		var userNameAndRepoName = htmlPageService.getPath(url);
		boolean containsTreePath = treeFiles.parallelStream().anyMatch(p -> p.contains(TREE_PATH));

		while (containsTreePath) {
			var childPaths = new HashSet<String>();

			pathsFromRoot.parallelStream()
						 .filter(this::isDirectoryPath)
					     .forEach(actualPage -> getPathsFilesByChildDirectory(actualPage, childPaths, userNameAndRepoName));

			treeFiles.removeIf(this::isDirectoryPath);

			treeFiles.addAll(childPaths);

			pathsFromRoot = treeFiles;
			containsTreePath = pathsFromRoot.parallelStream()
											.anyMatch(p -> p.contains(TREE_PATH));
		}
	}

	private void getPathsFilesByChildDirectory(final String pathChild, final Set<String> actualPaths, final String userNameAndRepoName) {
		var url = HTTPS_GITHUB_COM + userNameAndRepoName + pathChild;

		var pathsByPage = htmlPageService.getPathsByHTMLPage(url);

		if (pathsByPage.isEmpty()) {
			return;
		}

		pathsByPage.removeIf(pathChild::equals);
		pathsByPage.removeIf(pathChild::contains);

		actualPaths.addAll(pathsByPage);
	}

	private boolean isDirectoryPath(@NotBlank final String path) {
		return path.contains(TREE_PATH);
	}

	private void isValidUrl(@NotBlank final String url) {
		if (url.contains(".git")){
			throw new RuntimeException();//TODO Exception
		}
	}

}
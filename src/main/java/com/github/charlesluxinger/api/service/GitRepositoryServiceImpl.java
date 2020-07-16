package com.github.charlesluxinger.api.service;

import com.github.charlesluxinger.api.exception.NotValidURLException;
import com.github.charlesluxinger.api.model.DataByFileType;
import com.github.charlesluxinger.api.model.GitRepository;
import com.github.charlesluxinger.api.util.NumberUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.charlesluxinger.api.config.CachingConfig.CACHE_NAME;
import static com.github.charlesluxinger.api.model.RegexPattern.LINE_REGEX;
import static com.github.charlesluxinger.api.model.UrlParts.HTTPS_GITHUB_COM;
import static com.github.charlesluxinger.api.model.UrlParts.TREE_PATH;
import static java.util.Collections.synchronizedSet;

@Slf4j
@Service
@AllArgsConstructor
@Validated
public class GitRepositoryServiceImpl implements GitRepositoryService {

	private final FilesGroupData filesGroupData;
	private final HtmlPageService htmlPageService;
	private final RegexPatternService regexService;
	private final NumberUtils numberUtils;

	@Valid
	@Cacheable(value = CACHE_NAME, key = "#repository.url")
	public Set<DataByFileType> findAllFilesGroup(@Valid @NotNull final GitRepository repository) {
		log.info("Finding in: " + repository.getUrl());

		isACloneRepositoryUrl(repository.getUrl());

		return getFileDataGroup(repository.getUrl());
	}

	protected void isACloneRepositoryUrl(@NotBlank final String url) {
		if (url.endsWith(".git")) {
			throw new NotValidURLException("Is not a valid github url: " + url);
		}
	}

	protected Set<DataByFileType> getFileDataGroup(final String url) {
		filesGroupData.newFilesGroupDataList();

		return getDataByFilesExtensions(url).stream()
											.map(filesGroupData::addFileGroup)
											.flatMap(Set::stream)
											.collect(Collectors.toSet());
	}

	protected List<DataByFileType> getDataByFilesExtensions(final String url) {
		return getPathsFilesByRootDirectory(synchronizedSet(new HashSet<>()), url)
				.parallelStream()
				.map(url::concat)
				.map(this::getDataByFileType)
				.collect(Collectors.toList());
	}

	protected Set<String> getPathsFilesByRootDirectory(final Set<String> treeFiles, final String url) {
		var pathsFromRoot = htmlPageService.getPathsByHTMLPage(url);

		treeFiles.addAll(pathsFromRoot);

		getFilesPathByChildDirectories(treeFiles, url, pathsFromRoot);

		return treeFiles;
	}

	protected void getFilesPathByChildDirectories(final Set<String> treeFiles, final String url, Set<String> pathsFromRoot) {
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

	protected void getPathsFilesByChildDirectory(final String pathChild, final Set<String> actualPaths, final String userNameAndRepoName) {
		var url = HTTPS_GITHUB_COM + userNameAndRepoName + pathChild;

		var pathsByPage = htmlPageService.getPathsByHTMLPage(url);

		if (pathsByPage.isEmpty()) {
			return;
		}

		pathsByPage.removeIf(pathChild::equals);
		pathsByPage.removeIf(pathChild::contains);

		actualPaths.addAll(pathsByPage);
	}

	protected DataByFileType getDataByFileType(final String url) {
		var line = getDataByHTMLPage(url);
		var linesQuantity = numberUtils.getLinesQuantity(line);
		var bytesQuantity = numberUtils.getBytesQuantity(line);

		return DataByFileType.of(url, linesQuantity, bytesQuantity);
	}

	protected Set<String> getDataByHTMLPage(final String url) {
		return htmlPageService.getHTMLPageByURL(url)
							  .lines()
							  .parallel()
							  .map(this::findFileDataLineInHtmlPage)
							  .filter(StringUtils::isNotBlank)
							  .collect(Collectors.toSet());
	}

	protected String findFileDataLineInHtmlPage(final String line) {
		return regexService.getMatcher(LINE_REGEX, line);
	}

	protected boolean isDirectoryPath(@NotBlank final String path) {
		return path.contains(TREE_PATH);
	}

}
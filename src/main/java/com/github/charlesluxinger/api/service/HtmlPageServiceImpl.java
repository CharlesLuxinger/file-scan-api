package com.github.charlesluxinger.api.service;

import com.github.charlesluxinger.api.exception.NotHTMLPageException;
import com.github.charlesluxinger.api.exception.UnformedURLException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.charlesluxinger.api.model.RegexPattern.PATH_REGEX;
import static com.github.charlesluxinger.api.model.UrlParts.BLOB_PATH;
import static com.github.charlesluxinger.api.model.UrlParts.HTTPS_GITHUB_COM;
import static com.github.charlesluxinger.api.model.UrlParts.TREE_PATH;

@Service
@Validated
@AllArgsConstructor
public class HtmlPageServiceImpl implements HtmlPageService {

	private final RegexPatternService regexService;

	public String getPath(@NotBlank final String url) {
		return getUrl(url).getPath();
	}

	public URL getUrl(@NotBlank final String url) {
		try {
			if (url.contains(HTTPS_GITHUB_COM)) {
				return new URL(url);
			}
			return new URL(HTTPS_GITHUB_COM + url);
		} catch (MalformedURLException e) {
			throw new UnformedURLException("Invalid URL" + url, e);
		}
	}

	public BufferedReader getHTMLPageByURL(@NotBlank final String url) {
		try {
			var conn = getUrl(url).openConnection();
			return new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} catch (IOException e) {
			throw new NotHTMLPageException("Some wrong with url: " + url, e);//TODO Catch Exception
		}
	}

	public Set<String> getPathsByHTMLPage(@NotBlank final String url) {
		return getHTMLPageByURL(url).lines()
									.parallel()
									.map(this::findPathInHtmlLine)
									.filter(StringUtils::isNotBlank)
									.collect(Collectors.toSet());
	}

	public String findPathInHtmlLine(@NotBlank final String line) {
		var patternTree = TREE_PATH + PATH_REGEX;
		var patternBlob = BLOB_PATH + PATH_REGEX;

		if (line.contains(TREE_PATH)) {
			return regexService.getMatcher(patternTree, line);
		} else if (line.contains(BLOB_PATH)){
			return regexService.getMatcher(patternBlob, line);
		}

		return "";
	}

}
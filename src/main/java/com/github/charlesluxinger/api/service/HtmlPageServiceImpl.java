package com.github.charlesluxinger.api.service;

import com.github.charlesluxinger.api.exception.NotHTMLPageException;
import com.github.charlesluxinger.api.exception.UnformedURLException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotBlank;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
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
	private final RestTemplate restTemplate;

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
			var body = restTemplate.getForObject(getUrl(url).toURI(), String.class);
			return new BufferedReader(new StringReader(body));
		} catch (URISyntaxException e) {
			throw new NotHTMLPageException(url, e);
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
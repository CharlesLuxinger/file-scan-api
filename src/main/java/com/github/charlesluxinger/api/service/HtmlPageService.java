package com.github.charlesluxinger.api.service;

import javax.validation.constraints.NotBlank;
import java.io.BufferedReader;
import java.net.URL;
import java.util.Set;

public interface HtmlPageService {

	String getPath(@NotBlank final String url);

	URL getUrl(@NotBlank final String url);

	BufferedReader getHTMLPageByURL(@NotBlank final String url);

	Set<String> getPathsByHTMLPage(@NotBlank final String url);

	String findPathInHtmlLine(@NotBlank final String line);

}
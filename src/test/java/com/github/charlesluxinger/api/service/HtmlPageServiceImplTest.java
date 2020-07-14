package com.github.charlesluxinger.api.service;

import com.github.charlesluxinger.api.exception.NonHTMLPageException;
import com.github.charlesluxinger.api.exception.UnformedURLException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.Set;

import static com.github.charlesluxinger.api.model.RegexPattern.PATH_REGEX;
import static com.github.charlesluxinger.api.model.UrlParts.BLOB_PATH;
import static com.github.charlesluxinger.api.model.UrlParts.TREE_PATH;
import static java.util.Objects.nonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HtmlPageServiceImplTest {

	@Mock
	private RegexPatternService regexService;

	@InjectMocks
	private HtmlPageServiceImpl service;

	private final String gitHubUrlWithPath = "https://github.com/CharlesLuxinger/file-scan-api";
	private final String gitHubUrl = "https://github.com";


	@Test
	@DisplayName("should return a path from url")
	public void should_return_a_path_from_url_getPath(){
		var path = service.getPath(gitHubUrlWithPath);

		assertEquals(path, "/CharlesLuxinger/file-scan-api");
	}

	@Test
	@DisplayName("should return an url")
	public void should_return_an_url_getURL(){
		var url = service.getUrl(gitHubUrlWithPath);

		assertTrue(url instanceof URL);
		assertTrue(nonNull(url));
		assertEquals(url.toString(), gitHubUrlWithPath);
	}

	@Test
	@DisplayName("should return an url")
	public void should_return_a_complete_github_url_getURL(){
		var path = "/CharlesLuxinger/file-scan-api";
		var url = service.getUrl(path);

		assertTrue(url instanceof URL);
		assertTrue(nonNull(url));
		assertEquals(url.toString(), gitHubUrl + path);
	}

	@Test
	@DisplayName("should throw an exception when there is something wrong")
	public void should_throw_an_exception_when_there_is_something_wrong_getURL(){
		var mockService = mock(HtmlPageServiceImpl.class);
		when(mockService.getUrl(gitHubUrlWithPath)).thenThrow(UnformedURLException.class);

		assertThrows(UnformedURLException.class, () -> mockService.getUrl(gitHubUrlWithPath));
	}

	@Test
	@DisplayName("should return a tree path")
	public void should_return_a_tree_path_findPathInHtmlLine(){
		var url = gitHubUrlWithPath + "/tree/master/file.txt";

		when(regexService.getMatcher(TREE_PATH + PATH_REGEX, url)).thenReturn(TREE_PATH + "file.txt");

		var path = service.findPathInHtmlLine(url);

		assertEquals("/tree/master/file.txt", path);
	}

	@Test
	@DisplayName("should return a blob path")
	public void should_return_a_blob_path_findPathInHtmlLine(){
		var url = gitHubUrlWithPath + "/blob/master/file.txt";

		when(regexService.getMatcher(BLOB_PATH + PATH_REGEX, url)).thenReturn(BLOB_PATH + "file.txt");

		var path = service.findPathInHtmlLine(url);

		assertEquals("/blob/master/file.txt", path);
	}

	@Test
	@DisplayName("should return non null buffer read")
	public void should_return_non_null_buffer_read_getHTMLPageByURL() throws IOException {
		var page = service.getHTMLPageByURL(gitHubUrl);

		assertTrue(page instanceof BufferedReader);
		assertTrue(nonNull(page));

		page.lines().forEach(p -> assertTrue(nonNull(p)));
	}

	@Test
	@DisplayName("should return a set with paths")
	public void should_return_a_set_with_paths_getPathsByHTMLPage() throws IOException {
		var page = service.getPathsByHTMLPage(gitHubUrlWithPath);

		assertTrue(page instanceof Set);

		page.forEach(p -> assertTrue(nonNull(p)));
	}

	@Test
	@DisplayName("should throw an exception when there is something wrong")
	public void should_throw_an_exception_when_there_is_something_wrong_getHTMLPageByURL(){
		var mockService = mock(HtmlPageServiceImpl.class);

		when(mockService.getHTMLPageByURL(gitHubUrl)).thenThrow(NonHTMLPageException.class);

		assertThrows(NonHTMLPageException.class, () -> mockService.getHTMLPageByURL(gitHubUrl));
	}

}
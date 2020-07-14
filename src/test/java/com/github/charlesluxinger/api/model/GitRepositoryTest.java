package com.github.charlesluxinger.api.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.platform.commons.util.StringUtils.isBlank;

class GitRepositoryTest {

	@Test
	@DisplayName("should capture whole github url")
	public void should_capture_whole_github_with_valid_GITHUB_URL_REGEX() {
		var text = "https://github.com/CharlesLuxinger/file-scan-api";
		var repo = new GitRepository();
		var expected = getMatcher(repo.getGITHUB_URL_REGEX(), text);

		assertEquals(expected, text);
	}

	@Test
	@DisplayName("should return an empty string with a invalid github url")
	public void should_return_an_empty_string_with_an_invalid_github_url_GITHUB_URL_REGEX() {
		var text = "https://invalid.com/CharlesLuxinger/file-scan-api";
		var repo = new GitRepository();
		var expected = getMatcher(repo.getGITHUB_URL_REGEX(), text);

		assertTrue(isBlank(expected));
	}


	private String getMatcher(String regex, String text) {
		var pattern = Pattern.compile(regex);
		var matcher = pattern.matcher(text);

		if (matcher.find()){
			return matcher.group();
		}
		return "";
	}

}
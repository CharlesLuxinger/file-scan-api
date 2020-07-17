package com.github.charlesluxinger.api.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static com.github.charlesluxinger.api.model.RegexPattern.BYTES_QUANTITY_REGEX;
import static com.github.charlesluxinger.api.model.RegexPattern.BYTES_QUANTITY_REGEX_OR_K_BYTES_QUANTITY_REGEX;
import static com.github.charlesluxinger.api.model.RegexPattern.DOUBLE_REGEX;
import static com.github.charlesluxinger.api.model.RegexPattern.GITHUB_URL_REGEX;
import static com.github.charlesluxinger.api.model.RegexPattern.K_BYTES_QUANTITY_REGEX;
import static com.github.charlesluxinger.api.model.RegexPattern.LINE_QUANTITY_REGEX;
import static com.github.charlesluxinger.api.model.RegexPattern.LINE_REGEX;
import static com.github.charlesluxinger.api.model.RegexPattern.NUMBER_REGEX;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RegexPatternTest {

	@Test
	@DisplayName("should capture only number")
	public void should_capture_only_number_NUMBER_REGEX() {
		var text = "A simple text with number: 123";
		var expected = getMatcher(NUMBER_REGEX, text);

		assertEquals(expected, "123");
	}

	@Test
	@DisplayName("should capture only double number")
	public void should_capture_only_double_number_DOUBLE_REGEX() {
		var text = "A simple text with double: 123.12";
		var expected = getMatcher(DOUBLE_REGEX, text);

		assertEquals(expected, "123.12");
	}

	@Test
	@DisplayName("should capture only lines text")
	public void should_capture_only_bytes_lines_LINE_QUANTITY_REGEX() {
		var text = "A simple text with 12 lines";
		var expected = getMatcher(LINE_QUANTITY_REGEX, text);

		assertEquals(expected, "12 lines");
	}

	@Test
	@DisplayName("should capture only bytes text")
	public void should_capture_only_bytes_text_BYTES_QUANTITY_REGEX() {
		var text = "A simple text with 12 Bytes";
		var expected = getMatcher(BYTES_QUANTITY_REGEX, text);

		assertEquals(expected, "12 Bytes");
	}

	@Test
	@DisplayName("should capture only KB text")
	public void should_capture_only_KB_text_K_BYTES_QUANTITY_REGEX() {
		var text = "A simple text with 12.5 KB";
		var expected = getMatcher(K_BYTES_QUANTITY_REGEX, text);

		assertEquals(expected, "12.5 KB");
	}

	@Test
	@DisplayName("should capture only bytes text")
	public void should_capture_only_bytes_text_BYTES_QUANTITY_REGEX_OR_K_BYTES_QUANTITY_REGEX() {
		var text = "A simple text with 12 Bytes";
		var expected = getMatcher(BYTES_QUANTITY_REGEX_OR_K_BYTES_QUANTITY_REGEX, text);

		assertEquals(expected, "12 Bytes");
	}

	@Test
	@DisplayName("should capture only KB text")
	public void should_capture_only_KB_text_BYTES_QUANTITY_REGEX_OR_K_BYTES_QUANTITY_REGEX() {
		var text = "A simple text with 12.5 KB";
		var expected = getMatcher(BYTES_QUANTITY_REGEX_OR_K_BYTES_QUANTITY_REGEX, text);

		assertEquals(expected, "12.5 KB");
	}

	@Test
	@DisplayName("should capture only lines text")
	public void should_capture_only_bytes_lines_LINE_REGEX() {
		var text = "A simple text with 12 lines";
		var expected = getMatcher(LINE_QUANTITY_REGEX, text);

		assertEquals(expected, "12 lines");
	}

	@Test
	@DisplayName("should capture only bytes text")
	public void should_capture_only_bytes_text_LINE_REGEX() {
		var text = "A simple text with 12 Bytes";
		var expected = getMatcher(BYTES_QUANTITY_REGEX, text);

		assertEquals(expected, "12 Bytes");
	}

	@Test
	@DisplayName("should capture only KB text")
	public void should_capture_only_KB_text_LINE_REGEX() {
		var text = "A simple text with 12.5 KB";
		var expected = getMatcher(LINE_REGEX, text);

		assertEquals(expected, "12.5 KB");
	}

	@Test
	@DisplayName("should capture a valid github url")
	public void should_capture_a_valid_github_url_GITHUB_URL_REGEX() {
		var text = "https://github.com/CharlesLuxinger/Dart";
		var expected = getMatcher(GITHUB_URL_REGEX, text);

		assertEquals(expected, text);
	}

	@Test
	@DisplayName("should return blank an invalid github url")
	public void should_return_blank_an_invalid_github_url_GITHUB_URL_REGEX() {
		var text = "https://invalid.com/CharlesLuxinger/Dart";
		var expected = getMatcher(GITHUB_URL_REGEX, text);

		assertTrue(expected.isBlank());
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
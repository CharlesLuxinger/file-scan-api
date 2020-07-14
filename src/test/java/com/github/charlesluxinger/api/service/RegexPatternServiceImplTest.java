package com.github.charlesluxinger.api.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.platform.commons.util.StringUtils.isBlank;

class RegexPatternServiceImplTest {

	public final String NUMBER_REGEX = "[0-9]+";

	private final RegexPatternServiceImpl service = new RegexPatternServiceImpl();

	@Test
	@DisplayName("should capture only number")
	public void should_capture_only_number_NUMBER_REGEX() {
		var text = "A simple text with number: 123";
		var expected = service.getMatcher(NUMBER_REGEX, text);

		assertEquals(expected, "123");
	}

	@Test
	@DisplayName("should capture only number")
	public void should_return_an_empty_string_when_not_capture_a_pattern() {
		var text = "A simple text without number";
		var expected = service.getMatcher(NUMBER_REGEX, text);

		assertTrue(isBlank(expected));
	}
}
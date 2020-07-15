package com.github.charlesluxinger.api.util;

import com.github.charlesluxinger.api.service.RegexPatternServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NumberUtilsImplTest {

	private NumberUtilsImpl numberUtils;

	@BeforeEach
	public void SetUp() {
		numberUtils = new NumberUtilsImpl(new RegexPatternServiceImpl());
	}

	@Test
	@DisplayName("should return the number of line")
	void should_return_the_number_of_line_getLinesQuantity() {
		var lines = Set.of("17 lines", "17 Bytes");
		var quantity = numberUtils.getLinesQuantity(lines);

		assertEquals(17, quantity);
	}

	@Test
	@DisplayName("should return zero when not exist text with number of lines")
	void should_return_zero_when_not_exist_text_with_number_of_lines_getLinesQuantity() {
		var lines = Set.of("17 Bytes");
		var quantity = numberUtils.getLinesQuantity(lines);

		assertEquals(0, quantity);
	}

	@Test
	@DisplayName("should return the number of bytes when text is in bytes")
	void should_return_the_number_of_bytes_when_text_is_in_bytes_getBytesQuantity() {
		var bytes = Set.of("17 lines", "17 Bytes");
		var quantity = numberUtils.getBytesQuantity(bytes);

		assertEquals(17, quantity);
	}

	@Test
	@DisplayName("should return zero when not exist text with number of bytes or KB")
	void should_return_zero_when_not_exist_text_with_number_of_bytes_or_KB_getLinesQuantity() {
		var lines = Set.of("17 lines");
		var quantity = numberUtils.getBytesQuantity(lines);

		assertEquals(0, quantity);
	}

	@Test
	@DisplayName("should return the number of bytes when text is in KB")
	void should_return_the_number_of_bytes_when_text_is_in_KB_getBytesQuantity() {
		var bytes = Set.of("17 lines", "17.5 KB");
		var quantity = numberUtils.getBytesQuantity(bytes);

		assertEquals(17500, quantity);
	}

	@Test
	@DisplayName("should return the number of bytes when text is in KB")
	void should_return_the_number_of_bytes_when_text_is_in_KB_getBytesFromKBytes() {
		var bytes = "17.5 KB";
		var quantity = numberUtils.getBytesFromKBytes(bytes);

		assertEquals(17500, quantity);
	}

	@Test
	@DisplayName("should return zero when not exist text with number KB")
	void should_return_zero_when_not_exist_text_with_number_KB_getLinesQuantity() {
		var lines = "17 lines";
		var quantity = numberUtils.getBytesFromKBytes(lines);

		assertEquals(0, quantity);
	}

	@Test
	@DisplayName("should return the number in a text")
	void should_return_the_long_in_a_text_getBytesFromKBytes() {
		var bytes = "There is a number here: 100";
		var quantity = numberUtils.getNumberStringToLong(bytes);

		assertEquals(100, quantity);
	}

	@Test
	@DisplayName("should return zero when not exist int in text")
	void should_return_zero_when_not_exist_int_in_text_getLinesQuantity() {
		var text = "not exist a number";
		var quantity = numberUtils.getNumberStringToLong(text);

		assertEquals(0, quantity);
	}
}
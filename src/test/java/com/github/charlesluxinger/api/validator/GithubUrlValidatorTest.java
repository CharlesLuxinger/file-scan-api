package com.github.charlesluxinger.api.validator;

import com.github.charlesluxinger.api.exception.NotValidURLException;
import com.github.charlesluxinger.api.service.RegexPatternServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GithubUrlValidatorTest {

	private GithubUrlValidator validator;

	@BeforeEach
	public void setUp() {
		validator = new GithubUrlValidator(new RegexPatternServiceImpl());
	}

	@Test
	@DisplayName("should return true with a valid github url")
	public void should_return_true_with_a_valid_github_url_isValid() {
		assertTrue(validator.isValid("https://github.com/CharlesLuxinger/Dart"));
	}

	@Test
	@DisplayName("should return false with a null value")
	public void should_return_false_with_a_null_value_isValid() {
		assertFalse(validator.isValid(null));
	}

	@Test
	@DisplayName("should throw an exception with an invalid url")
	public void should_throw_an_exception_with_an_invalid_url_isValid() {
		var url = "https://invalid.com/CharlesLuxinger/Dart";
		assertThrows(NotValidURLException.class, () -> validator.isValid(url), "Is not a valid github url: " + url);
	}

	@Test
	@DisplayName("should throw an exception with a git clone url")
	public void should_throw_an_exception_with_a_git_clone_url_isValid() {
		var url = "https://github.com/CharlesLuxinger/Dart.git";
		assertThrows(NotValidURLException.class, () -> validator.isValid(url), "Is not a valid github url: " + url);
	}

}
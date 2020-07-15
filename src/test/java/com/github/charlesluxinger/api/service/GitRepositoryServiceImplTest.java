package com.github.charlesluxinger.api.service;

import com.github.charlesluxinger.api.exception.NotValidURLException;
import com.github.charlesluxinger.api.util.NumberUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class GitRepositoryServiceImplTest {

	@Mock
	private FilesGroupDataImpl filesGroupData;

	@Mock
	private HtmlPageService htmlPageService;

	@Mock
	private RegexPatternService regexService;

	@Mock
	private NumberUtils numberUtils;

	@InjectMocks
	private GitRepositoryServiceImpl service;

	@Test
	@DisplayName("should throw an exception when url ends with .git")
	public void should_throw_an_exception_when_url_ends_with_dot_git_isACloneRepositoryUrl() {
		assertThrows(NotValidURLException.class, () -> service.isACloneRepositoryUrl("https://github.com/CharlesLuxinger/Dart.git"));
	}

	@Test
	@DisplayName("should throw an exception when url ends with .git")
	public void should_not_throw_an_exception_when_url_not_ends_with_dot_git_isACloneRepositoryUrl() {
		assertDoesNotThrow(() -> service.isACloneRepositoryUrl("https://github.com/CharlesLuxinger/Dart"));
	}

	@Test
	@DisplayName("should return true when url contains tree path")
	public void should_return_true_when_url_contains_tree_path_isACloneRepositoryUrl() {
		assertTrue(service.isDirectoryPath("https://github.com/CharlesLuxinger/Dart/tree/master/folder"));
	}

	@Test
	@DisplayName("should return true when url not contains tree path")
	public void should_return_false_when_url_not_contains_tree_path_isACloneRepositoryUrl() {
		assertFalse(service.isDirectoryPath("https://github.com/CharlesLuxinger/Dart/blob/master/folder"));
	}
}
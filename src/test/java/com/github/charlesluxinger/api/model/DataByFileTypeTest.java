package com.github.charlesluxinger.api.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DataByFileTypeTest {

	@Test
	@DisplayName("should return an object instance of DataByFileTypeTest")
	public void should_return_an_object_instance_of_DataByFileTypeTest() {
		var url = "https://github.com/CharlesLuxinger/blob/master/file-scan-api.git";
		var lines = 123;
		var bytes = 123;

		var data = DataByFileType.of(url, lines, bytes);

		assertTrue(data instanceof DataByFileType);

		assertEquals("git", data.getFileType());
		assertEquals(lines, data.getLines());
		assertEquals(bytes, data.getBytes());
	}

	@Test
	@DisplayName("an url with file type should return file type")
	public void an_url_with_file_type_should_return_file_type_getFileType() {
		var url = "https://github.com/CharlesLuxinger/blob/master/file-scan-api.txt";

		var type = DataByFileType.getFileType(url);

		assertEquals("txt", type);
	}

	@Test
	@DisplayName("an url without file type should return file name")
	public void an_url_without_file_type_should_return_file_name_getFileType() {
		var url = "https://github.com/CharlesLuxinger/blob/master/file-scan-api";

		var type = DataByFileType.getFileType(url);

		assertEquals("file-scan-api", type);
	}

	@Test
	@DisplayName("non url file type should throw Illegal Argument Exception")
	public void non_url_file_type_should_throw_illegal_argument_exception_getFileType() {
		var url = "https://github.com/CharlesLuxinger/file-scan-api";

		assertThrows(IllegalArgumentException.class, () -> DataByFileType.getFileType(url), "Should a valid file URL");
	}

}
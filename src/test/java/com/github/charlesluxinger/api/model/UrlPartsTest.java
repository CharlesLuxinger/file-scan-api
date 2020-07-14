package com.github.charlesluxinger.api.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.github.charlesluxinger.api.model.UrlParts.BLOB_PATH;
import static com.github.charlesluxinger.api.model.UrlParts.HTTPS_GITHUB_COM;
import static com.github.charlesluxinger.api.model.UrlParts.TREE_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UrlPartsTest {

	@Test
	@DisplayName("should contains github url")
	public void should_contains_github_url_HTTPS_GITHUB_COM(){
		assertEquals(HTTPS_GITHUB_COM, "https://github.com");
	}

	@Test
	@DisplayName("should contains tree path")
	public void should_contains_tree_path_TREE_PATH(){
		assertEquals(TREE_PATH, "/tree/master/");
	}

	@Test
	@DisplayName("should contains tree path")
	public void should_contains_blob_path_BLOB_PATH(){
		assertEquals(BLOB_PATH, "/blob/master/");
	}

}
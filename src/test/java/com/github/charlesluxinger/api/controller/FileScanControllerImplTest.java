package com.github.charlesluxinger.api.controller;

import com.github.charlesluxinger.api.model.GitRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FileScanControllerImplTest {

	@LocalServerPort
	private int port;

	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.basePath = "/api/v1/repository";
		RestAssured.port = port;
	}


	@Test
	@DisplayName("should return 200 success with a correct git url pattern")
	public void should_return_200_success_with_a_correct_git_url_pattern_gitRepository() {
		var url = "https://github.com/CharlesLuxinger/Dart";

		given()
				.contentType(ContentType.JSON)
				.body(new GitRepository(url))
			.expect()
				.statusCode(200)
			.when()
				.post()
			.then()
				.body("size()", greaterThan(0))
				.body("get(0).fileType", notNullValue())
				.body("get(0).lines", greaterThan(0))
				.body("get(0).bytes", greaterThan(0));
	}

	@Test
	@DisplayName("should return 400 bad request with an incorrect git url pattern")
	public void should_return_400_bad_request_with_an_incorrect_git_url_pattern_gitRepository() {
		var url = "http://hub.com/CharlesLuxinger/Dart.git";

		given()
				.contentType(ContentType.JSON)
				.body(new GitRepository(url))
			.expect()
				.statusCode(400)
			.when()
				.post()
			.then()
				.body("status", is(400))
				.body("title", equalTo("Bad Request"))
				.body("detail", equalTo("Is not a valid github url: " + url))
				.body("path", equalTo("/api/v1/repository"))
				.body("timestamp", notNullValue());
	}

	@Test
	@DisplayName("should return 400 bad request with a clone git url")
	public void should_return_400_bad_request_with_a_clone_git_url_pattern_gitRepository() {
		var url = "https://github.com/CharlesLuxinger/Dart.git";

		given()
				.contentType(ContentType.JSON)
				.body(new GitRepository(url))
			.expect()
				.statusCode(400)
			.when()
				.post()
			.then()
				.body("status", is(400))
				.body("title", equalTo("Bad Request"))
				.body("detail", equalTo("Is not a valid github url: " + url))
				.body("path", equalTo("/api/v1/repository"))
				.body("timestamp", notNullValue());
	}

	@Test
	@DisplayName("should return 400 bad request with an unexpected git url pattern")
	public void should_return_400_bad_request_with_an_unexpected_body_gitRepository() {
		var body = "{\"something\": \"something\"}";

		given()
				.contentType(ContentType.JSON)
				.body(body)
			.expect()
				.statusCode(400)
			.when()
				.post()
			.then()
				.body("status", is(400))
				.body("title", equalTo("Bad Request"))
				.body("detail", equalTo("One or more fields are invalid. Fill in correctly and try again."))
				.body("path", equalTo("/api/v1/repository"))
				.body("timestamp", notNullValue());
	}

	@Test
	@DisplayName("should return 404 not found with not existed git url")
	public void should_return_200_not_found_with_not_existed_git_url_gitRepository() {
		var url = "https://github.com/CharlesLuxinger/Dartd";

		given()
				.contentType(ContentType.JSON)
				.body(new GitRepository(url))
			.expect()
				.statusCode(404)
			.when()
				.post()
			.then()
				.body("status", is(404))
				.body("title", equalTo("Not Found"))
				.body("detail", equalTo("Repository not found."))
				.body("path", equalTo("/api/v1/repository"))
				.body("timestamp", notNullValue());
	}

}
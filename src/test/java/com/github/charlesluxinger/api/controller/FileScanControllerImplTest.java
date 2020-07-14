package com.github.charlesluxinger.api.controller;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FileScanControllerImplTest {

//	@LocalServerPort
//	private int port;
//
//	@BeforeEach
//	public void setUp() {
//		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
//		RestAssured.basePath = "/api/v1/repository";
//		RestAssured.port = port;
//	}
//
//
//	@Test
//	@Disabled
//	@DisplayName("should return 200 success with a correct git url pattern")
//	public void should_return_200_success_with_a_correct_git_url_pattern_gitRepository() {
//		var url = "https://github.com/CharlesLuxinger/file-scan-api.git";
//
//		given()
//				.contentType(ContentType.JSON)
//				.body(new GitRepository(url))
//			.expect()
//				.statusCode(200)
//			.when()
//				.post()
//			.then()
//				.body("size()", notNullValue());
//	}
//
//	@Test
//	@Disabled
//	@DisplayName("should return 400 bad request with an incorrect git url pattern")
//	public void should_return_400_bad_request_with_an_incorrect_git_url_pattern_gitRepository() {
//		var url = "http://hub.com/CharlesLuxinger/file-scan-api";
//
//		given()
//				.contentType(ContentType.JSON)
//				.body(new GitRepository(url))
//			.expect()
//				.statusCode(400)
//			.when()
//				.post()
//			.then()
//				.body("status", is(400))
//				.body("title", equalTo("Bad Request"))
//				.body("detail", equalTo("One or more fields are invalid. Fill in correctly and try again."))
//				.body("path", equalTo("/api/v1/repository"))
//				.body("timestamp", notNullValue());
//	}
//
//	@Test
//	@Disabled
//	@DisplayName("should return 400 bad request with an unexpected git url pattern")
//	public void should_return_400_bad_request_with_an_unexpected_body_gitRepository() {
//		var body = "{\"something\": \"something\"}";
//
//		given()
//				.contentType(ContentType.JSON)
//				.body(body)
//			.expect()
//				.statusCode(400)
//			.when()
//				.post()
//			.then()
//				.body("status", is(400))
//				.body("title", equalTo("Bad Request"))
//				.body("detail", equalTo("One or more fields are invalid. Fill in correctly and try again."))
//				.body("path", equalTo("/api/v1/repository"))
//				.body("timestamp", notNullValue());
//	}

}
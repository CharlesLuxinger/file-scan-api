package com.github.charlesluxinger.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
public class GitRepository {

	private final String GITHUB_URL_REGEX = "(https://github\\.com/)\\b[-a-zA-Z0-9()@:%_.+~#?&/=]+";

	@Setter
	@Getter
	@Schema(example = "https://github.com/CharlesLuxinger/file-scan-api")
	@NotBlank
	@Pattern(regexp = GITHUB_URL_REGEX)
	private String url;

}
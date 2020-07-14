package com.github.charlesluxinger.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GitRepository {

	@JsonIgnore
	private final String GITHUB_URL_REGEX = "(https://github\\.com/)\\b[-a-zA-Z0-9()@:%_.+~#?&/=]+";

	@Schema(example = "https://github.com/CharlesLuxinger/file-scan-api")
	@NotBlank
	@Pattern(regexp = GITHUB_URL_REGEX)
	private String url;

}
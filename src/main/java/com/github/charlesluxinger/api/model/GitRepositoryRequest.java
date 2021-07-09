package com.github.charlesluxinger.api.model;

import com.github.charlesluxinger.api.validator.GitHubUrl;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GitRepositoryRequest {

	@Schema(example = "https://github.com/CharlesLuxinger/file-scan-api")
	@GitHubUrl
	private String url;

}
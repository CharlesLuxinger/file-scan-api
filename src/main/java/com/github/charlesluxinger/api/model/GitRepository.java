package com.github.charlesluxinger.api.model;

import com.github.charlesluxinger.api.validator.GithubUrl;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GitRepository {

	@Schema(example = "https://github.com/CharlesLuxinger/file-scan-api")
	@GithubUrl
	private String url;

}
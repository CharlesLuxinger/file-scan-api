package com.github.charlesluxinger.api.controller;

import com.github.charlesluxinger.api.exception.ApiExceptionResponse;
import com.github.charlesluxinger.api.model.DataByFileType;
import com.github.charlesluxinger.api.model.GitRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Set;

@Tag(name = "File Scan")
public interface FileScanController {

	@Operation(summary = "Get information about files from a GitHub repository URL", responses = {
			@ApiResponse(responseCode = "200", description = "Receive a GitHub Repository URL",
						 content = @Content(array =  @ArraySchema(schema =  @Schema(implementation = DataByFileType.class)),
						 mediaType = "application/json")),
			@ApiResponse(responseCode = "400", description = "Incorrect Payload",
						 content = @Content(schema =  @Schema(implementation = ApiExceptionResponse.class),
						 mediaType = "application/json")),
			@ApiResponse(responseCode = "500", description = "Server Error",
						 content = @Content(schema =  @Schema(implementation = ApiExceptionResponse.class),
						 mediaType = "application/json"))
	})
	Set<DataByFileType> gitRepository(@Parameter(description = "An object with url parameter",
											required = true,
											schema = @Schema(implementation = GitRepository.class, oneOf = GitRepository.class))
	                             final GitRepository repository);

}
package com.github.charlesluxinger.api.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

import static java.time.OffsetDateTime.now;

/**
 * ApiExceptionResponse
 */
@Schema(name = "Api Exception Response")
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiExceptionResponse {

    @Schema(example = "999")
    private final int status;

    @Schema(example = "Network crashes")
    @NotBlank
    private final String title;

    @Schema(example = "Some network cable was broken.")
    @NotBlank
    private final String detail;

    @Schema(example = "/api/v1/resource/1")
    @NotBlank
    private final String path;

    @Schema(example = "2020-04-24T19:27:01.718Z")
    @NotNull
    private final OffsetDateTime timestamp = now().truncatedTo(ChronoUnit.SECONDS);

}
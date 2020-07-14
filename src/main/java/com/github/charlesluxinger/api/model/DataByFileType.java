package com.github.charlesluxinger.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

import static com.github.charlesluxinger.api.model.UrlParts.BLOB_PATH;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
public class DataByFileType {

	@Schema(example = "txt")
	@NotBlank
	@EqualsAndHashCode.Include
	private String fileType;

	@Schema(example = "1")
	@PositiveOrZero
	private long lines;

	@Schema(example = "1")
	@PositiveOrZero
	private long bytes;

	public static DataByFileType of(final String url, final long lines, final long bytes) {
		return new DataByFileType(getFileType(url), lines, bytes);
	}

	public static String getFileType(final String url) {
		if (!url.contains(BLOB_PATH)){
			throw new IllegalArgumentException("Should a valid file URL");
		}

		var file = url.substring(url.lastIndexOf('/') + 1);

		if (file.contains(".")){
			return file.substring(file.lastIndexOf('.') + 1);
		}

		return file;
	}
}
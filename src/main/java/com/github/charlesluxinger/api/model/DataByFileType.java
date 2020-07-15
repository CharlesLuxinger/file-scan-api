package com.github.charlesluxinger.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import static com.github.charlesluxinger.api.model.UrlParts.BLOB_PATH;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
public class DataByFileType {

	@Schema(example = "txt")
	@NotBlank
	@EqualsAndHashCode.Include
	private final String fileType;

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
			throw new IllegalArgumentException("Should set a valid file URL: " + url);
		}

		var file = url.substring(url.lastIndexOf('/') + 1);

		if (file.contains(".")){
			return file.substring(file.lastIndexOf('.') + 1);
		}

		return file;
	}

	public void sumValues(@NotNull DataByFileType newData) {
		this.bytes += newData.getBytes();
		this.lines += newData.getLines();
	}

}
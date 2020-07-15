package com.github.charlesluxinger.api.util;

import com.github.charlesluxinger.api.service.RegexPatternService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

import static com.github.charlesluxinger.api.model.RegexPattern.BYTES_QUANTITY_REGEX_OR_K_BYTES_QUANTITY_REGEX;
import static com.github.charlesluxinger.api.model.RegexPattern.DOUBLE_REGEX;
import static com.github.charlesluxinger.api.model.RegexPattern.LINE_QUANTITY_REGEX;
import static com.github.charlesluxinger.api.model.RegexPattern.NUMBER_REGEX;

@Service
@Validated
@AllArgsConstructor
public class NumberUtilsImpl implements NumberUtils {

	private final RegexPatternService regexService;

	public long getLinesQuantity(@NotNull final Set<String> lines) {
		var lineQtd = lines.parallelStream()
						   .filter(l -> l.contains("lines"))
						   .findFirst()
						   .orElse("");

		if (lineQtd.isBlank()){
			return 0;
		}

		var lineQuantity = regexService.getMatcher(LINE_QUANTITY_REGEX, lineQtd);

		return getNumberStringToLong(lineQuantity);
	}

	public long getBytesQuantity(@NotNull final Set<String> lines) {
		var bytesQtd = lines.parallelStream()
							.filter(l -> l.contains("Bytes") || l.contains("KB"))
							.findFirst()
							.orElse("");

		if (bytesQtd.isBlank()){
			return 0;
		}

		var bytesLine = regexService.getMatcher(BYTES_QUANTITY_REGEX_OR_K_BYTES_QUANTITY_REGEX, bytesQtd);

		if (bytesLine.contains("KB")){
			return getBytesFromKBytes(bytesLine);
		}

		return getNumberStringToLong(bytesLine);
	}

	public long getBytesFromKBytes(@NotBlank final String line) {
		var K_BYTE = 1000;

		var doubleString = regexService.getMatcher(DOUBLE_REGEX, line);

		if (doubleString.isBlank()){
			return 0L;
		}

		var value = Double.valueOf(doubleString);
		value = value * K_BYTE;

		return value.longValue();
	}

	public long getNumberStringToLong(@NotBlank final String line) {
		var number = regexService.getMatcher(NUMBER_REGEX, line);

		if (number.isBlank()){
			return 0L;
		}

		return Long.parseLong(number);
	}

}
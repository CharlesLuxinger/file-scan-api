package com.github.charlesluxinger.api.util;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

public interface NumberUtils {

	long getLinesQuantity(@NotNull final Set<String> lines);

	long getBytesQuantity(@NotNull final Set<String> lines);

	long getBytesFromKBytes(@NotBlank final String line);

	long getNumberStringToLong(@NotBlank final String line);

}
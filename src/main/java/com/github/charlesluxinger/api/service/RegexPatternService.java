package com.github.charlesluxinger.api.service;

import javax.validation.constraints.NotBlank;

public interface RegexPatternService {

	String getMatcher(@NotBlank final String regex, @NotBlank final String text);

}

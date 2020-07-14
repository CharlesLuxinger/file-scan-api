package com.github.charlesluxinger.api.service;

import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import java.util.regex.Pattern;

@Service
public class RegexPatternServiceImpl  implements RegexPatternService{

	public String getMatcher(@NotBlank final String regex, @NotBlank final String text) {
		var pattern = Pattern.compile(regex);
		var matcher = pattern.matcher(text);

		if (matcher.find()){
			return matcher.group();
		}
		return "";
	}

}

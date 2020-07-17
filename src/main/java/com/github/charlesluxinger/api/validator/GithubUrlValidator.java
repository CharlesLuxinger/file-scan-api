package com.github.charlesluxinger.api.validator;

import com.github.charlesluxinger.api.exception.NotValidURLException;
import com.github.charlesluxinger.api.service.RegexPatternService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.github.charlesluxinger.api.model.RegexPattern.GITHUB_URL_REGEX;

@Component
@AllArgsConstructor
public class GithubUrlValidator implements ConstraintValidator<GithubUrl, String> {

    private final RegexPatternService regexService;

    @Override
    public boolean isValid(final String url, final ConstraintValidatorContext constraintValidatorContext) {
        return isValid(url);
    }

    protected boolean isValid(final String url) {
        if (url == null){
            return false;
        }

        var matcher = regexService.getMatcher(GITHUB_URL_REGEX, url);

        if (matcher.isBlank() || matcher.endsWith(".git")){
            throw new NotValidURLException("Is not a valid github url: " + url);
        }

        return true;
    }
}

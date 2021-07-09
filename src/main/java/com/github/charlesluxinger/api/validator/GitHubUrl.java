package com.github.charlesluxinger.api.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({METHOD, FIELD, PARAMETER, TYPE_USE})
@Documented
@Constraint(validatedBy = GithubUrlValidator.class)
public @interface GitHubUrl {
	String message() default "must match Github Pattern";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default {};

	@Target({ METHOD, FIELD, PARAMETER, TYPE_USE })
	@Retention(RUNTIME)
	@Documented
	@interface List {
		GitHubUrl[] value();
	}

}

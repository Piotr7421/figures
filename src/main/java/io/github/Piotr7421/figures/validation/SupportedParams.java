package io.github.Piotr7421.figures.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = SupportedParamsValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SupportedParams {

    String message() default "FIGURE_PARAMS_NOT_SUPPORTED";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

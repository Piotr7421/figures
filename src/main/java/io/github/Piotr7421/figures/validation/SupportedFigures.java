package io.github.Piotr7421.figures.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = SupportedFiguresValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SupportedFigures {

    String message() default "FIGURE_TYPE_NOT_SUPPORTED";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

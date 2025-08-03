package io.github.Piotr7421.figures.validation;

import io.github.Piotr7421.figures.config.FigureFields;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class SupportedParamsValidator implements ConstraintValidator<SupportedParams, Map<String, String>> {

    private final FigureFields figureFields;

    @Override
    public boolean isValid(Map<String, String> params, ConstraintValidatorContext constraintValidatorContext) {

        if (params == null) {
            return false;
        }

        return params.entrySet().stream()
                .anyMatch(param -> figureFields.reflectionFields().entrySet().stream()
                        .anyMatch(figure -> figure.getValue().contains(param.getKey())));
    }
}


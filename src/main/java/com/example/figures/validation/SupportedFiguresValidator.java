package com.example.figures.validation;

import com.example.figures.strategy.FigureCreation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class SupportedFiguresValidator implements ConstraintValidator<SupportedFigures, String> {

    private final Map<String, FigureCreation> figureCreations;

    @Override
    public boolean isValid(String type, ConstraintValidatorContext constraintValidatorContext) {
        return figureCreations.containsKey(type);
    }
}

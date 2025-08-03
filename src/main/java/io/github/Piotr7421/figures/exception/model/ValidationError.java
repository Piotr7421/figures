package io.github.Piotr7421.figures.exception.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationError extends ErrorResponse {
    private final List<ViolationInfo> violations = new ArrayList<>();

    public ValidationError() {
        super("Validation error");
    }

    public void addViolation(String field, String message) {
        violations.add(new ViolationInfo(field, message));
    }

    public record ViolationInfo(String field, String message) {
    }
}

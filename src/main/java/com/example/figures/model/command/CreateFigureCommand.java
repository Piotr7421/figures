package com.example.figures.model.command;

import com.example.figures.validation.SupportedFigures;
import com.example.figures.validation.SupportedParams;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateFigureCommand {

    @NotNull(message = "NULL_FIGURE_TYPE")
    @Pattern(regexp = "\\p{Lu}\\p{Ll}{1,19}", message = "PATTERN_MISMATCH_{regexp}")
    @SupportedFigures
    private String type;

    @NotEmpty(message = "EMPTY_PARAMS")
    @NotNull(message = "NULL_PARAMS")
    @SupportedParams
    private Map<String, String> params;
}

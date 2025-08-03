package io.github.Piotr7421.figures.model.command;

import io.github.Piotr7421.figures.validation.SupportedFigures;
import io.github.Piotr7421.figures.validation.SupportedParams;
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

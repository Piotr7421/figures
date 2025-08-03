package io.github.Piotr7421.figures.validation;

import io.github.Piotr7421.figures.config.FigureFields;
import io.github.Piotr7421.figures.exception.IncorrectParamsException;
import io.github.Piotr7421.figures.exception.IncorrectParamsSizeException;
import io.github.Piotr7421.figures.model.command.CreateFigureCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CommandParamsValidation {

    public void validParams(CreateFigureCommand command, FigureFields figureFields) {
        if (command.getParams().isEmpty()) {
            throw new IncorrectParamsException("NULL_PARAMS");
        }
        List<String> reflectionFields = figureFields.reflectionFields().get(command.getType());
        if (reflectionFields.size() - figureFields.numberOfFieldsInAbstractClass() != command.getParams().size()) {
            throw new IncorrectParamsSizeException(MessageFormat
                    .format("INCORRECT_SIZE_OF_{0}_PARAMS", command.getType()));
        }

        for (Map.Entry<String, String> param : command.getParams().entrySet()) {
            if (!reflectionFields.contains(param.getKey())) {
                throw new IncorrectParamsException(MessageFormat.format("INCORRECT_{0}_PARAMS", command.getType()));
            }
        }
    }
}

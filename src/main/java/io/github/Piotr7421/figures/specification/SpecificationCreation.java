package io.github.Piotr7421.figures.specification;

import io.github.Piotr7421.figures.config.FigureFields;
import io.github.Piotr7421.figures.exception.IncorrectSearchKeyException;
import io.github.Piotr7421.figures.exception.IncorrectSearchOperationException;
import io.github.Piotr7421.figures.model.Figure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.text.MessageFormat;
import java.util.List;

@Slf4j
public class SpecificationCreation {
    public static Specification<Figure> create(String search, FigureFields figureFields) {

        FigureSpecificationBuilder builder = new FigureSpecificationBuilder();
        if (search != null) {
            String[] params = search.split(",");
            for (String param : params) {
                log.info("param : {}", param);
                String[] criteria = param.split(":");
                boolean validatedFields = figureFields.reflectionFields().entrySet().stream()
                        .anyMatch(entry -> entry.getValue().contains(criteria[0]));
                if (!validatedFields) {
                    throw new IncorrectSearchKeyException(MessageFormat
                            .format("INCORRECT_SEARCH_KEY : {0} ", criteria[0]));
                }
                if (criteria[1].isBlank()) {
                    throw new IncorrectSearchOperationException("NULL_SEARCH_OPERATION");
                }
                if (!List.of("<", ">", "=", "*").contains(criteria[1])) {
                    throw new IncorrectSearchOperationException(MessageFormat
                            .format("INCORRECT_SEARCH_OPERATION : {0} ", criteria[1]));
                }
                builder.with(criteria[0], criteria[1], criteria[2]);
            }
        }
        return builder.build();
    }
}


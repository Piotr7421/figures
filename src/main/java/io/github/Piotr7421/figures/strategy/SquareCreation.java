package io.github.Piotr7421.figures.strategy;

import io.github.Piotr7421.figures.model.Square;
import io.github.Piotr7421.figures.model.command.CreateFigureCommand;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("Square")
public class SquareCreation implements FigureCreation {
    @Override
    public Square create(CreateFigureCommand command) {
        Map<String, String> params = command.getParams();
        return Square.builder()
                .type(command.getType())
                .side(Double.parseDouble(params.get("side")))
                .build();
    }
}

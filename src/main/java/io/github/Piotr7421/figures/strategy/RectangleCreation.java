package io.github.Piotr7421.figures.strategy;

import io.github.Piotr7421.figures.model.Rectangle;
import io.github.Piotr7421.figures.model.command.CreateFigureCommand;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("Rectangle")
public class RectangleCreation implements FigureCreation {
    @Override
    public Rectangle create(CreateFigureCommand command) {
        Map<String, String> params = command.getParams();
        return Rectangle.builder()
                .type(command.getType())
                .length(Double.parseDouble(params.get("length")))
                .width(Double.parseDouble(params.get("width")))
                .build();
    }
}

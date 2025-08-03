package io.github.Piotr7421.figures.strategy;

import io.github.Piotr7421.figures.model.Circle;
import io.github.Piotr7421.figures.model.command.CreateFigureCommand;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("Circle")
public class CircleCreation implements FigureCreation {
    @Override
    public Circle create(CreateFigureCommand command) {
        Map<String, String> params = command.getParams();
        return Circle.builder()
                .type(command.getType())
                .radius(Double.parseDouble(params.get("radius")))
                .build();
    }
}

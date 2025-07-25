package com.example.figures.strategy;

import com.example.figures.model.Circle;
import com.example.figures.model.command.CreateFigureCommand;
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

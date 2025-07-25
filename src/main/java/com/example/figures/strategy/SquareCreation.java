package com.example.figures.strategy;

import com.example.figures.model.Square;
import com.example.figures.model.command.CreateFigureCommand;
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

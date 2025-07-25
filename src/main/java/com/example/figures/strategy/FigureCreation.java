package com.example.figures.strategy;

import com.example.figures.model.Figure;
import com.example.figures.model.command.CreateFigureCommand;

public interface FigureCreation {
    Figure create(CreateFigureCommand command);
}

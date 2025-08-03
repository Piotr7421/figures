package io.github.Piotr7421.figures.strategy;

import io.github.Piotr7421.figures.model.Figure;
import io.github.Piotr7421.figures.model.command.CreateFigureCommand;

public interface FigureCreation {
    Figure create(CreateFigureCommand command);
}

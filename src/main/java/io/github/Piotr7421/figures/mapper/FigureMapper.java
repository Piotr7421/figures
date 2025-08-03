package io.github.Piotr7421.figures.mapper;

import io.github.Piotr7421.figures.model.Figure;
import io.github.Piotr7421.figures.model.dto.FigureDto;

public interface FigureMapper<T extends Figure> {

    FigureDto mapToDto(T t);
}

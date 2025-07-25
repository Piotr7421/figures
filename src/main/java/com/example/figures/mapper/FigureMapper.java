package com.example.figures.mapper;

import com.example.figures.model.Figure;
import com.example.figures.model.dto.FigureDto;

public interface FigureMapper<T extends Figure> {

    FigureDto mapToDto(T t);
}

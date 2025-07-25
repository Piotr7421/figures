package com.example.figures.service;

import com.example.figures.exception.NoMapperFoundException;
import com.example.figures.mapper.FigureMapper;
import com.example.figures.model.Figure;
import com.example.figures.model.dto.FigureDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FigureMapperService {

    private final Map<String, FigureMapper<? extends Figure>> figureMappers;

    @SuppressWarnings("unchecked")
    public <T extends Figure> FigureDto mapToDto(T figure) {
        String figureMapperName = figure.getClass().getSimpleName() + "Mapper";
        FigureMapper<T> figureMapper = (FigureMapper<T>) figureMappers.get(figureMapperName);
        if (figureMapper == null) {
            throw new NoMapperFoundException(MessageFormat.format("{0}_NOT_FOUND", figureMapperName));
        }
        return figureMapper.mapToDto(figure);
    }
}

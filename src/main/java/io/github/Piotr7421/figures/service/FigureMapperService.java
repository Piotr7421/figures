package io.github.Piotr7421.figures.service;

import io.github.Piotr7421.figures.exception.NoMapperFoundException;
import io.github.Piotr7421.figures.mapper.FigureMapper;
import io.github.Piotr7421.figures.model.Figure;
import io.github.Piotr7421.figures.model.dto.FigureDto;
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

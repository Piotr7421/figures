package com.example.figures.service;

import com.example.figures.model.Figure;
import com.example.figures.model.command.CreateFigureCommand;
import com.example.figures.model.dto.FigureDto;
import com.example.figures.repository.FigureRepository;
import com.example.figures.strategy.FigureCreation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class FigureService {

    private final FigureRepository figureRepository;
    private final Map<String, FigureCreation> figureCreations;
    private final FigureMapperService figureMapperService;

    public FigureDto create(CreateFigureCommand command) {
        FigureCreation creationStrategy = figureCreations.get(command.getType());
        Figure figure = creationStrategy.create(command);
        return figureMapperService.mapToDto(figureRepository.save(figure));
    }

    public Page<FigureDto> findAll(Specification<Figure> spec, Pageable pageable) {
        return figureRepository.findAll(spec, pageable)
                .map(figureMapperService::mapToDto);
    }
}

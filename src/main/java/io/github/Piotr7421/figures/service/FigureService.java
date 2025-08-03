package io.github.Piotr7421.figures.service;

import io.github.Piotr7421.figures.model.Figure;
import io.github.Piotr7421.figures.model.command.CreateFigureCommand;
import io.github.Piotr7421.figures.model.dto.FigureDto;
import io.github.Piotr7421.figures.repository.FigureRepository;
import io.github.Piotr7421.figures.strategy.FigureCreation;
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

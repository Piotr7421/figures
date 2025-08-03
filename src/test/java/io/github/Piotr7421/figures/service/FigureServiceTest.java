package io.github.Piotr7421.figures.service;

import io.github.Piotr7421.figures.model.Figure;
import io.github.Piotr7421.figures.model.command.CreateFigureCommand;
import io.github.Piotr7421.figures.model.dto.FigureDto;
import io.github.Piotr7421.figures.repository.FigureRepository;
import io.github.Piotr7421.figures.strategy.FigureCreation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.Map;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FigureServiceTest {

    @Mock
    private FigureRepository figureRepository;
    @Mock
    private Map<String, FigureCreation> figureCreations;

    @Mock
    private FigureMapperService figureMapperService;

    @InjectMocks
    private FigureService figureService;

    @Test
    void testCreate_Successful() {

        FigureCreation mockCreation = mock(FigureCreation.class);
        Figure mockFigure = mock(Figure.class);
        FigureDto mockFigureDto = mock(FigureDto.class);
        CreateFigureCommand command = new CreateFigureCommand();
        command.setType("Circle");
        when(figureCreations.get(anyString())).thenReturn(mockCreation);
        when(mockCreation.create(any())).thenReturn(mockFigure);
        when(figureRepository.save(any(Figure.class))).thenReturn(mockFigure);
        when(figureMapperService.mapToDto(any(Figure.class))).thenReturn(mockFigureDto);

        FigureDto result = figureService.create(command);

        assertNotNull(result);
        verify(figureCreations).get("Circle");
        verify(mockCreation).create(command);
        verify(figureRepository).save(mockFigure);
        verify(figureMapperService).mapToDto(mockFigure);
    }

    @Test
    void testFindAll_Successful() {
        Specification<Figure> spec = mock(Specification.class);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Figure> page = new PageImpl<>(Collections.singletonList(mock(Figure.class)));

        when(figureRepository.findAll(spec, pageable)).thenReturn(page);
        when(figureMapperService.mapToDto(any(Figure.class))).thenReturn(mock(FigureDto.class));

        Page<FigureDto> result = figureService.findAll(spec, pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size()); // Assuming one figure in the mock page
        verify(figureRepository).findAll(spec, pageable);
        verify(figureMapperService, atLeastOnce()).mapToDto(any(Figure.class));
    }
}
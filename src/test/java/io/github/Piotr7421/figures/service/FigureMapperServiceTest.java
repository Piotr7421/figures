package io.github.Piotr7421.figures.service;

import io.github.Piotr7421.figures.exception.NoMapperFoundException;
import io.github.Piotr7421.figures.mapper.CircleMapper;
import io.github.Piotr7421.figures.mapper.FigureMapper;
import io.github.Piotr7421.figures.mapper.RectangleMapper;
import io.github.Piotr7421.figures.model.Circle;
import io.github.Piotr7421.figures.model.Figure;
import io.github.Piotr7421.figures.model.Rectangle;
import io.github.Piotr7421.figures.model.dto.CircleDto;
import io.github.Piotr7421.figures.model.dto.FigureDto;
import io.github.Piotr7421.figures.model.dto.RectangleDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FigureMapperServiceTest {
    @Mock
    private CircleMapper circleMapper;
    @Mock
    private RectangleMapper rectangleMapper;
    private FigureMapperService figureMapperService;
    private Map<String, FigureMapper<? extends Figure>> figureMappers;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        figureMappers = new HashMap<>();
        figureMappers.put("CircleMapper", circleMapper);
        figureMappers.put("RectangleMapper", rectangleMapper);
        figureMapperService = new FigureMapperService(figureMappers);
    }

    @Test
    void whenMapCircleToDto_thenSuccess() {
        Circle circle = Circle.builder()
                .id(1)
                .type("Circle")
                .radius(10.0)
                .build();
        CircleDto expectedDto = CircleDto.builder()
                .id(1)
                .type("Circle")
                .radius(10.0)
                .build();

        when(circleMapper.mapToDto(circle)).thenReturn(expectedDto);

        FigureDto resultDto = figureMapperService.mapToDto(circle);

        assertNotNull(resultDto);
        assertInstanceOf(CircleDto.class, resultDto);
        assertEquals(expectedDto, resultDto);
    }

    @Test
    void whenMapRectangleToDto_thenSuccess() {
        Rectangle rectangle = Rectangle.builder()
                .id(1)
                .type("Rectangle")
                .length(10.0)
                .width(5.0)
                .build();
        RectangleDto expectedDto = RectangleDto.builder()
                .id(1)
                .type("Rectangle")
                .length(10.0)
                .width(5.0)
                .build();

        when(rectangleMapper.mapToDto(rectangle)).thenReturn(expectedDto);

        FigureDto resultDto = figureMapperService.mapToDto(rectangle);

        assertNotNull(resultDto);
        assertInstanceOf(RectangleDto.class, resultDto);
        assertEquals(expectedDto, resultDto);
    }

    @Test
    void whenMapUnknownFigureToDto_thenThrowException() {
        Figure unknownFigure = new Figure() {
        };

        Exception exception = assertThrows(NoMapperFoundException.class, () -> {
            figureMapperService.mapToDto(unknownFigure);
        });

        String expectedMessagePart = "_NOT_FOUND";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessagePart));
    }
}
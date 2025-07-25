package com.example.figures.mapper;

import com.example.figures.model.Rectangle;
import com.example.figures.model.dto.RectangleDto;
import org.springframework.stereotype.Component;

@Component("RectangleMapper")
public class RectangleMapper implements FigureMapper<Rectangle> {
    @Override
    public RectangleDto mapToDto(Rectangle rectangle) {
        return RectangleDto.builder()
                .id(rectangle.getId())
                .type(rectangle.getClass().getSimpleName())
                .length(rectangle.getLength())
                .width(rectangle.getWidth())
                .build();
    }
}

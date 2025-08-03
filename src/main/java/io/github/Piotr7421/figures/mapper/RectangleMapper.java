package io.github.Piotr7421.figures.mapper;

import io.github.Piotr7421.figures.model.Rectangle;
import io.github.Piotr7421.figures.model.dto.RectangleDto;
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

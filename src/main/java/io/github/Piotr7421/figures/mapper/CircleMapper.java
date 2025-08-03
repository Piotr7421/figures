package io.github.Piotr7421.figures.mapper;

import io.github.Piotr7421.figures.model.Circle;
import io.github.Piotr7421.figures.model.dto.CircleDto;
import org.springframework.stereotype.Component;

@Component("CircleMapper")
public class CircleMapper implements FigureMapper<Circle> {

    @Override
    public CircleDto mapToDto(Circle circle) {
        return CircleDto.builder()
                .type(circle.getClass().getSimpleName())
                .id(circle.getId())
                .radius(circle.getRadius())
                .build();
    }
}

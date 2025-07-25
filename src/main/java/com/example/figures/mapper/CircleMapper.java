package com.example.figures.mapper;

import com.example.figures.model.Circle;
import com.example.figures.model.dto.CircleDto;
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

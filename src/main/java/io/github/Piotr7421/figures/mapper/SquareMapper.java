package io.github.Piotr7421.figures.mapper;

import io.github.Piotr7421.figures.model.Square;
import io.github.Piotr7421.figures.model.dto.SquareDto;
import org.springframework.stereotype.Component;

@Component("SquareMapper")
public class SquareMapper implements FigureMapper<Square> {
    @Override
    public SquareDto mapToDto(Square square) {
        return SquareDto.builder()
                .id(square.getId())
                .type(square.getClass().getSimpleName())
                .side(square.getSide())
                .build();
    }
}

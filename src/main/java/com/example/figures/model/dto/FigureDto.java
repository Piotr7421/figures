package com.example.figures.model.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class FigureDto {

    private int id;
    private String type;
}

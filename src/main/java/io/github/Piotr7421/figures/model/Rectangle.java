package io.github.Piotr7421.figures.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("Rectangle")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Rectangle extends Figure {

    @Positive(message = "NOT_POSITIVE")
    private double length;

    @Positive(message = "NOT_POSITIVE")
    private double width;
}

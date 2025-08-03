package io.github.Piotr7421.figures.repository;

import io.github.Piotr7421.figures.model.Figure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FigureRepository extends JpaRepository<Figure, Integer>, JpaSpecificationExecutor<Figure> {
}

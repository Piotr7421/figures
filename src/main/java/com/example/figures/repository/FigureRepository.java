package com.example.figures.repository;

import com.example.figures.model.Figure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FigureRepository extends JpaRepository<Figure, Integer>, JpaSpecificationExecutor<Figure> {
}

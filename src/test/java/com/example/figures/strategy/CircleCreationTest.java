package com.example.figures.strategy;

import org.junit.jupiter.api.Test;
import com.example.figures.model.Circle;
import com.example.figures.model.command.CreateFigureCommand;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CircleCreationTest {

    @Test
    void testCreateCircle() {
        Map<String, String> params = new HashMap<>();
        params.put("radius", "5.0");
        CreateFigureCommand command = new CreateFigureCommand("Circle", params);
        CircleCreation circleCreation = new CircleCreation();
        Circle result = circleCreation.create(command);
        assertEquals("Circle", result.getType());
        assertEquals(5.0, result.getRadius());
    }
}
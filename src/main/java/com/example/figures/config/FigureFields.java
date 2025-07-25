package com.example.figures.config;

import com.example.figures.model.Figure;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@Slf4j
public class FigureFields {

    @Bean
    public Map<String, List<String>> reflectionFields() {

        Reflections reflections = new Reflections("com.example.figures.model");
        Set<Class<? extends Figure>> subTypes = reflections.getSubTypesOf(Figure.class);

        Map<String, List<String>> figures = subTypes.stream()
                .collect(Collectors.toMap(
                        Class::getSimpleName,
                        subType -> Stream.concat(
                                        Arrays.stream(Figure.class.getDeclaredFields()),
                                        Arrays.stream(subType.getDeclaredFields())
                                )
                                .map(Field::getName)
                                .distinct()
                                .toList()
                ));

        log.info("FigureFields Reflections >>>>>>>>>>>>>>>>>>>>> {}", figures);
        return figures;
    }

    @Bean
    public long numberOfFieldsInAbstractClass() {
        return Arrays.stream(Figure.class.getDeclaredFields()).count();
    }
}


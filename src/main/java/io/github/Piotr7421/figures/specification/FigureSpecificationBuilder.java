package io.github.Piotr7421.figures.specification;

import io.github.Piotr7421.figures.model.Figure;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class FigureSpecificationBuilder {
    private final List<SearchCriteria> params = new ArrayList<>();

    public FigureSpecificationBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<Figure> build() {
        if (params.isEmpty()) {
            return null;
        }

        List<FigureSpecification> specs = params.stream()
                .map(FigureSpecification::new)
                .toList();

        Specification<Figure> result = specs.get(0);

        for (int i = 1; i < specs.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }
        return result;
    }
}

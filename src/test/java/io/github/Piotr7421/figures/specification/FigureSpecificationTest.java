package io.github.Piotr7421.figures.specification;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import io.github.Piotr7421.figures.model.Circle;
import io.github.Piotr7421.figures.model.Rectangle;
import io.github.Piotr7421.figures.model.Square;
import io.github.Piotr7421.figures.repository.FigureRepository;

import static org.hamcrest.Matchers.hasKey;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class FigureSpecificationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    FigureRepository figureRepository;

    @BeforeEach
    void init() {
        Circle circle1 = Circle.builder()
                .radius(34.2)
                .type("Circle")
                .build();
        figureRepository.save(circle1);
        Circle circle2 = Circle.builder()
                .radius(14.9)
                .type("Circle")
                .build();
        figureRepository.save(circle2);
        Rectangle rectangle1 = Rectangle.builder()
                .length(10.9)
                .width(8.4)
                .type("Rectangle")
                .build();
        figureRepository.save(rectangle1);
        Rectangle rectangle2 = Rectangle.builder()
                .length(23.4)
                .width(16.3)
                .type("Rectangle")
                .build();
        figureRepository.save(rectangle2);
        Square square1 = Square.builder()
                .side(13.3)
                .type("Square")
                .build();
        figureRepository.save(square1);
        Square square2 = Square.builder()
                .side(25.8)
                .type("Square")
                .build();
        figureRepository.save(square2);
    }

    @AfterEach
     void cleanup() {
        figureRepository.deleteAll();
    }

    @Test
    void findAllWithSearchCriteria_ShouldReturnCircleOfRadiusLessOrEqualThan30() throws Exception {

        String search = "radius:<:30,type:=:Circle";

        mockMvc.perform(get("/api/v1/figures")
                        .param("search", search)
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,asc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").hasJsonPath())// .isArray())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$", hasKey("content")))
                .andExpect(jsonPath("$.content.[0].type").value("Circle"))
                .andExpect(jsonPath("$.content.[0].radius").value(14.9))
                .andExpect(jsonPath("$.content.size()").value(1));
    }

    @Test
    void findAllWithSearchCriteria_ShouldReturnRectangleWithLengthGreaterOrEqualThan15AndIdGreaterOrEqualThan2() throws Exception {

        String search = "type:=:Rectangle,length:>:15,id:>:2";

        mockMvc.perform(get("/api/v1/figures")
                        .param("search", search)
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,asc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").hasJsonPath())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$", hasKey("content")))
                .andExpect(jsonPath("$.content.[0].type").value("Rectangle"))
                .andExpect(jsonPath("$.content.[0].length").value(23.4))
                .andExpect(jsonPath("$.content.[0].width").value(16.3))
                .andExpect(jsonPath("$.content.size()").value(1));
    }

    @Test
    void findAllWithSearchCriteria_ShouldReturnFigureWithTypeLikeLe() throws Exception {

        String search = "type:*:le";

        mockMvc.perform(get("/api/v1/figures")
                        .param("search", search)
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,asc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").hasJsonPath())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$", hasKey("content")))
                .andExpect(jsonPath("$.content.[0].type").value("Circle"))
                .andExpect(jsonPath("$.content.[0].radius").value(34.2))
                .andExpect(jsonPath("$.content.[3].type").value("Rectangle"))
                .andExpect(jsonPath("$.content.[3].length").value(23.4))
                .andExpect(jsonPath("$.content.[3].width").value(16.3))
                .andExpect(jsonPath("$.content.[3].side").doesNotExist())
                .andExpect(jsonPath("$.content.size()").value(4));
    }

    @Test
    void findAllWithSearchCriteria_ShouldReturnSquareWithSideGreaterOrEqualThan20() throws Exception {

        String search = "side:>:20";

        mockMvc.perform(get("/api/v1/figures")
                        .param("search", search)
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,asc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").hasJsonPath())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$", hasKey("content")))
                .andExpect(jsonPath("$.content.[0].type").value("Square"))
                .andExpect(jsonPath("$.content.[0].side").value(25.8))
                .andExpect(jsonPath("$.content.[1].side").doesNotExist())
                .andExpect(jsonPath("$.content.size()").value(1));
    }

    @Test
    void findAllWithSearchCriteria_ShouldNotReturnAnyFigure() throws Exception {

        String search = "type:*:TRapeze";

        mockMvc.perform(get("/api/v1/figures")
                        .param("search", search)
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,asc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").hasJsonPath())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$", hasKey("content")))
                .andExpect(jsonPath("$.content.size()").value(0));
    }

    @Test
    void findAll_IncorrectKeySearchFieldName_ShouldReturnError4xxAndValidationMessage() throws Exception {

        String search = "type:=:rectangle,lengthHHHHHHHHHHHH:>:14";

        mockMvc.perform(get("/api/v1/figures")
                        .param("search", search)
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,asc"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$").hasJsonPath())
                .andExpect(jsonPath("$.message").value("INCORRECT_SEARCH_KEY : lengthHHHHHHHHHHHH "));
    }

    @Test
    void findAll_IncorrectKeySearchFigureType_ShouldReturnError4xxAndValidationMessage() throws Exception {

        String search = "tyRRRRRRRRRpe:=:Rectangle,length:>:14";

        mockMvc.perform(get("/api/v1/figures")
                        .param("search", search)
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,asc"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$").hasJsonPath())
                .andExpect(jsonPath("$.message").value("INCORRECT_SEARCH_KEY : tyRRRRRRRRRpe "));
    }

    @Test
    void findAll_NoOperationSearch_ShouldReturnError4xxAndValidationMessage() throws Exception {

        String search = "type:=:rectangle,length::14";

        mockMvc.perform(get("/api/v1/figures")
                        .param("search", search)
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,asc"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$").hasJsonPath())
                .andExpect(jsonPath("$.message").value("NULL_SEARCH_OPERATION"));
    }

    @Test
    void findAll_IncorrectSearchOperation_ShouldReturnError4xxAndValidationMessage() throws Exception {

        String search = "type:=:rectangle,length:%:14";

        mockMvc.perform(get("/api/v1/figures")
                        .param("search", search)
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,asc"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$").hasJsonPath())
                .andExpect(jsonPath("$.message").value("INCORRECT_SEARCH_OPERATION : % "));
    }
}
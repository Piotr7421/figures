package io.github.Piotr7421.figures.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import io.github.Piotr7421.figures.model.Circle;
import io.github.Piotr7421.figures.model.Rectangle;
import io.github.Piotr7421.figures.model.Square;
import io.github.Piotr7421.figures.model.command.CreateFigureCommand;
import io.github.Piotr7421.figures.repository.FigureRepository;

import java.util.Map;

import static org.hamcrest.Matchers.hasKey;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class FigureControllerTest {

    private CreateFigureCommand command;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
    void testFindAll_ShouldReturnAllSixFiguresSortedByIdDescending() throws Exception {
        mockMvc.perform(get("/api/v1/figures")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,desc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").hasJsonPath())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$", hasKey("content")))

                .andExpect(jsonPath("$.content.[0].type").value("Square"))
                .andExpect(jsonPath("$.content.[0].side").value(25.8))

                .andExpect(jsonPath("$.content.[1].type").value("Square"))
                .andExpect(jsonPath("$.content.[1].side").value(13.3))

                .andExpect(jsonPath("$.content.[2].type").value("Rectangle"))
                .andExpect(jsonPath("$.content.[2].length").value(23.4))
                .andExpect(jsonPath("$.content.[2].width").value(16.3))

                .andExpect(jsonPath("$.content.[3].type").value("Rectangle"))
                .andExpect(jsonPath("$.content.[3].length").value(10.9))
                .andExpect(jsonPath("$.content.[3].width").value(8.4))

                .andExpect(jsonPath("$.content.[4].type").value("Circle"))
                .andExpect(jsonPath("$.content.[4].radius").value(14.9))

                .andExpect(jsonPath("$.content.[5].type").value("Circle"))
                .andExpect(jsonPath("$.content.[5].radius").value(34.2))

                .andExpect(jsonPath("$.content.size()").value(6))
                .andExpect(jsonPath("$.pageable.pageNumber").value(0))
                .andExpect(jsonPath("$.pageable.pageSize").value(10))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.totalElements").value(6));
    }

    @Test
    void testCreate_ShouldSaveNewFigureOfCircleType() throws Exception {
//        checking how many figures have been saved before adding new one
        mockMvc.perform(get("/api/v1/figures")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,desc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").hasJsonPath())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$", hasKey("content")))
                .andExpect(jsonPath("$.totalElements").value(6))
                .andExpect(jsonPath("$.content.size()").value(6));
//        it follows that there are six figures

        command = new CreateFigureCommand();
        command.setType("Circle");
        command.setParams(Map.of("radius", "55.5"));
        mockMvc.perform(post("/api/v1/figures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.type").value("Circle"))
                .andExpect(jsonPath("$.radius").value(55.5));

//        checking the number of figures after writing a new figure
        mockMvc.perform(get("/api/v1/figures")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,desc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").hasJsonPath())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$", hasKey("content")))
                .andExpect(jsonPath("$.content.size()").value(7))
                .andExpect(jsonPath("$.totalElements").value(7));
//        it follows that there are seven figures
    }

    @Test
    void testCreate_FigureTypeNotSupported_ShouldReturnError4xxAndValidationMessages() throws Exception {

        command = new CreateFigureCommand();
        command.setType("TRapeze");
        command.setParams(Map.of("radius", "55.5"));
        mockMvc.perform(post("/api/v1/figures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("Validation error"))
                .andExpect(jsonPath("$.violations.[*].field", Matchers.hasItem("type")))
                .andExpect(jsonPath("$.violations.[*].message", Matchers.hasItem("PATTERN_MISMATCH_\\p{Lu}\\p{Ll}{1,19}")))
                .andExpect(jsonPath("$.violations.[*].message", Matchers.hasItem("FIGURE_TYPE_NOT_SUPPORTED")));
    }

    @Test
    void testCreate_MismatchedFieldNameWithFigureType_ShouldReturnError4xxAndValidationMessages() throws Exception {

        //      Square has side, not radius
        command = new CreateFigureCommand();
        command.setType("Square");
        command.setParams(Map.of("radius", "55.5"));
        mockMvc.perform(post("/api/v1/figures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("INCORRECT_Square_PARAMS"));
    }

    @Test
    void testCreate_IncorrectNumberOfParameters_ShouldReturnError4xxAndValidationMessage() throws Exception {

        //        lack of width
        command = new CreateFigureCommand();
        command.setType("Rectangle");
        command.setParams(Map.of("length", "55.5"));
        mockMvc.perform(post("/api/v1/figures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("INCORRECT_SIZE_OF_Rectangle_PARAMS"));
    }

    @Test
    void testCreate_IncorrectFigureFieldInParams_ShouldReturnError4xxAndValidationMessage() throws Exception {

        command = new CreateFigureCommand();
        command.setType("Rectangle");
        command.setParams(Map.of("length", "14.3", "widthHHHHHHHHHHHHHHH", "23.9"));
        mockMvc.perform(post("/api/v1/figures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("INCORRECT_Rectangle_PARAMS"));
    }

    @Test
    void testCreate_NoParams_ShouldReturnError4xxAndValidationMessages() throws Exception {

        command = new CreateFigureCommand();
        command.setType("Rectangle");
        mockMvc.perform(post("/api/v1/figures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("Validation error"))
                .andExpect(jsonPath("$.violations.[*].field", Matchers.hasItem("params")))
                .andExpect(jsonPath("$.violations.[*].message", Matchers.hasItem("FIGURE_PARAMS_NOT_SUPPORTED")))
                .andExpect(jsonPath("$.violations.[*].message", Matchers.hasItem("NULL_PARAMS")))
                .andExpect(jsonPath("$.violations.[*].message", Matchers.hasItem("EMPTY_PARAMS")));
    }

    @Test
    void testCreate_EmptyParams_ShouldReturnError4xxAndValidationMessage() throws Exception {

        command = new CreateFigureCommand();
        command.setType("Rectangle");
        command.setParams(Map.of());
        mockMvc.perform(post("/api/v1/figures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("Validation error"))
                .andExpect(jsonPath("$.violations.[*].field", Matchers.hasItem("params")))
                .andExpect(jsonPath("$.violations.[*].message", Matchers.hasItem("EMPTY_PARAMS")))
                .andExpect(jsonPath("$.violations.[*].message", Matchers.hasItem("FIGURE_PARAMS_NOT_SUPPORTED")));
    }

    @Test
    void testCreate_NotPositiveValue_ShouldReturnError4xxAndValidationMessage() throws Exception {

        command = new CreateFigureCommand();
        command.setType("Rectangle");
        command.setParams(Map.of());
        command.setParams(Map.of("length", "-14.3", "width", "-23.9"));
        mockMvc.perform(post("/api/v1/figures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("Validation error"))
                .andExpect(jsonPath("$.violations.[*].field", Matchers.hasItem("length")))
                .andExpect(jsonPath("$.violations.[*].message", Matchers.hasItem("NOT_POSITIVE")))
                .andExpect(jsonPath("$.violations.[*].field", Matchers.hasItem("width")))
                .andExpect(jsonPath("$.violations.[*].message", Matchers.hasItem("NOT_POSITIVE")));
    }
}
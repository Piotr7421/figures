package com.example.figures.controller;

import com.example.figures.config.FigureFields;
import com.example.figures.model.command.CreateFigureCommand;
import com.example.figures.model.dto.FigureDto;
import com.example.figures.service.FigureService;
import com.example.figures.specification.SpecificationCreation;
import com.example.figures.validation.CommandParamsValidation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/figures")
public class FigureController {

    private final FigureService service;
    private final FigureFields figureFields;
    private final CommandParamsValidation paramsValidation;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FigureDto create(@RequestBody @Valid CreateFigureCommand command) {
        paramsValidation.validParams(command, figureFields);
        return service.create(command);
    }

    @GetMapping
    public Page<FigureDto> findAll(@RequestParam(value = "search", required = false) String search, Pageable pageable) {
        return service.findAll(SpecificationCreation.create(search, figureFields), pageable);
    }
}

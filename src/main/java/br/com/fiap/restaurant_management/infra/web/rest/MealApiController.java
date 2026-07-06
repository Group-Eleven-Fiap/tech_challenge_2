package br.com.fiap.restaurant_management.infra.web.rest;

import br.com.fiap.restaurant_management.core.controller.MealController;
import br.com.fiap.restaurant_management.core.dto.MealDTO;
import br.com.fiap.restaurant_management.infra.web.dto.MealInput;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/meal")
@RequiredArgsConstructor
public class MealApiController {

    private final MealController mealController;

    @PostMapping
    public MealDTO create(@Valid @RequestBody MealInput input) {
        return mealController.createMeal(mapToDto(input));
    }

    private MealDTO mapToDto(MealInput input) {
        return new MealDTO(
                input.getName(),
                input.getDescription(),
                input.getPrice(),
                input.getPictureUrl());
    }
}

package br.com.fiap.restaurant_management.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MealDTO {

    private String name;
    private String description;
    private Double price;
    private String pictureUrl;
}

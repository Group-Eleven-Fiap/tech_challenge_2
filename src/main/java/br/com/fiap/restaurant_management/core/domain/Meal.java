package br.com.fiap.restaurant_management.core.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class Meal {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private String pictureUrl;

    public Meal(String name, String description, Double price, String pictureUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.pictureUrl = pictureUrl;
    }

    // Valida que o preço não pode ser null ou igual a 0
    public boolean validatePrice() {
        // Retorna true quando o preço for válido (não é nulo e não é exatamente 0.0)
        return price != null && Double.compare(price, 0.0) != 0;
    }
}

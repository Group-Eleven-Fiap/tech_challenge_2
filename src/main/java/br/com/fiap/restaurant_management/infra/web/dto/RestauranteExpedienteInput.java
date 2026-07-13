package br.com.fiap.restaurant_management.infra.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class RestauranteExpedienteInput {

    @NotNull
    @Positive
    private Long idRestaurante;

    @NotBlank
    private String diaSemana;

    @NotNull
    private LocalTime horaAbertura;

    @NotNull
    private LocalTime horaFechamento;
}

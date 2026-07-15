package br.com.fiap.restaurant_management.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class RestauranteExpedienteDTO {

    private Long id;
    private Long idRestaurante;
    private String diaSemana;
    private LocalTime horaAbertura;
    private LocalTime horaFechamento;
}

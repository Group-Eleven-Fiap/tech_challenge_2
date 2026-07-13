package br.com.fiap.restaurant_management.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class RestauranteExpedienteDTO {

    private UUID id;
    private Long idRestaurante;
    private String diaSemana;
    private LocalTime horaAbertura;
    private LocalTime horaFechamento;
}

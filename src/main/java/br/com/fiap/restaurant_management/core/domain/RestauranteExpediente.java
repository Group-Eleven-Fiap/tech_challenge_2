package br.com.fiap.restaurant_management.core.domain;

import lombok.Getter;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Getter
public class RestauranteExpediente {

    private static final List<String> DIAS_VALIDOS = List.of(
            "SEGUNDA", "TERCA", "QUARTA", "QUINTA", "SEXTA", "SABADO", "DOMINGO");

    private Long id;
    private Long idRestaurante;
    private String diaSemana;
    private LocalTime horaAbertura;
    private LocalTime horaFechamento;

    public RestauranteExpediente(Long idRestaurante, String diaSemana, LocalTime horaAbertura, LocalTime horaFechamento) {
        this.idRestaurante = idRestaurante;
        this.diaSemana = diaSemana;
        this.horaAbertura = horaAbertura;
        this.horaFechamento = horaFechamento;
    }

    public RestauranteExpediente(Long id, Long idRestaurante, String diaSemana, LocalTime horaAbertura, LocalTime horaFechamento) {
        this.id = id;
        this.idRestaurante = idRestaurante;
        this.diaSemana = diaSemana;
        this.horaAbertura = horaAbertura;
        this.horaFechamento = horaFechamento;
    }

    // Valida que o restaurante foi informado
    public boolean validateIdRestaurante() {
        return idRestaurante != null && idRestaurante > 0;
    }

    // Valida que o dia da semana informado está entre os valores aceitos (mesma regra do CHECK ck_dia_semana)
    public boolean validateDiaSemana() {
        return diaSemana != null && DIAS_VALIDOS.contains(diaSemana.toUpperCase());
    }

    // Valida que os horários foram informados e que o fechamento é posterior à abertura
    public boolean validateHorarios() {
        return horaAbertura != null
                && horaFechamento != null
                && horaFechamento.isAfter(horaAbertura);
    }
}

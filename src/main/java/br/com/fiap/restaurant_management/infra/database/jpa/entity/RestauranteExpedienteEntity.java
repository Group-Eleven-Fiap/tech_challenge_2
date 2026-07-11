package br.com.fiap.restaurant_management.infra.database.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_RESTAURANTE_EXPEDIENTE",
        uniqueConstraints = @UniqueConstraint(
                name = "uq_restaurante_dia",
                columnNames = {"id_restaurante", "dia_semana"}))
public class RestauranteExpedienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "id_restaurante", nullable = false)
    private UUID idRestaurante;

    @Column(name = "dia_semana", nullable = false, length = 15)
    private String diaSemana;

    @Column(name = "hora_abertura", nullable = false)
    private LocalTime horaAbertura;

    @Column(name = "hora_fechamento", nullable = false)
    private LocalTime horaFechamento;

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizado_em", nullable = false)
    private LocalDateTime atualizadoEm;
}

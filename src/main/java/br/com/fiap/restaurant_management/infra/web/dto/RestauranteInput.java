package br.com.fiap.restaurant_management.infra.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RestauranteInput {

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String nome;

    @NotBlank(message = "Endereço é obrigatório")
    @Size(max = 255, message = "Endereço deve ter no máximo 255 caracteres")
    private String endereco;

    @NotBlank(message = "Tipo de cozinha é obrigatório")
    @Size(max = 100, message = "Tipo de cozinha deve ter no máximo 100 caracteres")
    private String tipoCozinha;

    @NotBlank(message = "Horário de funcionamento é obrigatório")
    @Size(max = 100, message = "Horário de funcionamento deve ter no máximo 100 caracteres")
    private String horarioFuncionamento;

    @NotNull(message = "Dono do restaurante é obrigatório")
    @Positive(message = "Identificador do dono deve ser positivo")
    private Long idDono;
}

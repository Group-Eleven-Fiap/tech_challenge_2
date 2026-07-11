package br.com.fiap.restaurant_management.core.mapper;

import br.com.fiap.restaurant_management.core.domain.RestauranteExpediente;
import br.com.fiap.restaurant_management.core.dto.RestauranteExpedienteDTO;
import org.springframework.stereotype.Component;

@Component
public class RestauranteExpedienteMapper {

    public RestauranteExpediente toDomain(RestauranteExpedienteDTO input) {
        return new RestauranteExpediente(
                input.getId(),
                input.getIdRestaurante(),
                input.getDiaSemana(),
                input.getHoraAbertura(),
                input.getHoraFechamento());
    }

    public RestauranteExpedienteDTO toDTO(RestauranteExpediente expediente) {
        return new RestauranteExpedienteDTO(
                expediente.getId(),
                expediente.getIdRestaurante(),
                expediente.getDiaSemana(),
                expediente.getHoraAbertura(),
                expediente.getHoraFechamento());
    }
}

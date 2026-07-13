package br.com.fiap.restaurant_management.core.mapper;

import br.com.fiap.restaurant_management.core.domain.Restaurante;
import br.com.fiap.restaurant_management.core.dto.RestauranteDTO;
import org.springframework.stereotype.Component;

@Component
public class RestauranteMapper {

    public Restaurante toDomain(RestauranteDTO dto) {
        return new Restaurante(
                dto.getId(), dto.getNome(), dto.getEndereco(), dto.getTipoCozinha(),
                dto.getHorarioFuncionamento(), dto.getIdDono(), dto.getCriadoEm(), dto.getAtualizadoEm());
    }

    public RestauranteDTO toDTO(Restaurante restaurante) {
        return new RestauranteDTO(
                restaurante.getId(), restaurante.getNome(), restaurante.getEndereco(), restaurante.getTipoCozinha(),
                restaurante.getHorarioFuncionamento(), restaurante.getIdDono(),
                restaurante.getCriadoEm(), restaurante.getAtualizadoEm());
    }
}

package br.com.fiap.restaurant_management.core.mapper;

import br.com.fiap.restaurant_management.core.domain.TipoUsuario;
import br.com.fiap.restaurant_management.core.dto.TipoUsuarioInputDto;
import br.com.fiap.restaurant_management.core.dto.TipoUsuarioOutputDto;
import org.springframework.stereotype.Component;

@Component
public class TipoUsuarioMapper {

    public TipoUsuario map(TipoUsuarioInputDto input) {
        return new TipoUsuario(input.getNome());
    }

    public TipoUsuarioOutputDto map(TipoUsuario domain) {
        return new TipoUsuarioOutputDto(domain.getId(), domain.getNome());
    }
}

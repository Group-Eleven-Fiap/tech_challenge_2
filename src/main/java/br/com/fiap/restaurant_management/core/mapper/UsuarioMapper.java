package br.com.fiap.restaurant_management.core.mapper;

import br.com.fiap.restaurant_management.core.domain.TipoUsuario;
import br.com.fiap.restaurant_management.core.domain.Usuario;
import br.com.fiap.restaurant_management.core.dto.UsuarioInputDto;
import br.com.fiap.restaurant_management.core.dto.UsuarioOutputDto;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    private final TipoUsuarioMapper tipoUsuarioMapper;

    public UsuarioMapper(TipoUsuarioMapper tipoUsuarioMapper) {
        this.tipoUsuarioMapper = tipoUsuarioMapper;
    }

    public Usuario map(UsuarioInputDto input, TipoUsuario tipoUsuario) {
        return new Usuario(
                input.getNome(),
                input.getEmail(),
                input.getLogin(),
                input.getSenha(),
                tipoUsuario);
    }

    public UsuarioOutputDto map(Usuario domain) {
        return new UsuarioOutputDto(
                domain.getId(),
                domain.getNome(),
                domain.getEmail(),
                domain.getLogin(),
                tipoUsuarioMapper.map(domain.getTipoUsuario()),
                domain.getCriadoEm(),
                domain.getAtualizadoEm());
    }
}

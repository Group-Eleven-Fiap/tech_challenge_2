package br.com.fiap.restaurant_management.core.usecase;

import br.com.fiap.restaurant_management.core.domain.TipoUsuario;
import br.com.fiap.restaurant_management.core.domain.Usuario;
import br.com.fiap.restaurant_management.core.dto.UsuarioInputDto;
import br.com.fiap.restaurant_management.core.dto.UsuarioOutputDto;
import br.com.fiap.restaurant_management.core.exception.ResourceNotFoundException;
import br.com.fiap.restaurant_management.core.gateway.TipoUsuarioGateway;
import br.com.fiap.restaurant_management.core.gateway.UsuarioGateway;
import br.com.fiap.restaurant_management.core.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UsuarioUseCaseImpl implements UsuarioUseCase{

    private final UsuarioMapper mapper;
    private final UsuarioGateway gateway;
    private final TipoUsuarioGateway tipoUsuarioGateway;

    @Override
    public List<UsuarioOutputDto> consultar() {
        return gateway.consultar().stream()
                .map(mapper::map)
                .toList();
    }

    @Override
    public Long criar(UsuarioInputDto input) {
        var tipoUsuario = consultarTipoUsuarioPorId(input.getTipoUsuarioId());
        return gateway.criar(mapper.map(input, tipoUsuario));
    }

    @Override
    public void excluir(Long id) {
        gateway.consultarPorId(id).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        gateway.excluir(id);
    }

    @Override
    public void atualizar(Long id, UsuarioInputDto input) {
        var usuario = consultarPorId(id);
        var tipoUsuario = consultarTipoUsuarioPorId(input.getTipoUsuarioId());

        var atualizado = new Usuario(
                usuario.getId(),
                input.getNome(),
                input.getEmail(),
                input.getLogin(),
                input.getSenha(),
                tipoUsuario);

        gateway.atualizar(atualizado);
    }

    @Override
    public void atualizarTipoUsuario(Long usuarioId, Long tipoUsuarioId) {
        var usuario = consultarPorId(usuarioId);
        var tipoUsuario = consultarTipoUsuarioPorId(tipoUsuarioId);

        usuario.atualizarTipoUsuario(tipoUsuario);

        gateway.atualizar(usuario);
    }

    private Usuario consultarPorId(Long id) {
        return gateway.consultarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }

    private TipoUsuario consultarTipoUsuarioPorId(Long tipoUsuarioId) {
        return tipoUsuarioGateway.consultarPorId(tipoUsuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de Usuário não encontrado"));
    }
}

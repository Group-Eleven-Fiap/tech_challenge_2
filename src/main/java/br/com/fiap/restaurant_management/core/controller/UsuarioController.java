package br.com.fiap.restaurant_management.core.controller;

import br.com.fiap.restaurant_management.core.dto.UsuarioInputDto;
import br.com.fiap.restaurant_management.core.dto.UsuarioOutputDto;
import br.com.fiap.restaurant_management.core.usecase.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class UsuarioController {

    private final CriarUsuarioUseCase criarUsuarioUseCase;
    private final AtualizarUsuarioUseCase atualizarUsuarioUseCase;
    private final AtualizarUsuarioTipoUsuarioUseCase atualizarUsuarioTipoUsuarioUseCase;
    private final ExcluirUsuarioUseCase excluirUsuarioUseCase;
    private final ConsultarUsuarioUseCase consultarUsuarioUseCase;

    public Long criar(UsuarioInputDto input) {
        return criarUsuarioUseCase.criar(input);
    }

    public void atualizar(Long id, UsuarioInputDto input) {
        atualizarUsuarioUseCase.atualizar(id, input);
    }

    public void excluir(Long id) {
        excluirUsuarioUseCase.excluir(id);
    }

    public List<UsuarioOutputDto> consultar() {
        return consultarUsuarioUseCase.consultar();
    }

    public void atualizarUsuarioTipoUsuario(Long usuarioId, Long tipoUsuarioId) {
        atualizarUsuarioTipoUsuarioUseCase.atualizar(usuarioId, tipoUsuarioId);
    }
}

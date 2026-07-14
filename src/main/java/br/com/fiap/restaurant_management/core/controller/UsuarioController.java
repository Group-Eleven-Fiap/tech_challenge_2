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

    private final UsuarioUseCase useCase;

    public Long criar(UsuarioInputDto input) {
        return useCase.criar(input);
    }

    public void atualizar(Long id, UsuarioInputDto input) {
        useCase.atualizar(id, input);
    }

    public void excluir(Long id) {
        useCase.excluir(id);
    }

    public List<UsuarioOutputDto> consultar() {
        return useCase.consultar();
    }

    public void atualizarTipoUsuario(Long usuarioId, Long tipoUsuarioId) {
        useCase.atualizarTipoUsuario(usuarioId, tipoUsuarioId);
    }
}

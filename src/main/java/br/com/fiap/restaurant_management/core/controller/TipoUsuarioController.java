package br.com.fiap.restaurant_management.core.controller;

import br.com.fiap.restaurant_management.core.dto.TipoUsuarioInputDto;
import br.com.fiap.restaurant_management.core.dto.TipoUsuarioOutputDto;
import br.com.fiap.restaurant_management.core.usecase.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class TipoUsuarioController {

    private final TipoUsuarioUseCase usecase;

    public Long criar(TipoUsuarioInputDto input) {
        return usecase.criar(input);
    }

    public void atualizar(Long id, TipoUsuarioInputDto input) {
        usecase.atualizar(id, input);
    }

    public void excluir(Long id) {
        usecase.excluir(id);
    }

    public List<TipoUsuarioOutputDto> consultar() {
        return usecase.consultar();
    }
}

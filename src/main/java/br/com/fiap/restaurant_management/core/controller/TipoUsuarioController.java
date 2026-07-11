package br.com.fiap.restaurant_management.core.controller;

import br.com.fiap.restaurant_management.core.dto.TipoUsuarioInputDto;
import br.com.fiap.restaurant_management.core.dto.TipoUsuarioOutputDto;
import br.com.fiap.restaurant_management.core.usecase.AtualizarTipoUsuarioUseCase;
import br.com.fiap.restaurant_management.core.usecase.ConsultarTipoUsuarioUseCase;
import br.com.fiap.restaurant_management.core.usecase.CriarTipoUsuarioUseCase;
import br.com.fiap.restaurant_management.core.usecase.ExcluirTipoUsuarioUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class TipoUsuarioController {

    private final CriarTipoUsuarioUseCase criarTipoUsuarioUseCase;
    private final AtualizarTipoUsuarioUseCase atualizarTipoUsuarioUseCase;
    private final ExcluirTipoUsuarioUseCase excluirTipoUsuarioUseCase;
    private final ConsultarTipoUsuarioUseCase consultarTipoUsuarioUseCase;

    public Long criar(TipoUsuarioInputDto input) {
        return criarTipoUsuarioUseCase.criar(input);
    }

    public void atualizar(Long id, TipoUsuarioInputDto input) {
        atualizarTipoUsuarioUseCase.atualizar(id, input);
    }

    public void excluir(Long id) {
        excluirTipoUsuarioUseCase.excluir(id);
    }

    public List<TipoUsuarioOutputDto> consultar() {
        return consultarTipoUsuarioUseCase.consultar();
    }
}

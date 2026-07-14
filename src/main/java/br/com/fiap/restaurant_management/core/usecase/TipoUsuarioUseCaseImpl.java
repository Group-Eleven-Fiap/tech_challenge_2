package br.com.fiap.restaurant_management.core.usecase;

import br.com.fiap.restaurant_management.core.dto.TipoUsuarioInputDto;
import br.com.fiap.restaurant_management.core.dto.TipoUsuarioOutputDto;
import br.com.fiap.restaurant_management.core.exception.BusinessRuleException;
import br.com.fiap.restaurant_management.core.gateway.TipoUsuarioGateway;
import br.com.fiap.restaurant_management.core.mapper.TipoUsuarioMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TipoUsuarioUseCaseImpl implements TipoUsuarioUseCase {

    private final TipoUsuarioMapper mapper;
    private final TipoUsuarioGateway gateway;

    @Override
    public void atualizar(Long id, TipoUsuarioInputDto input) {

        var tipoUsuario = gateway.consultarPorId(id)
                .orElseThrow(() -> new BusinessRuleException("Tipo de Usuário não encontrado"));

        tipoUsuario.atualizarNome(input.getNome());

        gateway.atualizar(tipoUsuario);
    }

    @Override
    public void excluir(Long id) {
        gateway.consultarPorId(id)
                .orElseThrow(() -> new BusinessRuleException("Tipo de Usuário não encontrado"));

        gateway.excluir(id);
    }

    public Long criar(TipoUsuarioInputDto input) {
        return gateway.criar(mapper.map(input));
    }

    @Override
    public List<TipoUsuarioOutputDto> consultar() {
        return gateway.consultar().stream()
                .map(mapper::map)
                .toList();
    }

}

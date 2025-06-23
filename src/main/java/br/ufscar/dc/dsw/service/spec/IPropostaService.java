package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.Proposta;
import br.ufscar.dc.dsw.domain.Cliente;

public interface IPropostaService {
    Proposta buscarPorId(Long id);
    List<Proposta> buscarTodosPorCliente(Cliente cliente);
    void salvar (Proposta proposta);
}

package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.Loja;
import br.ufscar.dc.dsw.domain.Proposta;
import br.ufscar.dc.dsw.domain.Cliente;

public interface IPropostaService {
    Proposta buscarPorId(Long id);
    List<Proposta> buscarTodosPorCliente(Cliente cliente);
    List<Proposta> buscarTodosPorLoja(Loja loja);
    void salvar (Proposta proposta);
    void excluir(Long id);
    public boolean temPropostaAbertaParaCliente(Cliente cliente);
}

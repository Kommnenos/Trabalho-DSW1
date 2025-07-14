package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.Loja;
import br.ufscar.dc.dsw.domain.Proposta;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Veiculo;

public interface IPropostaService {
    Proposta buscarPorId(Long id);
    List<Proposta> buscarTodosPorCliente(Cliente cliente);
    List<Proposta> buscarTodosPorLoja(Loja loja);
    List<Proposta> buscarTodosPorVeiculo(Veiculo veiculo);
    void salvar (Proposta proposta);
    void excluir(Long id);
    void excluirTodosPorCliente(Cliente cliente);
    public boolean temPropostaAbertaParaCliente(Cliente cliente);
    public boolean temPropostaAbertaParaVeiculo(Veiculo veiculo);
}

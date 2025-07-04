package br.ufscar.dc.dsw.service.impl;

import java.util.List;

import br.ufscar.dc.dsw.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.dsw.dao.IPropostaDAO;
import br.ufscar.dc.dsw.service.spec.IPropostaService;

@Service
@Transactional(readOnly = false)
public class PropostaService implements IPropostaService{
    @Autowired
    IPropostaDAO dao;

    public void salvar(Proposta proposta) {
        dao.save(proposta);
    }

    public void excluir(Long id) {
        dao.deleteById(id);
    }

    public void excluirTodosPorCliente(Cliente cliente){
        dao.deleteAllByCliente(cliente);
    }

    @Transactional(readOnly = true)
    public Proposta buscarPorId(Long id) {
        return dao.findById(id.longValue());
    }

    @Transactional(readOnly = true)
    public List<Proposta> buscarTodosPorCliente(Cliente cliente) {
        return dao.findAllByCliente(cliente);
    }

    @Transactional(readOnly = true)
    public List<Proposta> buscarTodosPorLoja(Loja loja) {
        return dao.findAllByVeiculoLojaId(loja.getId());
    }

    @Transactional(readOnly = true)
    public List<Proposta> buscarTodosPorVeiculo(Veiculo veiculo) {
        return dao.findAllByVeiculo(veiculo);
    }

    @Transactional(readOnly = true)
    public boolean temPropostaAbertaParaCliente(Cliente cliente) {
        return dao.existsByClienteAndStatus(cliente, StatusProposta.ABERTO);
    }

    @Transactional(readOnly = true)
    public boolean temPropostaAbertaParaVeiculo(Veiculo veiculo) {
        return dao.existsByVeiculoAndStatus(veiculo, StatusProposta.ABERTO);
    }
}

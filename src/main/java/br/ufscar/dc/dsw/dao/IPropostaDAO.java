package br.ufscar.dc.dsw.dao;

import java.util.List;

import br.ufscar.dc.dsw.domain.Loja;
import br.ufscar.dc.dsw.domain.StatusProposta;
import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Proposta;
import br.ufscar.dc.dsw.domain.Cliente;

@SuppressWarnings("unchecked")
public interface IPropostaDAO extends CrudRepository<Proposta, Long>{
    Proposta findById(long id);

    List<Proposta> findAllByCliente(Cliente cliente);
    List<Proposta> findAllByVeiculoLojaId(Long lojaId);

    Proposta save(Proposta proposta);

    boolean existsByClienteAndStatus(Cliente cliente, StatusProposta status);

}

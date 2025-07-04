package br.ufscar.dc.dsw.dao;

import java.util.List;

import br.ufscar.dc.dsw.domain.*;
import org.springframework.data.repository.CrudRepository;

@SuppressWarnings("unchecked")
public interface IPropostaDAO extends CrudRepository<Proposta, Long>{
    Proposta findById(long id);

    List<Proposta> findAllByCliente(Cliente cliente);
    List<Proposta> findAllByVeiculo(Veiculo veiculo);
    List<Proposta> findAllByVeiculoLojaId(Long lojaId);

    Proposta save(Proposta proposta);

    boolean existsByClienteAndStatus(Cliente cliente, StatusProposta status);
    boolean existsByVeiculoAndStatus(Veiculo veiculo, StatusProposta status);

    void deleteByVeiculo(Veiculo veiculo);

    void deleteAllByCliente(Cliente cliente);
}

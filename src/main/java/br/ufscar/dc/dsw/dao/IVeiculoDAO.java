package br.ufscar.dc.dsw.dao;

import java.util.List;

import br.ufscar.dc.dsw.domain.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Veiculo;
import org.springframework.data.repository.query.Param;

@SuppressWarnings("unchecked")
public interface IVeiculoDAO extends CrudRepository<Veiculo, Long>{

	Veiculo findById(long id);

	List<Veiculo> findAll();
	
	Veiculo save(Veiculo veiculo);

	void deleteById(Long id);

	@Query("SELECT v FROM Veiculo v WHERE v.loja.id = :lojaId")
	public List<Veiculo> getVeiculoByLoja(@Param("lojaId") Long lojaId);

	List<Veiculo> findAllByModeloContainingIgnoreCase(String modelo);
	List<Veiculo> findAllByLojaIdAndModeloContainingIgnoreCase(Long lojaId, String modelo);


}
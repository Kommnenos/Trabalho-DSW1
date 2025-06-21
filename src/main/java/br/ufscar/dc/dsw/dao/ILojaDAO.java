package br.ufscar.dc.dsw.dao;

import java.util.List;

import br.ufscar.dc.dsw.domain.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Loja;
import org.springframework.data.repository.query.Param;

@SuppressWarnings("unchecked")
public interface  ILojaDAO extends CrudRepository<Loja, Long>{

	Loja findById(long id);
	Loja findByCNPJ (String CNPJ);
	List<Loja> findAll();
	Loja save(Loja loja);
	void deleteById(Long id);

	@Query("SELECT l FROM Loja l WHERE l.email = :email")
	public Loja getLojaByEmail(@Param("email") String email);
}

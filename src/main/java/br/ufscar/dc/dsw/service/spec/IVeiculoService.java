package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.Veiculo;
import org.springframework.transaction.annotation.Transactional;

public interface IVeiculoService {

	Veiculo buscarPorId(Long id);
	
	List<Veiculo> buscarTodos();
	
	void salvar(Veiculo veiculo);
	
	void excluir(Long id);
	@Transactional(readOnly = true)
	public List<Veiculo> buscarTodosPorLoja(Long lojaId);
	
}

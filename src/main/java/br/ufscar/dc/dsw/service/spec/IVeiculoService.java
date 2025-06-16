package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.Veiculo;

public interface IVeiculoService {

	Veiculo buscarPorId(Long id);
	
	List<Veiculo> buscarTodos();
	
	void salvar(Veiculo veiculo);
	
	void excluir(Long id);
	
}

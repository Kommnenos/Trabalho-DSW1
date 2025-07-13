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

	@Transactional(readOnly = true)
	public List<Veiculo> buscarTodosPorModelo(String modelo);

	@Transactional(readOnly = true)
	List<Veiculo> buscarTodosPorLojaEModelo(Long lojaId, String modelo);

	@Transactional(readOnly = true)
	List<Veiculo> buscarTodosSemPropostaAceita();

	@Transactional(readOnly = true)
	List<Veiculo> buscarTodosPorModeloSemPropostaAceita(String modelo);

	@Transactional(readOnly = true)
	List<Veiculo> buscarTodosPorLojaEModeloSemPropostaAceita(Long lojaId, String modelo);



}

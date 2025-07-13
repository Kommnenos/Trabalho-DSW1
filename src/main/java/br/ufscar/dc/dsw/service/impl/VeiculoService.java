package br.ufscar.dc.dsw.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import br.ufscar.dc.dsw.dao.IPropostaDAO;
import br.ufscar.dc.dsw.domain.Proposta;
import br.ufscar.dc.dsw.domain.StatusProposta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.dsw.dao.IVeiculoDAO;
import br.ufscar.dc.dsw.domain.Veiculo;
import br.ufscar.dc.dsw.service.spec.IVeiculoService;

@Service
@Transactional(readOnly = false)
public class VeiculoService implements IVeiculoService {

	@Autowired
	IVeiculoDAO dao;

	@Autowired
	IPropostaDAO propostaDAO;
	
	public void salvar(Veiculo veiculo) {
		dao.save(veiculo);
	}

	@Transactional(readOnly = true)
	public Veiculo buscarPorId(Long id) {
		return dao.findById(id.longValue());
	}

	@Transactional(readOnly = true)
	public List<Veiculo> buscarTodosPorLoja(Long lojaId) {
		return dao.getVeiculoByLoja(lojaId);
	}


	@Transactional(readOnly = true)
	public List<Veiculo> buscarTodos() {
		return dao.findAll();
	}

	@Override
	public void excluir(Long id) {
		Veiculo veiculo = dao.findById(id.longValue());
		if(veiculo != null) {
			propostaDAO.deleteByVeiculo(veiculo);
			System.out.println(veiculo);
		}

		dao.deleteById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Veiculo> buscarTodosPorModelo(String modelo) {
		return dao.findAllByModeloContainingIgnoreCase(modelo);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Veiculo> buscarTodosPorLojaEModelo(Long lojaId, String modelo) {
		return dao.findAllByLojaIdAndModeloContainingIgnoreCase(lojaId, modelo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Veiculo> buscarTodosSemPropostaAceita(){
		return dao.findAllVeiculosSemPropostaAceita();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Veiculo> buscarTodosPorModeloSemPropostaAceita(String modelo){
		List<Veiculo> veiculos = buscarTodosPorModelo(modelo);
		Iterator<Veiculo> iterator = veiculos.iterator();
		while (iterator.hasNext()) {
			Veiculo v = iterator.next();
			List<Proposta> propostasVeiculo = v.getPropostas();
			for (Proposta p : propostasVeiculo) {
				if (p.getStatus() == StatusProposta.ACEITO) {
					iterator.remove();
					break;
				}
			}
		}
		return veiculos;


		//return dao.findAllVeiculosSemPropostaAceita().stream().filter(veiculo -> veiculo.getModelo().equals(modelo)).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<Veiculo> buscarTodosPorLojaEModeloSemPropostaAceita(Long lojaId, String modelo){
		List<Veiculo> veiculos = buscarTodosPorLojaEModelo(lojaId, modelo);
		Iterator<Veiculo> iterator = veiculos.iterator();
		while (iterator.hasNext()) {
			Veiculo v = iterator.next();
			List<Proposta> propostasVeiculo = v.getPropostas();
			for (Proposta p : propostasVeiculo) {
				if (p.getStatus() == StatusProposta.ACEITO) {
					iterator.remove();
					break;
				}
			}
		}
		return veiculos;

		//return dao.findAllVeiculosSemPropostaAceita().stream().filter(veiculo -> veiculo.getModelo().equalsIgnoreCase(modelo)).filter(veiculo -> Objects.equals(veiculo.getLoja().getId(), lojaId)).collect(Collectors.toList());
	}


}

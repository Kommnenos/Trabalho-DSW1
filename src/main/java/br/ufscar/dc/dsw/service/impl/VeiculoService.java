package br.ufscar.dc.dsw.service.impl;

import java.util.List;

import br.ufscar.dc.dsw.dao.IPropostaDAO;
import br.ufscar.dc.dsw.domain.Proposta;
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



}

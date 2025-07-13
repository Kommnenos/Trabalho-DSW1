package br.ufscar.dc.dsw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.dsw.dao.IUsuarioDAO;
import br.ufscar.dc.dsw.domain.Loja;
import br.ufscar.dc.dsw.service.spec.ILojaService;

@Service
@Transactional(readOnly = false)
public class LojaService implements ILojaService {

	@Autowired
	IUsuarioDAO dao;
	
	public void salvar(Loja loja) {
		dao.save(loja);
	}

	public void excluir(Long id) {
		dao.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Loja buscarPorId(Long id) {
		return (Loja) dao.findById(id.longValue());
	}

	@Transactional(readOnly = true)
	public List<Loja> buscarTodos() {
		return dao.findAllLojas();
	}
	
	@Transactional(readOnly = true)
	public boolean lojaTemVeiculos(Long id) {
		Loja loja = buscarPorId(id);
		return !loja.getVeiculos().isEmpty();
	}

}

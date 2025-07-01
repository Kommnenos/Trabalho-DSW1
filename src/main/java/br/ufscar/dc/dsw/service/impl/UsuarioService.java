package br.ufscar.dc.dsw.service.impl;

import br.ufscar.dc.dsw.dao.IUsuarioDAO;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Loja;
import br.ufscar.dc.dsw.domain.Usuario;
import br.ufscar.dc.dsw.service.spec.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = false)
public class UsuarioService implements IUsuarioService {
    @Autowired
    IUsuarioDAO dao;

    public void salvar(Usuario usuario) {
        dao.save(usuario);
    }

    public void excluir(Long id) {
        dao.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        return dao.findById(id.longValue());
    }

    @Transactional(readOnly = true)
    public List<Usuario> buscarTodos() {
        return dao.findAll();
    }

    @Transactional(readOnly = true)
    public List<Cliente> buscarTodosClientes() {
        return dao.findAllClientes();
    }

    @Transactional(readOnly = true)
    public List<Loja> buscarTodosLojas() {
        return dao.findAllLojas();
    }

    @Transactional(readOnly = true)
    public boolean lojaTemVeiculos(Long id) {
        Usuario usuario = buscarPorId(id);
        if(usuario instanceof Loja){
            Loja loja = (Loja) usuario;
            return loja.getVeiculos() != null && !loja.getVeiculos().isEmpty();
        }

        return false;

    }
}

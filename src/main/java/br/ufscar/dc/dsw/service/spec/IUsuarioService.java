package br.ufscar.dc.dsw.service.spec;

import br.ufscar.dc.dsw.domain.Usuario;

import java.util.List;

public interface IUsuarioService {
    Usuario buscarPorId(Long id);
    List<Usuario> buscarTodos();
    void salvar(Usuario usuario);
    void excluir(Long id);
    boolean lojaTemVeiculos(Long id);
}

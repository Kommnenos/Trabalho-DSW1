package br.ufscar.dc.dsw.security;

import br.ufscar.dc.dsw.domain.Loja;
import br.ufscar.dc.dsw.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.ufscar.dc.dsw.dao.IUsuarioDAO;
import br.ufscar.dc.dsw.domain.Cliente;

public class UsuarioDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private IUsuarioDAO dao;



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = dao.getUsuarioByEmail(email);
        if(usuario != null) {
            return new UsuarioDetails(usuario);
        }

        throw new UsernameNotFoundException("Usuário não encontrado " + email);
    }


}

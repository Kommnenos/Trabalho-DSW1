package br.ufscar.dc.dsw.security;

import br.ufscar.dc.dsw.dao.ILojaDAO;
import br.ufscar.dc.dsw.domain.Loja;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.ufscar.dc.dsw.dao.IClienteDAO;
import br.ufscar.dc.dsw.domain.Cliente;

public class UsuarioDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private IClienteDAO clienteDao;

    @Autowired
    private ILojaDAO lojaDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Cliente cliente = clienteDao.getClienteByEmail(email);
        if(cliente != null) {
            return new UsuarioDetails(cliente);
        }

        Loja loja = lojaDao.getLojaByEmail(email);
        if(loja != null) {
            return new UsuarioDetails(loja);
        }

        throw new UsernameNotFoundException("Usuário não encontrado " + email);
    }


}

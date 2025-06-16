package br.ufscar.dc.dsw.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.ufscar.dc.dsw.dao.IClienteDAO;
import br.ufscar.dc.dsw.domain.Cliente;

public class ClienteDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private IClienteDAO dao;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Cliente cliente = dao.getClienteByEmail(email);
		
		if(cliente == null) {
			throw new UsernameNotFoundException("Usuário não encontrado");
		}
		
		return new ClienteDetails(cliente);
	}
    
    
}

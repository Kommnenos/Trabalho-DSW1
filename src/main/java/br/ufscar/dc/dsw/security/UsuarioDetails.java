package br.ufscar.dc.dsw.security;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Loja;

public class UsuarioDetails implements UserDetails {

    private Cliente cliente;
    private Loja loja;

    public UsuarioDetails(Cliente cliente) {
        this.cliente = cliente;
    }

    public UsuarioDetails(Loja loja) {
        this.loja = loja;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (cliente != null) {
            return Arrays.asList(new SimpleGrantedAuthority(cliente.getRole()));
        } else {
            return Arrays.asList(new SimpleGrantedAuthority("   LOJA"));
        }
    }

    @Override
    public String getPassword() {
        return (cliente != null) ? cliente.getSenha() : loja.getSenha();
    }

    @Override
    public String getUsername() {
        return (cliente != null) ? cliente.getEmail() : loja.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}

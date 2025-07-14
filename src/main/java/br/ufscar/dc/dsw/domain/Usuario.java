package br.ufscar.dc.dsw.domain;

import br.ufscar.dc.dsw.validation.UniqueEmail;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Usuario")
public class Usuario extends AbstractEntity<Long> {

    @UniqueEmail
    @NotBlank(message = "{NotBlank.email}")
    @Column(nullable = false, unique = true, length= 60)
    private String email;

    @NotBlank (message = "{NotBlank.senha}")
    @Column(nullable = false, length= 64)
    private String senha;

    @NotBlank (message = "{NotBlank.cliente.nome}")
    @Column(nullable = false, length= 60)
    private String nome;

    @NotBlank
    @Column(nullable = false, length = 10)
    private String role;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

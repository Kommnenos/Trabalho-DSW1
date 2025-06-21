package br.ufscar.dc.dsw.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.ufscar.dc.dsw.validation.UniqueCPF;
import br.ufscar.dc.dsw.validation.UniqueEmail;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

@SuppressWarnings("serial")
@Entity
@Table(name = "Cliente")
public class Cliente extends AbstractEntity<Long> {

	@UniqueEmail
	@NotBlank
	@Column(nullable = false, unique = true, length= 60)
	private String email;

	@NotBlank
	@Column(nullable = false, length= 64)
	private String senha;

	@NotBlank
	@Column(nullable = false, length= 60)
	private String nome;

	@UniqueCPF
	@NotBlank
	@Size(min=14, max=14)
	@Column(nullable = false, unique = true, length=14)
	private String CPF;

	@Column(length=15)
	private String telefone;

	@Column(length= 1)
	private String sexo;

	@Column
	private LocalDate dataNasc;

	@NotBlank
	@Column(nullable = false, length = 10)
	private String role;
	
	@Column
    private boolean enabled;

	public Cliente() {
		this.role = "ROLE_USER";
	}

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

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String CPF) {
		this.CPF = CPF;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public LocalDate getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(LocalDate dataNasc) {
		this.dataNasc = dataNasc;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	
}
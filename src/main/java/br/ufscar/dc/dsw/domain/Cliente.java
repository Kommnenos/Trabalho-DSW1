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
public class Cliente extends Usuario {

	@UniqueCPF
	@NotBlank (message = "{NotBlank.cliente.CPF}")
	@Size(min=14, max=14, message = "{Size.cliente.CPF}")
	@Column(nullable = false, unique = true, length=14)
	private String CPF;

	@Column(length=15)
	private String telefone;

	@Column(length= 1)
	private String sexo;

	@Column
	private LocalDate dataNasc;

	@Column
    private boolean enabled;

	public Cliente() {
		this.setRole("ROLE_USER");
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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	
}
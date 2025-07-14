package br.ufscar.dc.dsw.domain;

import java.util.List;

import br.ufscar.dc.dsw.validation.UniqueEmail;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import br.ufscar.dc.dsw.validation.UniqueCNPJ;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@SuppressWarnings("serial")
@Entity
@Table(name = "Loja")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Loja extends Usuario {

	@UniqueCNPJ (message = "{Unique.loja.CNPJ}")
	@NotBlank (message = "{NotBlank.loja.CNPJ}")
	@Size(min = 18, max = 18, message = "{Size.loja.CNPJ}")
	@Column(nullable = false, unique = true, length = 60)
	private String CNPJ;

	@NotBlank (message = "{NotBlank.loja.descricao}")
	@Size(max = 60, message = "{Size.loja.descricao}")
	@Column(nullable = false, length = 60)
	private String descricao;

	@Column
	private boolean enabled;

	@OneToMany(mappedBy = "loja")
	@JsonManagedReference
	private List<Veiculo> veiculos;


	public Loja() {
		this.setRole("ROLE_LOJA");
	}


	public String getCNPJ() {
		return CNPJ;
	}

	public void setCNPJ(String CNPJ) {
		this.CNPJ = CNPJ;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Veiculo> getVeiculos() {
		return veiculos;
	}

	public void setVeiculos(List<Veiculo> veiculos) {
		this.veiculos = veiculos;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}

package br.ufscar.dc.dsw.domain;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import br.ufscar.dc.dsw.validation.UniqueCNPJ;

@SuppressWarnings("serial")
@Entity
@Table(name = "Loja")
public class Loja extends AbstractEntity<Long> {

	@UniqueCNPJ (message = "{Unique.loja.CNPJ}")
	@NotBlank
	@Size(min = 18, max = 18, message = "{Size.loja.CNPJ}")
	@Column(nullable = false, unique = true, length = 60)
	private String CNPJ;

	@NotBlank
	@Size(max = 60, message = "{Size.loja.email}")
	@Column(nullable = false, unique = true, length = 60)
	private String email;

	@NotBlank
	@Size(max = 60, message = "{Size.loja.senha}")
	@Column(nullable = false, unique = true, length = 60)
	private String senha;

	@NotBlank
	@Size(max = 60, message = "{Size.loja.nome}")
	@Column(nullable = false, unique = true, length = 60)
	private String nome;

	@NotBlank
	@Size(max = 60, message = "{Size.loja.descricao}")
	@Column(nullable = false, unique = true, length = 60)
	private String descricao;

	@OneToMany(mappedBy = "loja")
	private List<Veiculo> veiculos;
	
	public String getCNPJ() {
		return CNPJ;
	}

	public void setCNPJ(String CNPJ) {
		this.CNPJ = CNPJ;
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
}

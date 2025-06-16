package br.ufscar.dc.dsw.domain;

import java.math.BigDecimal;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@SuppressWarnings("serial")
@Entity
@Table(name = "Veiculo")
public class Veiculo extends AbstractEntity<Long> {


	@NotBlank(message = "{NotBlank.veiculo.cnpj_loja}")
	@Size(max = 60)
	@Column(nullable = false, length = 60)
	private String cnpj_loja;

	@NotBlank(message = "{NotBlank.veiculo.placa}")
	@Size(max = 60)
	@Column(nullable = false, length = 60)
	private String placa;

	@NotBlank(message = "{NotBlank.veiculo.modelo}")
	@Size(max = 60)
	@Column(nullable = false, length = 60)
	private String modelo;

	@Id
	@NotBlank(message = "{NotBlank.veiculo.chassi}")
	@Size(max = 60)
	@Column(nullable = false, length = 60)
	private String chassi;
    
	@NotNull(message = "{NotNull.veiculo.ano}")
	@Column(nullable = false, length = 5)
	private Integer ano;
    
	@NotNull(message = "{NotNull.veiculo.quilometragem}")
	@Column(nullable = false, length = 5)
	private Integer quilometragem;

	@NotBlank(message = "{NotBlank.veiculo.descricao}")
	@Size(max = 240)
	@Column(nullable = false, length = 240)
	private String descricao;
	
	@NotNull(message = "{NotNull.veiculo.valor}")
	@Column(nullable = false, columnDefinition = "DECIMAL(8,2) DEFAULT 0.0")
	private BigDecimal valor;

	@NotNull(message = "{NotNull.veiculo.loja}")
	@ManyToOne
	@JoinColumn(name = "loja")
	private Loja loja;

	public String getCNPJloja() {
		return cnpj_loja;
	}

	public void setCNPJloja(String cnpj_loja) {
		this.cnpj_loja = cnpj_loja;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getChassi() {
		return chassi;
	}

	public void setChassi(String chassi) {
		this.chassi = chassi;
	}

	public @NotBlank(message = "{NotBlank.veiculo.cnpj_loja}") @Size(max = 60) String getAno() {
		return cnpj_loja;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Integer getQuilometragem() {
		return quilometragem;
	}

	public void setQuilometragem(Integer quilometragem) {
		this.quilometragem = quilometragem;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Loja getLoja() {
		return loja;
	}

	public void setLoja(Loja loja) {
		this.loja = loja;
	}

}

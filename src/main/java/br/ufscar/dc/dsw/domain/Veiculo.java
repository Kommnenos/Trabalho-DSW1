package br.ufscar.dc.dsw.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@SuppressWarnings("serial")
@Entity
@Table(name = "Veiculo")
public class Veiculo extends AbstractEntity<Long> {

	@NotBlank(message = "{NotBlank.veiculo.placa}")
	@Size(max = 60)
	@Column(nullable = false, length = 60)
	private String placa;

	@NotBlank(message = "{NotBlank.veiculo.modelo}")
	@Size(max = 60)
	@Column(nullable = false, length = 60)
	private String modelo;

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
	@Column(nullable = false, precision = 8, scale = 2)
	private BigDecimal valor;

	@NotNull(message = "{NotNull.veiculo.loja}")
	@ManyToOne
	@JoinColumn(name = "loja_id")
	private Loja loja;

	@Lob
	@Basic
	@Column(length = 10485760) // 1MB
	private byte[] imagem;

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

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Integer getAno() {
		return ano;
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

	public String getCNPJLoja(){
		return loja.getCNPJ();
	}

	public byte[] getImagem() {
		return imagem;
	}

	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}
}

package br.ufscar.dc.dsw.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

@SuppressWarnings("serial")
@Entity
@Table(name = "Proposta")
public class Proposta extends AbstractEntity<Long>{

    @NotNull
    @Column(nullable = false)
    private String data;

    @NotNull
    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal valor;

    @NotNull(message = "{NotNull.proposta.veiculo}")
    @ManyToOne
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente ;

    @NotBlank
    @Column(nullable = false, length = 255)
    private String condicoesPagamento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusProposta status;  // Enum: ABERTO, ACEITO, NAO_ACEITO

    @Column
    private BigDecimal valorContraproposta;

    @Column(length = 255)
    private String condicoesContraproposta;

    @Column
    private LocalDateTime dataReuniao;

    @Column(length = 255)
    private String linkVideoconferencia;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getCondicoesPagamento() {
        return condicoesPagamento;
    }

    public void setCondicoesPagamento(String condicoesPagamento) {
        this.condicoesPagamento = condicoesPagamento;
    }

    public StatusProposta getStatus() {
        return status;
    }

    public void setStatus(StatusProposta status) {
        this.status = status;
    }

    public BigDecimal getValorContraproposta() {
        return valorContraproposta;
    }

    public void setValorContraproposta(BigDecimal valorContraproposta) {
        this.valorContraproposta = valorContraproposta;
    }

    public String getCondicoesContraproposta() {
        return condicoesContraproposta;
    }

    public void setCondicoesContraproposta(String condicoesContraproposta) {
        this.condicoesContraproposta = condicoesContraproposta;
    }

    public LocalDateTime getDataReuniao() {
        return dataReuniao;
    }

    public void setDataReuniao(LocalDateTime dataReuniao) {
        this.dataReuniao = dataReuniao;
    }

    public String getLinkVideoconferencia() {
        return linkVideoconferencia;
    }

    public void setLinkVideoconferencia(String linkVideoconferencia) {
        this.linkVideoconferencia = linkVideoconferencia;
    }

}

package br.ufscar.dc.dsw.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "ImagemVeiculo")
public class ImagemVeiculo extends AbstractEntity<Long> {
    @Lob
    @Column(nullable = false)
    private byte[] dados;

    @ManyToOne
    @JoinColumn(name = "veiculo_id", nullable = false)
    @JsonBackReference("veiculo-imagens")
    private Veiculo veiculo;


    public byte[] getDados() {
        return dados;
    }

    public void setDados(byte[] dados) {
        this.dados = dados;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

}

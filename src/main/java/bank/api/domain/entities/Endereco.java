package bank.api.domain.entities;

import bank.api.presentation.dtos.cliente.DadosAtualizarEndereco;
import bank.api.presentation.dtos.cliente.DadosEndereco;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
    @Column(nullable = false, length = 50)
    private String logradouro;
    @Column(nullable = false, length = 50)
    private String bairro;
    @Column(nullable = false, length = 8)
    private String cep;
    @Column(nullable = false, length = 10)
    private Integer numero;
    private String complemento;
    @Column(nullable = false, length = 50)
    private String cidade;
    @Column(nullable = false, length = 2)
    private String uf;

    public Endereco(DadosEndereco dados) {
        this.logradouro = dados.logradouro();
        this.bairro = dados.bairro();
        this.cep = dados.cep();
        this.uf = dados.uf();
        this.cidade = dados.cidade();
        this.numero = dados.numero();
        this.complemento = dados.complemento();
    }

    public void atualizarInformacoes(DadosAtualizarEndereco dados) {
        if(dados.logradouro() != null) {
            this.logradouro = dados.logradouro();
        }
        if(dados.bairro() != null) {
            this.bairro = dados.bairro();
        }
        if(dados.cep() != null) {
            this.cep = dados.cep();
        }
        if(dados.numero() != null) {
            this.numero = dados.numero();
        }
        if(dados.complemento() != null) {
            this.complemento = dados.complemento();
        }
        if(dados.cidade() != null) {
            this.cidade = dados.cidade();
        }
        if(dados.uf() != null) {
            this.uf = dados.uf();
        }
    }
}
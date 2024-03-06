package bank.api.domain.entities;

import bank.api.presentation.dtos.cliente.DadosAtualizarCliente;
import bank.api.presentation.dtos.cliente.DadosCadastroCliente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Cliente")
@Table(name = "clientes")
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
    public Cliente(DadosCadastroCliente dados) {
        this.nome = dados.nome();
        this.cpf = dados.cpf();
        this.genero = dados.genero();
        this.telefone = dados.telefone();
        this.email = dados.email();
        this.endereco = new Endereco(dados.endereco());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cpf", nullable = false, unique = true, length = 14)
    private String cpf;

    @Column(name = "genero", nullable = false, length = 20)
    private String genero;

    @Column(name = "telefone", length = 20, nullable = false)
    private String telefone;

    @Column(name = "email", length = 100)
    private String email;

    @Embedded
    private Endereco endereco;

    public void atualizarInformacoes(DadosAtualizarCliente dados) {
        if(dados.email() != null){
            this.email = dados.email();
        }
        if(dados.telefone() != null){
            this.telefone = dados.telefone();
        }
        if(dados.endereco() != null) {
            this.endereco.atualizarInformacoes(dados.endereco());
        }
    }
}

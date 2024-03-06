package bank.api.presentation.dtos.cliente;

import bank.api.domain.entities.Cliente;
import bank.api.domain.entities.Endereco;

public record DadosDetalhamentoCliente(
        Long id,
        String nome,
        String cpf,
        String genero,
        String telefone,
        String email,
        Endereco endereco){
    public DadosDetalhamentoCliente(Cliente cliente){
        this(cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getGenero(),
                cliente.getTelefone(),
                cliente.getEmail(),
                cliente.getEndereco());
    }
}

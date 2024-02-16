package bank.api.application.cliente.dtos;

import bank.api.domain.cliente.entities.Cliente;
import bank.api.domain.cliente.entities.Endereco;

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
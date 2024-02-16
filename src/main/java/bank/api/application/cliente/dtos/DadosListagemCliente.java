package bank.api.application.cliente.dtos;

import bank.api.domain.cliente.entities.Cliente;

public record DadosListagemCliente(
        Long id,
        String nome,
        String telefone,
        String email) {
    public DadosListagemCliente(Cliente cliente){
        this(cliente.getId(), cliente.getNome(), cliente.getTelefone(), cliente.getEmail());
    }
}

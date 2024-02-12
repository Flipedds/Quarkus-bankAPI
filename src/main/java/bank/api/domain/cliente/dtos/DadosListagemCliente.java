package bank.api.domain.cliente.dtos;

import bank.api.domain.cliente.models.Cliente;

public record DadosListagemCliente(
        Long id,
        String nome,
        String telefone,
        String email) {
    public DadosListagemCliente(Cliente cliente){
        this(cliente.getId(), cliente.getNome(), cliente.getTelefone(), cliente.getEmail());
    }
}

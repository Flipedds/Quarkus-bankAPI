package bank.api.application.cliente.dtos;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizarCliente(
        @NotNull Long id,
        String telefone,
        String email,
        DadosAtualizarEndereco endereco) {
}

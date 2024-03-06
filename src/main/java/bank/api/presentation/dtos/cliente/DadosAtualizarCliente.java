package bank.api.presentation.dtos.cliente;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizarCliente(
        @NotNull Long id,
        String telefone,
        String email,
        DadosAtualizarEndereco endereco) {
}

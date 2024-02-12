package bank.api.domain.cliente.dtos;

import bank.api.domain.endereco.dtos.DadosAtualizarEndereco;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizarCliente(
        @NotNull Long id,
        String telefone,
        String email,
        DadosAtualizarEndereco endereco) {
}

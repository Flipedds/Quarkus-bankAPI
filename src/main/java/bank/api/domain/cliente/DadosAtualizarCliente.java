package bank.api.domain.cliente;

import bank.api.domain.endereco.DadosAtualizarEndereco;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizarCliente(
        @NotNull Long id,
        String telefone,
        String email,
        DadosAtualizarEndereco endereco) {
}

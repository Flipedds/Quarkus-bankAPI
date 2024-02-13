package bank.api.domain.conta.dtos;

import bank.api.domain.conta.enums.TipoConta;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroConta(
        @NotNull TipoConta tipoConta,
        @NotNull Long clienteId) {
}

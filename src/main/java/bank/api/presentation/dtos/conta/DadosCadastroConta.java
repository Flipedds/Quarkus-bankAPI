package bank.api.presentation.dtos.conta;

import bank.api.domain.enums.TipoConta;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroConta(
        @NotNull TipoConta tipoConta,
        @NotNull Long clienteId) {
}

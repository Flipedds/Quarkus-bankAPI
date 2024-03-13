package bank.api.presentation.dtos.autenticacao;

import jakarta.validation.constraints.NotBlank;

public record Credenciais(
        @NotBlank String username,
        @NotBlank String password) {
}

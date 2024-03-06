package bank.api.presentation.dtos.cliente;

import jakarta.validation.constraints.Pattern;

public record DadosAtualizarEndereco(
        String logradouro,
        String bairro,
        @Pattern(regexp = "\\d{8}") String cep,
        String cidade,
        String uf,
        String complemento,
        Integer numero) {
}

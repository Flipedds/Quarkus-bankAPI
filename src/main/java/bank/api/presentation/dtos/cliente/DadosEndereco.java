package bank.api.presentation.dtos.cliente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosEndereco(
        @NotBlank String logradouro,
        @NotBlank String bairro, 
        @NotBlank @Pattern(regexp = "\\d{8}") String cep,
        @NotBlank String cidade, 
        @NotBlank String uf, 
        String complemento,
        @NotNull Integer numero) {}

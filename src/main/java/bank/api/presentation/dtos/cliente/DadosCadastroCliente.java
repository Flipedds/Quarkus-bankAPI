package bank.api.presentation.dtos.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroCliente(
        @NotBlank String nome,
        @NotBlank @Pattern(regexp = "\\d{11}") String cpf,
        @NotBlank String genero,
        @NotBlank String telefone,
        @NotBlank @Email String email,
        DadosEndereco endereco) {}

package bank.api.domain.validators;

import bank.api.presentation.dtos.conta.DadosCadastroConta;

public interface Validador {
    void validar(DadosCadastroConta dados);
}

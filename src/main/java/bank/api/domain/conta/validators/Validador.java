package bank.api.domain.conta.validators;

import bank.api.application.conta.dtos.DadosCadastroConta;

public interface Validador {
    void validar(DadosCadastroConta dados);
}

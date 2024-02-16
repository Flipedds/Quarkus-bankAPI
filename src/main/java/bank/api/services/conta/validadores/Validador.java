package bank.api.services.conta.validadores;

import bank.api.application.conta.dtos.DadosCadastroConta;

public interface Validador {
    void validar(DadosCadastroConta dados);
}

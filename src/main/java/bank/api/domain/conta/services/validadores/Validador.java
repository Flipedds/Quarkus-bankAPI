package bank.api.domain.conta.services.validadores;

import bank.api.domain.conta.dtos.DadosCadastroConta;

public interface Validador {
    void validar(DadosCadastroConta dados);
}

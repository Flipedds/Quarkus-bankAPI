package bank.api.domain.validators;

import bank.api.presentation.dtos.transacao.DadosNovaTransacao;

public interface IValidadorDeposito {
    void validar(DadosNovaTransacao dados);

    Integer getPriority();
}

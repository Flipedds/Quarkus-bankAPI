package bank.api.domain.validators;

import bank.api.presentation.dtos.transacao.DadosNovaTransacao;

public interface IValidadorTransferencia {
    void validar(DadosNovaTransacao dados);

    Integer getPriority();
}

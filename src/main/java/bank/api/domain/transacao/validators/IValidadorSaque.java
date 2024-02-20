package bank.api.domain.transacao.validators;

import bank.api.application.transacao.dtos.DadosNovaTransacao;

public interface IValidadorSaque {
    void validar(DadosNovaTransacao dados);
}

package bank.api.domain.validators;

import bank.api.presentation.dtos.transacao.DadosNovaTransacao;

public interface IValidadorSaque {
    void validar(DadosNovaTransacao dados);
}

package bank.api.services.transacao.validadores.saque;

import bank.api.application.transacao.dtos.DadosNovaTransacao;

public interface IValidadorSaque {
    void validar(DadosNovaTransacao dados);
}

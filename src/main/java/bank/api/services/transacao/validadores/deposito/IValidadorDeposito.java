package bank.api.services.transacao.validadores.deposito;

import bank.api.application.transacao.dtos.DadosNovaTransacao;

public interface IValidadorDeposito {
    void validar(DadosNovaTransacao dados);
}

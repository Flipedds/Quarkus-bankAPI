package bank.api.services.transacao.validadores.transferencia;

import bank.api.application.transacao.dtos.DadosNovaTransacao;

public interface IValidadorTransferencia {
    void validar(DadosNovaTransacao dados);
}

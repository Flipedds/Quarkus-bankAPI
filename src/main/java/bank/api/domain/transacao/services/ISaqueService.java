package bank.api.domain.transacao.services;

import bank.api.application.transacao.dtos.DadosNovaTransacao;
import bank.api.domain.transacao.entities.Transacao;

public interface ISaqueService {
    Transacao sacar(DadosNovaTransacao dados);
}

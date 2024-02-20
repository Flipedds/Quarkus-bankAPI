package bank.api.domain.transacao.services;

import bank.api.application.transacao.dtos.DadosNovaTransacao;
import bank.api.domain.transacao.entities.Transacao;
import jakarta.transaction.Transactional;

public interface ITransacaoService {
    @Transactional
    Transacao executarESalvarTransacao(DadosNovaTransacao dados);
}

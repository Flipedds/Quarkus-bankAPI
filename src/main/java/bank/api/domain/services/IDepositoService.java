package bank.api.domain.services;

import bank.api.presentation.dtos.transacao.DadosNovaTransacao;
import bank.api.domain.entities.Transacao;

public interface IDepositoService {
    Transacao depositar(DadosNovaTransacao dados);
}

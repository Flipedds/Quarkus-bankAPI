package bank.api.domain.services;

import bank.api.presentation.dtos.transacao.DadosNovaTransacao;
import bank.api.domain.entities.Transacao;

public interface ITransferenciaService {
    Transacao transferir(DadosNovaTransacao dados);
}

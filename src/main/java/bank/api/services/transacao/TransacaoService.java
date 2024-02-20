package bank.api.services.transacao;

import bank.api.application.transacao.dtos.DadosNovaTransacao;
import bank.api.domain.transacao.entities.Transacao;
import bank.api.domain.transacao.services.IDepositoService;
import bank.api.domain.transacao.services.ISaqueService;
import bank.api.domain.transacao.services.ITransacaoService;
import bank.api.domain.transacao.services.ITransferenciaService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class TransacaoService implements ITransacaoService {
    @Inject
    IDepositoService depositoService;

    @Inject
    ISaqueService saqueService;

    @Inject
    ITransferenciaService transferenciaService;

    @Transactional
    public Transacao executarESalvarTransacao(DadosNovaTransacao dados) {
        switch (dados.tipoTransacao()){
            case DEPOSITO -> {
                return depositoService.depositar(dados);
            }
            case SAQUE -> {
                return saqueService.sacar(dados);
            }
            case TRANSFERENCIA -> {
                return transferenciaService.transferir(dados);
            }
        }
        return null;
    }
}

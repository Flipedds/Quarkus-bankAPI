package bank.api.application.services;

import bank.api.presentation.dtos.transacao.DadosNovaTransacao;
import bank.api.domain.repositories.IContaRepository;
import bank.api.domain.entities.Transacao;
import bank.api.domain.repositories.ITransacaoRepository;
import bank.api.domain.services.ITransferenciaService;
import bank.api.domain.validators.IValidadorTransferencia;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ApplicationScoped
public class TransferenciaService implements ITransferenciaService {

    @Inject
    Instance<IValidadorTransferencia> validadoresTransferencia;

    @Inject
    IContaRepository contaRepository;

    @Inject
    ITransacaoRepository transacaoRepository;

    @Override
    public Transacao transferir(DadosNovaTransacao dados) {
        validadoresTransferencia.forEach(v -> v.validar(dados));
        var conta = contaRepository.findById(dados.idConta());
        int saldoDaConta = Integer.parseInt(String.valueOf(conta.getSaldo()));
        int valorDaTransferencia = Integer.parseInt(String.valueOf(dados.valor()));
        var contaDestino = contaRepository.findById(dados.idContaDestino());
        int saldoDaContaDestino = Integer.parseInt(String.valueOf(contaDestino.getSaldo()));

        conta.setSaldo(BigDecimal.valueOf(saldoDaConta - valorDaTransferencia));
        contaDestino.setSaldo(BigDecimal.valueOf(saldoDaContaDestino + valorDaTransferencia));
        Transacao transacao = new Transacao(null, dados.tipoTransacao(), dados.valor(),
                LocalDateTime.now(), conta, contaDestino);
        transacaoRepository.persist(transacao);
        return transacao;
    }
}

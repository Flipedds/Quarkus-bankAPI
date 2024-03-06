package bank.api.application.services;

import bank.api.presentation.dtos.transacao.DadosNovaTransacao;
import bank.api.domain.repositories.IContaRepository;
import bank.api.domain.entities.Transacao;
import bank.api.domain.repositories.ITransacaoRepository;
import bank.api.domain.services.IDepositoService;
import bank.api.domain.validators.IValidadorDeposito;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ApplicationScoped
public class DepositoService implements IDepositoService {

    @Inject
    Instance<IValidadorDeposito> validadoresDeposito;
    @Inject
    IContaRepository contaRepository;

    @Inject
    ITransacaoRepository transacaoRepository;

    @Override
    public Transacao depositar(DadosNovaTransacao dados) {
        validadoresDeposito.forEach(v -> v.validar(dados));
        var conta = contaRepository.findById(dados.idConta());
        int saldoDaConta = Integer.parseInt(String.valueOf(conta.getSaldo()));
        int valorDoDeposito = Integer.parseInt(String.valueOf(dados.valor()));
        conta.setSaldo(BigDecimal.valueOf(saldoDaConta + valorDoDeposito));
        Transacao transacao = new Transacao(null, dados.tipoTransacao(), dados.valor(),
                LocalDateTime.now(), conta, null);
        transacaoRepository.persist(transacao);
        return transacao;
    }
}

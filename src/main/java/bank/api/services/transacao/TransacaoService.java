package bank.api.services.transacao;

import bank.api.application.transacao.dtos.DadosNovaTransacao;
import bank.api.domain.conta.repositories.IContaRepository;
import bank.api.domain.transacao.entities.Transacao;
import bank.api.domain.transacao.repositories.ITransacaoRepository;
import bank.api.domain.transacao.services.ITransacaoService;
import bank.api.services.transacao.validadores.deposito.IValidadorDeposito;
import bank.api.services.transacao.validadores.saque.IValidadorSaque;
import bank.api.services.transacao.validadores.transferencia.IValidadorTransferencia;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ApplicationScoped
public class TransacaoService implements ITransacaoService {
    @Inject
    IContaRepository contaRepository;

    @Inject
    ITransacaoRepository transacaoRepository;

    @Inject
    Instance<IValidadorSaque> validadoresSaque;

    @Inject
    Instance<IValidadorDeposito> validadoresDeposito;

    @Inject
    Instance<IValidadorTransferencia> validadoresTransferencia;

    @Transactional
    public Transacao executarESalvarTransacao(DadosNovaTransacao dados) {
        switch (dados.tipoTransacao()){
            case DEPOSITO -> {
                var conta = contaRepository.findById(dados.idConta());
                int saldoDaConta = Integer.parseInt(String.valueOf(conta.getSaldo()));
                int valorDoDeposito = Integer.parseInt(String.valueOf(dados.valor()));
                validadoresDeposito.forEach(v -> v.validar(dados));
                conta.setSaldo(BigDecimal.valueOf(saldoDaConta + valorDoDeposito));
                Transacao transacao = new Transacao(null, dados.tipoTransacao(), dados.valor(),
                        LocalDateTime.now(), conta, null);
                transacaoRepository.persist(transacao);
                return transacao;
            }
            case SAQUE -> {
                var conta = contaRepository.findById(dados.idConta());
                int saldoDaConta = Integer.parseInt(String.valueOf(conta.getSaldo()));
                int valorDoSaque = Integer.parseInt(String.valueOf(dados.valor()));
                validadoresSaque.forEach(v -> v.validar(dados));
                conta.setSaldo(BigDecimal.valueOf(saldoDaConta - valorDoSaque));
                Transacao transacao = new Transacao(null, dados.tipoTransacao(), dados.valor(),
                        LocalDateTime.now(), conta, null);
                transacaoRepository.persist(transacao);
                return transacao;
            }
            case TRANSFERENCIA -> {
                var conta = contaRepository.findById(dados.idConta());
                int saldoDaConta = Integer.parseInt(String.valueOf(conta.getSaldo()));
                int valorDaTransferencia = Integer.parseInt(String.valueOf(dados.valor()));
                var contaDestino = contaRepository.findById(dados.idContaDestino());
                int saldoDaContaDestino = Integer.parseInt(String.valueOf(contaDestino.getSaldo()));
                validadoresTransferencia.forEach(v -> v.validar(dados));
                conta.setSaldo(BigDecimal.valueOf(saldoDaConta - valorDaTransferencia));
                contaDestino.setSaldo(BigDecimal.valueOf(saldoDaContaDestino + valorDaTransferencia));
                Transacao transacao = new Transacao(null, dados.tipoTransacao(), dados.valor(),
                        LocalDateTime.now(), conta, contaDestino);
                transacaoRepository.persist(transacao);
                return transacao;
            }
        }
        return null;
    }
}

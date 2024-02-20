package bank.api.services.transacao;

import bank.api.application.transacao.dtos.DadosNovaTransacao;
import bank.api.domain.conta.repositories.IContaRepository;
import bank.api.domain.transacao.entities.Transacao;
import bank.api.domain.transacao.repositories.ITransacaoRepository;
import bank.api.domain.transacao.services.ISaqueService;
import bank.api.domain.transacao.validators.IValidadorSaque;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@ApplicationScoped
public class SaqueService implements ISaqueService {

    @Inject
    IContaRepository contaRepository;

    @Inject
    ITransacaoRepository transacaoRepository;

    @Inject
    Instance<IValidadorSaque> validadoresSaque;

    @Override
    public Transacao sacar(DadosNovaTransacao dados) {
        validadoresSaque.forEach(v -> v.validar(dados));
        var conta = contaRepository.findById(dados.idConta());
        int saldoDaConta = Integer.parseInt(String.valueOf(conta.getSaldo()));
        int valorDoSaque = Integer.parseInt(String.valueOf(dados.valor()));
        conta.setSaldo(BigDecimal.valueOf(saldoDaConta - valorDoSaque));
        Transacao transacao = new Transacao(null, dados.tipoTransacao(), dados.valor(),
                LocalDateTime.now(), conta, null);
        transacaoRepository.persist(transacao);
        return transacao;
    }
}

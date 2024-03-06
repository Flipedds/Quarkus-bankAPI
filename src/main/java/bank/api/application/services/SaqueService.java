package bank.api.application.services;

import bank.api.presentation.dtos.transacao.DadosNovaTransacao;
import bank.api.domain.repositories.IContaRepository;
import bank.api.domain.entities.Transacao;
import bank.api.domain.repositories.ITransacaoRepository;
import bank.api.domain.services.ISaqueService;
import bank.api.domain.validators.IValidadorSaque;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;

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
        validadoresSaque.stream()
                .sorted(Comparator.comparingInt(IValidadorSaque::getPriority))
                .forEach(v -> v.validar(dados));

        var conta = contaRepository.findById(dados.idConta());
        BigDecimal saldoDaConta = conta.getSaldo();
        BigDecimal valorDoSaque = dados.valor();
        conta.setSaldo(saldoDaConta.subtract(valorDoSaque));
        Transacao transacao = new Transacao(null, dados.tipoTransacao(), dados.valor(),
                LocalDateTime.now(), conta, null);
        transacaoRepository.persist(transacao);
        return transacao;
    }
}

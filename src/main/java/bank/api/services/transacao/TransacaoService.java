package bank.api.services.transacao;

import java.util.List;
import java.util.Objects;

import bank.api.application.transacao.dtos.DadosListagemTransacao;
import bank.api.application.transacao.dtos.DadosNovaTransacao;
import bank.api.domain.transacao.entities.Transacao;
import bank.api.domain.transacao.repositories.ITransacaoRepository;
import bank.api.domain.transacao.services.IDepositoService;
import bank.api.domain.transacao.services.ISaqueService;
import bank.api.domain.transacao.services.ITransacaoService;
import bank.api.domain.transacao.services.ITransferenciaService;
import io.quarkus.cache.CacheResult;
import io.quarkus.panache.common.Page;
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

    @Inject
    ITransacaoRepository transacaoRepository;

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

    @Override
    @CacheResult(cacheName = "transacoes")
    public List<DadosListagemTransacao> findAllTransacoes(int pagina, int tamanho) {
        return transacaoRepository.findAll()
                .page(Page.of(pagina, tamanho))
                .stream()
                .map(DadosListagemTransacao::new)
                .toList();
    }

    @Override
    public List<DadosListagemTransacao> getExtrato(Long id) {
        return transacaoRepository.findAll()
                .stream()
                .filter(transacao -> {
                    if(transacao.getConta() != null && transacao.getContaDestino() == null && transacao.getConta().getId().equals(id)){
                        return true;
                    }
                    else return transacao.getConta() != null && transacao.getContaDestino() != null && transacao.getContaDestino().getId().equals(id);
                })
                .map(DadosListagemTransacao::new)
                .toList();
    }
}

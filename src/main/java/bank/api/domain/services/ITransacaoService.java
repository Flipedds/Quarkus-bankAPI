package bank.api.domain.services;

import java.util.List;
import bank.api.presentation.dtos.transacao.DadosListagemTransacao;
import bank.api.presentation.dtos.transacao.DadosNovaTransacao;
import bank.api.domain.entities.Transacao;
import io.quarkus.cache.CacheResult;
import jakarta.transaction.Transactional;

public interface ITransacaoService {
    @Transactional
    Transacao executarESalvarTransacao(DadosNovaTransacao dados);
    @CacheResult(cacheName = "transacoes")
    List<DadosListagemTransacao> findAllTransacoes(int pagina, int tamanho);
    @CacheResult(cacheName = "extrato")
    List<DadosListagemTransacao> getExtrato(Long id);
}

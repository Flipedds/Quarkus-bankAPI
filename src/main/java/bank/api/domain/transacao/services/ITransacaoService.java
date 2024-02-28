package bank.api.domain.transacao.services;

import java.util.List;
import bank.api.application.transacao.dtos.DadosListagemTransacao;
import bank.api.application.transacao.dtos.DadosNovaTransacao;
import bank.api.domain.transacao.entities.Transacao;
import jakarta.transaction.Transactional;

public interface ITransacaoService {
    @Transactional
    Transacao executarESalvarTransacao(DadosNovaTransacao dados);

    List<DadosListagemTransacao> findAllTransacoes(int pagina, int tamanho);

    List<DadosListagemTransacao> getExtrato(Long id);
}

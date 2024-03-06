package bank.api.presentation.dtos.transacao;

import bank.api.domain.entities.Transacao;
import bank.api.domain.enums.TipoTransacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DadosListagemTransacao(
        Long id,
        Long conta,
        Long contaDestino,
        BigDecimal valor,
        LocalDateTime data,
        TipoTransacao tipoTransacao) {
            public DadosListagemTransacao(Transacao transacao){
                this(transacao.getId(),
                        transacao.getConta().getId(),
                        transacao.getContaDestino() != null ? transacao.getContaDestino().getId() : 0L,
                        transacao.getValor(),
                        transacao.getData(),
                        transacao.getTipoTransacao());
            }
        }

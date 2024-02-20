package bank.api.application.transacao.dtos;

import bank.api.domain.transacao.enums.TipoTransacao;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DadosNovaTransacao(
        @NotNull TipoTransacao tipoTransacao,
        @NotNull BigDecimal valor,
        @NotNull Long idConta,
        Long idContaDestino) { }

package bank.api.presentation.dtos.transacao;

import bank.api.domain.enums.TipoTransacao;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DadosNovaTransacao(
        @NotNull TipoTransacao tipoTransacao,
        @NotNull BigDecimal valor,
        @NotNull Long idConta,
        Long idContaDestino) { }

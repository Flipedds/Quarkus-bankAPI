package bank.api.presentation.dtos.conta;

import bank.api.domain.enums.TipoConta;
import bank.api.domain.entities.Conta;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DadosDetalhamentoConta(
        Long id,
        TipoConta tipoConta,
        Long clienteId,
        LocalDateTime dataCriacao,
        BigDecimal saldo,
        Boolean estaAtiva) {
    public DadosDetalhamentoConta(Conta conta) {
        this(conta.getId(),
                conta.getTipoConta(),
                conta.getCliente().getId(),
                conta.getData(),
                conta.getSaldo(),
                conta.getEstaAtiva());
    }
}

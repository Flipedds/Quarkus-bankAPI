package bank.api.presentation.dtos.conta;

import bank.api.domain.enums.TipoConta;
import bank.api.domain.entities.Conta;

import java.math.BigDecimal;

public record DadosListagemConta(
        Long id,
        TipoConta tipoConta,
        Long clienteId,
        BigDecimal saldo) {
    public DadosListagemConta(Conta conta){
        this(conta.getId(), conta.getTipoConta(), conta.getCliente().getId(), conta.getSaldo());
    }
}

package bank.api.domain.conta.dtos;

import bank.api.domain.conta.enums.TipoConta;
import bank.api.domain.conta.models.Conta;

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

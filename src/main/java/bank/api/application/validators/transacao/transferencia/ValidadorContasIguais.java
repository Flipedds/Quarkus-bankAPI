package bank.api.application.validators.transacao.transferencia;

import bank.api.domain.exceptions.ConflictException;
import bank.api.domain.validators.IValidadorTransferencia;
import bank.api.presentation.dtos.transacao.DadosNovaTransacao;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ValidadorContasIguais implements IValidadorTransferencia {
    @Override
    public void validar(DadosNovaTransacao dados) {
        if (dados.idConta().equals(dados.idContaDestino())) {
            throw new ConflictException("Conta de origem e destino não podem ser iguais !");
        }
    }

    @Override
    public Integer getPriority() {
        return 3;
    }
}

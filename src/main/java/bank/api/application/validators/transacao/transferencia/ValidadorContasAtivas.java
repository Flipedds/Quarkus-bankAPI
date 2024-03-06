package bank.api.application.validators.transacao.transferencia;

import bank.api.presentation.dtos.transacao.DadosNovaTransacao;
import bank.api.domain.repositories.IContaRepository;
import bank.api.domain.validators.IValidadorTransferencia;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.hibernate.PropertyValueException;

@ApplicationScoped
public class ValidadorContasAtivas implements IValidadorTransferencia {
    @Inject
    IContaRepository contaRepository;

    @Override
    public void validar(DadosNovaTransacao dados) {
        var conta = contaRepository.findById(dados.idConta());
        var contaDestino = contaRepository.findById(dados.idContaDestino());

        if (!conta.getEstaAtiva()){
            throw new PropertyValueException("conta deve estar ativa para realizar transferencia", "DadosNovaTransacao", "idConta");
        }

        if(!contaDestino.getEstaAtiva()){
            throw new PropertyValueException("conta destino deve estar ativa para receber a transferência", "DadosNovaTransacao", "idContaDestino");
        }
    }

    @Override
    public Integer getPriority() {
        return 2;
    }
}

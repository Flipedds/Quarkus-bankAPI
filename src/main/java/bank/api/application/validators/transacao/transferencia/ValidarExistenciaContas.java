package bank.api.application.validators.transacao.transferencia;

import bank.api.presentation.dtos.transacao.DadosNovaTransacao;
import bank.api.domain.repositories.IContaRepository;
import bank.api.domain.validators.IValidadorTransferencia;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;

@ApplicationScoped
public class ValidarExistenciaContas implements IValidadorTransferencia {
    @Inject
    IContaRepository contaRepository;

    @Override
    public void validar(DadosNovaTransacao dados) {
        var conta = contaRepository.findById(dados.idConta());
        var contaDestino = contaRepository.findById(dados.idContaDestino());
        if(conta == null){
            throw new EntityNotFoundException("Conta não encontrada !");
        }
        if(contaDestino == null){
            throw new EntityNotFoundException("Conta destino não encontrada !");
        }
    }

    @Override
    public Integer getPriority() {
        return 1;
    }
}

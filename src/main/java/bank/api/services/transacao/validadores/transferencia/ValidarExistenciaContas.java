package bank.api.services.transacao.validadores.transferencia;

import bank.api.application.transacao.dtos.DadosNovaTransacao;
import bank.api.domain.conta.repositories.IContaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;

@ApplicationScoped
public class ValidarExistenciaContas implements IValidadorTransferencia{
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
}

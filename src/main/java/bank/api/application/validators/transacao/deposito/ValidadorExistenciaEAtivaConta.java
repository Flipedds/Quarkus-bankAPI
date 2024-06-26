package bank.api.application.validators.transacao.deposito;

import bank.api.presentation.dtos.transacao.DadosNovaTransacao;
import bank.api.domain.repositories.IContaRepository;
import bank.api.domain.validators.IValidadorDeposito;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.PropertyValueException;

@ApplicationScoped
public class ValidadorExistenciaEAtivaConta implements IValidadorDeposito {

    @Inject
    IContaRepository contaRepository;
    @Override
    public void validar(DadosNovaTransacao dados) {
        var conta = contaRepository.findById(dados.idConta());
        if(conta == null){
            throw new EntityNotFoundException("Conta não encontrada !");
        }
        if(!conta.getEstaAtiva()){
            throw new PropertyValueException("conta deve estar ativa para realizar deposito !", "DadosNovaTransacao", "idConta");
        }
    }

    @Override
    public Integer getPriority() {
        return 1;
    }
}

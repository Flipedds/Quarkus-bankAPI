package bank.api.services.transacao.validadores.transferencia;

import bank.api.application.transacao.dtos.DadosNovaTransacao;
import bank.api.domain.conta.repositories.IContaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.hibernate.PropertyValueException;

@ApplicationScoped
public class ValidadorSaldoConta implements IValidadorTransferencia{
    @Inject
    IContaRepository contaRepository;
    @Override
    public void validar(DadosNovaTransacao dados) {
        var conta = contaRepository.findById(dados.idConta());
        int saldoDaConta = Integer.parseInt(String.valueOf(conta.getSaldo()));
        int valorDaTransferencia = Integer.parseInt(String.valueOf(dados.valor()));
        if(valorDaTransferencia > saldoDaConta){
            throw new PropertyValueException("saldo insuficiente para realizar transferencia", "DadosNovaTransacao", "valor");
        }
    }
}

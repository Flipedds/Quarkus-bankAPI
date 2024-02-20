package bank.api.services.transacao.validadores.saque;

import bank.api.application.transacao.dtos.DadosNovaTransacao;
import bank.api.domain.conta.repositories.IContaRepository;
import bank.api.domain.transacao.validators.IValidadorSaque;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.hibernate.PropertyValueException;

@ApplicationScoped
public class ValidadorSaldoEValorDoSaque implements IValidadorSaque {
    @Inject
    IContaRepository contaRepository;
    @Override
    public void validar(DadosNovaTransacao dados) {
        var conta = contaRepository.findById(dados.idConta());
        int saldoDaConta = Integer.parseInt(String.valueOf(conta.getSaldo()));
        int valorDoSaque = Integer.parseInt(String.valueOf(dados.valor()));

        if(saldoDaConta <= 0){
            throw new PropertyValueException("Saldo insuficiente para realizar o saque", "DadosNovaTransacao", "valor");
        }

        if(saldoDaConta < valorDoSaque){
            throw new PropertyValueException("Saldo insuficiente para realizar o saque", "DadosNovaTransacao", "valor");
        }
    }
}

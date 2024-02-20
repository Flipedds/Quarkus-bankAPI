package bank.api.services.transacao.validadores.deposito;

import bank.api.application.transacao.dtos.DadosNovaTransacao;
import jakarta.enterprise.context.ApplicationScoped;
import org.hibernate.PropertyValueException;

@ApplicationScoped
public class ValidadorValorDoDeposito implements IValidadorDeposito{
    @Override
    public void validar(DadosNovaTransacao dados) {
        int valorDoDeposito = Integer.parseInt(String.valueOf(dados.valor()));
        if(valorDoDeposito <= 0){
            throw new PropertyValueException("Valor do depósito deve ser maior que 0 !", "DadosNovaTransacao", "valor");
        }
    }
}

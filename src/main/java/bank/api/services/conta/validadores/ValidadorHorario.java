package bank.api.services.conta.validadores;

import bank.api.application.conta.dtos.DadosCadastroConta;
import bank.api.domain.conta.validators.Validador;
import bank.api.infra.exceptions.ClosedBankException;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalTime;

@ApplicationScoped
public class ValidadorHorario implements Validador {
    @Override
    public void validar(DadosCadastroConta dados) {
        LocalTime horaAtual = LocalTime.now();
        LocalTime horaDeAbertura = LocalTime.of(10, 0);
        LocalTime horaDeFechamento = LocalTime.of(16, 0);

        if(horaAtual.isBefore(horaDeAbertura) || horaAtual.isAfter(horaDeFechamento)){
            throw new ClosedBankException(
                    "Não foi possível abrir a conta, fora do horário de funcionamento !");
        }
    }
}

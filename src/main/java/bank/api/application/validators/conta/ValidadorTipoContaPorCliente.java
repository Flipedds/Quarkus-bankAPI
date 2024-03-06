package bank.api.application.validators.conta;

import bank.api.presentation.dtos.conta.DadosCadastroConta;
import bank.api.domain.validators.Validador;
import bank.api.domain.exceptions.ConflictException;
import bank.api.infra.repositories.ContaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
@ApplicationScoped
public class ValidadorTipoContaPorCliente implements Validador {
    @Inject
    ContaRepository contaRepository;
    @Override
    public void validar(DadosCadastroConta dados) {
        if(contaRepository.findContaByClienteAndTipo(dados.clienteId(), dados.tipoConta()) != null){
            throw new ConflictException(
                    "Cliente já possui uma conta deste tipo: " + dados.tipoConta());
        }
    }
}

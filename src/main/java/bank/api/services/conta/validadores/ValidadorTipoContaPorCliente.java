package bank.api.services.conta.validadores;

import bank.api.application.conta.dtos.DadosCadastroConta;
import bank.api.infra.exceptions.ConflictException;
import bank.api.infra.repositories.conta.ContaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
@ApplicationScoped
public class ValidadorTipoContaPorCliente implements Validador{
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

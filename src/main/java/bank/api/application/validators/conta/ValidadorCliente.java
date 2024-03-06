package bank.api.application.validators.conta;

import bank.api.presentation.dtos.conta.DadosCadastroConta;
import bank.api.domain.validators.Validador;
import bank.api.infra.repositories.ClienteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;

@ApplicationScoped
public class ValidadorCliente implements Validador {
    @Inject
    ClienteRepository clienteRepository;
    @Override
    public void validar(DadosCadastroConta dados) {
        var cliente = clienteRepository.findById(dados.clienteId());
        if (cliente == null){
            throw new EntityNotFoundException("Cliente não encontrado !");
        }
    }
}

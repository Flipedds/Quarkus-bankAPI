package bank.api.services.conta.validadores;

import bank.api.application.conta.dtos.DadosCadastroConta;
import bank.api.infra.repositories.cliente.ClienteRepository;
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

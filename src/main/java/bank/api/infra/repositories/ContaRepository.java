package bank.api.infra.repositories;


import bank.api.domain.enums.TipoConta;
import bank.api.domain.entities.Conta;
import bank.api.domain.repositories.IContaRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ContaRepository implements IContaRepository {
    public Conta findContaByClienteAndTipo(Long id, TipoConta tipoConta){
        return find("cliente.id = ?1 and tipoConta = ?2", id, tipoConta).firstResult();
    }
}

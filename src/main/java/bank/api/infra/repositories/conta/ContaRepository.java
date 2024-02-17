package bank.api.infra.repositories.conta;


import bank.api.domain.conta.enums.TipoConta;
import bank.api.domain.conta.entities.Conta;
import bank.api.domain.conta.repositories.IContaRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ContaRepository implements IContaRepository {
    public Conta findContaByClienteAndTipo(Long id, TipoConta tipoConta){
        return find("cliente.id = ?1 and tipoConta = ?2", id, tipoConta).firstResult();
    }
}

package bank.api.infra.repositories;


import bank.api.domain.conta.enums.TipoConta;
import bank.api.domain.conta.models.Conta;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ContaRepository implements PanacheRepository<Conta> {
    public List<Conta> findContasAtivas(){
        return list("estaAtiva", true);
    }

    public Conta findContaByClienteAndTipo(Long id, TipoConta tipoConta){
        return find("cliente.id = ?1 and tipoConta = ?2", id, tipoConta).firstResult();
    }
}

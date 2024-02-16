package bank.api.domain.conta.repositories;

import bank.api.domain.conta.entities.Conta;
import bank.api.domain.conta.enums.TipoConta;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import java.util.List;

public interface IContaRepository extends PanacheRepository<Conta> {
    List<Conta> findContasAtivas();
    Conta findContaByClienteAndTipo(Long id, TipoConta tipoConta);
}

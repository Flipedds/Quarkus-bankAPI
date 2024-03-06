package bank.api.domain.repositories;

import bank.api.domain.entities.Conta;
import bank.api.domain.enums.TipoConta;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

public interface IContaRepository extends PanacheRepository<Conta> {
    Conta findContaByClienteAndTipo(Long id, TipoConta tipoConta);
}

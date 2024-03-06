package bank.api.domain.repositories;

import bank.api.domain.entities.Cliente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

public interface IClienteRepository extends PanacheRepository<Cliente> {
}

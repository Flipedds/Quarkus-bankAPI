package bank.api.domain.cliente.repositories;

import bank.api.domain.cliente.entities.Cliente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

public interface IClienteRepository extends PanacheRepository<Cliente> {
}

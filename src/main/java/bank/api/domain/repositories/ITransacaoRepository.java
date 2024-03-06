package bank.api.domain.repositories;

import bank.api.domain.entities.Transacao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

public interface ITransacaoRepository extends PanacheRepository<Transacao> {
}

package bank.api.domain.transacao.repositories;

import bank.api.domain.transacao.entities.Transacao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

public interface ITransacaoRepository extends PanacheRepository<Transacao> {
}

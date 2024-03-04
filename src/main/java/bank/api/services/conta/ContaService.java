package bank.api.services.conta;

import bank.api.application.conta.dtos.DadosCadastroConta;
import bank.api.application.conta.dtos.DadosListagemConta;
import bank.api.domain.cliente.repositories.IClienteRepository;
import bank.api.domain.conta.entities.Conta;
import bank.api.domain.conta.repositories.IContaRepository;
import bank.api.domain.conta.validators.Validador;
import bank.api.domain.conta.services.IContaService;
import bank.api.infra.exceptions.ConflictException;
import io.quarkus.cache.CacheResult;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class ContaService implements IContaService {
    @Inject
    IContaRepository contaRepository;

    @Inject
    IClienteRepository clienteRepository;

    @Inject
    Instance<Validador> validadores;

    @Transactional
    public Conta addConta(DadosCadastroConta dados){

        validadores.forEach(v -> v.validar(dados));

        var cliente = clienteRepository.findById(dados.clienteId());

        var conta = new Conta(
                null, cliente,
                BigDecimal.ZERO,
                dados.tipoConta(),
                LocalDateTime.now(),
                true);
        contaRepository.persist(conta);
        return conta;
    }

    @CacheResult(cacheName = "contas")
    public List<DadosListagemConta> findAllContas(Integer pagina, Integer tamanho){
        return contaRepository.findAll()
                .page(Page.of(pagina, tamanho))
                .stream()
                .filter(Conta::getEstaAtiva)
                .map(DadosListagemConta::new)
                .toList();
    }

    public Conta findConta(Long id){
        var conta = contaRepository.findById(id);
        if(conta == null){
            throw new EntityNotFoundException("Conta não encontrada !");
        }
        return conta;
    }

    @Transactional
    public void DeleteLogicConta(Long id){
        var conta = contaRepository.findById(id);
        if(conta == null){
            throw new EntityNotFoundException("Conta não encontrada !");
        }
        if(!conta.getEstaAtiva()){
            throw new EntityNotFoundException("Conta não está ativa !");
        }
        if(conta.getSaldo().compareTo(BigDecimal.ZERO) > 0)
        {
            throw new ConflictException("Conta possui saldo, não é possível desativar !");
        }
        conta.delete();
    }
}

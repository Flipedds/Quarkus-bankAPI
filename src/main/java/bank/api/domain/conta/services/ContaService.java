package bank.api.domain.conta.services;

import bank.api.domain.conta.dtos.DadosCadastroConta;
import bank.api.domain.conta.dtos.DadosListagemConta;
import bank.api.domain.conta.models.Conta;
import bank.api.domain.conta.services.validadores.Validador;
import bank.api.infra.exceptions.ConflictException;
import bank.api.infra.repositories.ClienteRepository;
import bank.api.infra.repositories.ContaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class ContaService {
    @Inject
    ContaRepository contaRepository;

    @Inject
    ClienteRepository clienteRepository;

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

    public List<DadosListagemConta> findAllContas(){
        return contaRepository.findContasAtivas()
                .stream()
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

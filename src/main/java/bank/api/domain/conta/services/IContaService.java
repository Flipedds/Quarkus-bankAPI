package bank.api.domain.conta.services;

import bank.api.application.conta.dtos.DadosCadastroConta;
import bank.api.application.conta.dtos.DadosListagemConta;
import bank.api.domain.conta.entities.Conta;
import jakarta.transaction.Transactional;

import java.util.List;

public interface IContaService {
    @Transactional
    Conta addConta(DadosCadastroConta dados);
    public List<DadosListagemConta> findAllContas();
    public Conta findConta(Long id);
    @Transactional
    public void DeleteLogicConta(Long id);
}

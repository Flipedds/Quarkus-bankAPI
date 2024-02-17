package bank.api.domain.conta.services;

import bank.api.application.conta.dtos.DadosCadastroConta;
import bank.api.application.conta.dtos.DadosListagemConta;
import bank.api.domain.conta.entities.Conta;
import jakarta.transaction.Transactional;

import java.util.List;

public interface IContaService {
    @Transactional
    Conta addConta(DadosCadastroConta dados);
    List<DadosListagemConta> findAllContas(Integer pagina, Integer tamanho);
    Conta findConta(Long id);
    @Transactional
    void DeleteLogicConta(Long id);
}

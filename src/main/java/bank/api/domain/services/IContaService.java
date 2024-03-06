package bank.api.domain.services;

import bank.api.presentation.dtos.conta.DadosCadastroConta;
import bank.api.presentation.dtos.conta.DadosListagemConta;
import bank.api.domain.entities.Conta;
import io.quarkus.cache.CacheResult;
import jakarta.transaction.Transactional;

import java.util.List;

public interface IContaService {
    @Transactional
    Conta addConta(DadosCadastroConta dados);
    @CacheResult(cacheName = "contas")
    List<DadosListagemConta> findAllContas(Integer pagina, Integer tamanho);
    @CacheResult(cacheName = "findConta")
    Conta findConta(Long id);
    @Transactional
    void DeleteLogicConta(Long id);
}

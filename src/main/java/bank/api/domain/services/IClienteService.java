package bank.api.domain.services;

import bank.api.presentation.dtos.cliente.DadosAtualizarCliente;
import bank.api.presentation.dtos.cliente.DadosCadastroCliente;
import bank.api.presentation.dtos.cliente.DadosListagemCliente;
import bank.api.domain.entities.Cliente;
import io.quarkus.cache.CacheResult;
import jakarta.transaction.Transactional;

import java.util.List;

public interface IClienteService {
    @CacheResult(cacheName = "clientes")
    List<DadosListagemCliente> findAllClientes(int pagina, int tamanho, String order);
    @CacheResult(cacheName = "findCliente")
    Cliente findCliente(Long id);
    @Transactional
    Cliente addCliente(DadosCadastroCliente dados);
    @Transactional
    void deleteCliente(Long id);
    @Transactional
    Cliente putCliente(DadosAtualizarCliente dados);
}

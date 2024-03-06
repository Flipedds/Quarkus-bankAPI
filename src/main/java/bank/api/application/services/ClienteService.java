package bank.api.application.services;

import bank.api.presentation.dtos.cliente.DadosAtualizarCliente;
import bank.api.presentation.dtos.cliente.DadosCadastroCliente;
import bank.api.presentation.dtos.cliente.DadosListagemCliente;
import bank.api.domain.entities.Cliente;
import bank.api.domain.repositories.IClienteRepository;
import bank.api.domain.services.IClienteService;
import io.quarkus.cache.CacheResult;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class ClienteService implements IClienteService {
    @Inject
    IClienteRepository clienteRepository;
    @Override
    @CacheResult(cacheName = "clientes")
    public List<DadosListagemCliente> findAllClientes(int pagina, int tamanho, String order){
        Comparator<DadosListagemCliente> comparatorPorNome = Comparator.comparing(DadosListagemCliente::nome);
        if(Objects.equals(order, "reversed")){
            return clienteRepository.findAll().page(Page.of(pagina, tamanho))
                    .stream().map(DadosListagemCliente::new)
                    .sorted(comparatorPorNome.reversed())
                    .toList();
        }
        return clienteRepository.findAll().page(Page.of(pagina, tamanho))
                .stream().map(DadosListagemCliente::new)
                .sorted(comparatorPorNome).toList();
    }
    @Override
    @CacheResult(cacheName = "findCliente")
    public Cliente findCliente(Long id){
        var cliente = clienteRepository.findById(id);
        if(cliente == null){
            throw new EntityNotFoundException("Cliente não encontrado !");
        }
        return clienteRepository.findById(id);
    }
    @Override
    @Transactional
    public Cliente addCliente(DadosCadastroCliente dados){
        var cliente = new Cliente(dados);
        clienteRepository.persist(cliente);
        return cliente;
    }
    @Override
    @Transactional
    public void deleteCliente(Long id){
        var cliente = clienteRepository.findById(id);
        if(cliente == null){
            throw new EntityNotFoundException("Cliente não encontrado !");
        }
        clienteRepository.deleteById(id);
    }
    @Override
    @Transactional
    public Cliente putCliente(DadosAtualizarCliente dados){
        var cliente = clienteRepository.findById(dados.id());
        if(cliente == null){
            throw new EntityNotFoundException("Cliente não encontrado !");
        }
        cliente.atualizarInformacoes(dados);
        return cliente;
    }
}

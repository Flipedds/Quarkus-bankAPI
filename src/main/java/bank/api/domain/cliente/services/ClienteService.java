package bank.api.domain.cliente.services;

import bank.api.domain.cliente.dtos.DadosAtualizarCliente;
import bank.api.domain.cliente.dtos.DadosCadastroCliente;
import bank.api.domain.cliente.dtos.DadosListagemCliente;
import bank.api.domain.cliente.models.Cliente;
import bank.api.infra.repositories.ClienteRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class ClienteService {
    @Inject
    ClienteRepository clienteRepository;

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

    public Cliente findCliente(Long id){
        var cliente = clienteRepository.findById(id);
        if(cliente == null){
            throw new EntityNotFoundException("Cliente não encontrado !");
        }
        return clienteRepository.findById(id);
    }

    @Transactional
    public Cliente addCliente(DadosCadastroCliente dados){
        var cliente = new Cliente(dados);
        clienteRepository.persist(cliente);
        return cliente;
    }

    @Transactional
    public void deleteCliente(Long id){
        var cliente = clienteRepository.findById(id);
        if(cliente == null){
            throw new EntityNotFoundException("Cliente não encontrado !");
        }
        clienteRepository.deleteById(id);
    }

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

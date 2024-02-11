package bank.api.domain.cliente;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
public class ClienteService {
    @Inject
    ClienteRepository clienteRepository;

    public List<DadosListagemCliente> findAllClientes(){
        return clienteRepository.findAll().stream().map(DadosListagemCliente::new).toList();
    }

    public Cliente findCliente(Long id){
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
        clienteRepository.deleteById(id);
    }

    @Transactional
    public Cliente putCliente(DadosAtualizarCliente dados){
        var cliente = clienteRepository.findById(dados.id());
        cliente.atualizarInformacoes(dados);
        return cliente;
    }
}

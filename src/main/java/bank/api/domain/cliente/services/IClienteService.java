package bank.api.domain.cliente.services;

import bank.api.application.cliente.dtos.DadosAtualizarCliente;
import bank.api.application.cliente.dtos.DadosCadastroCliente;
import bank.api.application.cliente.dtos.DadosListagemCliente;
import bank.api.domain.cliente.entities.Cliente;
import jakarta.transaction.Transactional;

import java.util.List;

public interface IClienteService {
    List<DadosListagemCliente> findAllClientes(int pagina, int tamanho, String order);
    Cliente findCliente(Long id);
    @Transactional
    Cliente addCliente(DadosCadastroCliente dados);
    @Transactional
    void deleteCliente(Long id);
    @Transactional
    Cliente putCliente(DadosAtualizarCliente dados);
}

package bank.api.unitarios.cliente;

import bank.api.application.cliente.dtos.DadosAtualizarCliente;
import bank.api.application.cliente.dtos.DadosCadastroCliente;
import bank.api.application.cliente.dtos.DadosEndereco;
import bank.api.domain.cliente.entities.Cliente;
import bank.api.domain.cliente.repositories.IClienteRepository;
import bank.api.domain.cliente.services.IClienteService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@QuarkusTest
@TestSecurity(user = "test", roles = {"manager"})
class ClienteServiceUnitTest {
    @Inject
    IClienteService clienteService;

    @InjectMock
    IClienteRepository clienteRepository;


    private static DadosCadastroCliente getDadosCadastroCliente() {
        DadosEndereco endereco = new DadosEndereco(
                "rua teste", "teste", "95849584", "teste", "te", "", 20);

        return new DadosCadastroCliente(
                "teste", "59584958495", "feminino", "968695869", "teste@gmail.com", endereco);
    }

    @Test
    @DisplayName("buscando Cliente - service!")
    void findCliente() {
        // arrange
        DadosCadastroCliente dadosCadastroCliente = getDadosCadastroCliente();

        Cliente cliente = new Cliente(dadosCadastroCliente);
        Mockito.when(clienteRepository.findById(1L)).thenReturn(cliente);

        // act
        Cliente clienteRetornado = clienteService.findCliente(1L);

        // assert
        assertNotNull(clienteRetornado);
        assertEquals(cliente.getId(), clienteRetornado.getId());
        assertEquals(cliente.getNome(), clienteRetornado.getNome());
        assertEquals(cliente.getCpf(), clienteRetornado.getCpf());
        assertEquals(cliente.getTelefone(), clienteRetornado.getTelefone());
        assertEquals(cliente.getEmail(), clienteRetornado.getEmail());
        assertEquals(cliente.getGenero(), clienteRetornado.getGenero());

        assertEquals(cliente.getEndereco().getLogradouro(), clienteRetornado.getEndereco().getLogradouro());
        assertEquals(cliente.getEndereco().getCep(), clienteRetornado.getEndereco().getCep());
        assertEquals(cliente.getEndereco().getBairro(), clienteRetornado.getEndereco().getBairro());
        assertEquals(cliente.getEndereco().getCidade(), clienteRetornado.getEndereco().getCidade());
        assertEquals(cliente.getEndereco().getNumero(), clienteRetornado.getEndereco().getNumero());
        assertEquals(cliente.getEndereco().getComplemento(), clienteRetornado.getEndereco().getComplemento());
        assertEquals(cliente.getEndereco().getUf(), clienteRetornado.getEndereco().getUf());
    }
    @Test
    @DisplayName("Falha ao buscar Cliente - service!")
    void failFindCliente() {
        Mockito.when(clienteRepository.findById(any())).thenReturn(null);
        // assert
        assertThrows(EntityNotFoundException.class, () -> clienteService.deleteCliente(2L));
    }


    @Test
    @DisplayName("Removendo Cliente - service!")
    void deleteCliente() {
        // arrange
        DadosCadastroCliente dadosCadastroCliente = getDadosCadastroCliente();

        Cliente cliente = new Cliente(dadosCadastroCliente);
        Mockito.when(clienteRepository.findById(1L)).thenReturn(cliente);

        assertDoesNotThrow(() -> clienteService.deleteCliente(1L));
    }

    @Test
    @DisplayName("Falha ao Remover Cliente - service!")
    void FailDeleteCliente() {
        Mockito.when(clienteRepository.findById(2L)).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> clienteService.deleteCliente(2L));
    }


    @Test
    @DisplayName("Atualizando Cliente - service!")
    void putCliente() {
        // arrange
        DadosAtualizarCliente dadosAtualizarCliente = new DadosAtualizarCliente(1L, "968695868", null, null);
        DadosCadastroCliente dadosCadastroCliente = getDadosCadastroCliente();
        Cliente cliente = new Cliente(dadosCadastroCliente);
        cliente.setId(dadosAtualizarCliente.id());

        Mockito.when(clienteRepository.findById(1L)).thenReturn(cliente);

        // act
        Cliente clienteRetornado = clienteService.putCliente(dadosAtualizarCliente);

        // assert
        assertNotNull(clienteRetornado);
        assertEquals(1L, clienteRetornado.getId());
        assertEquals(dadosAtualizarCliente.telefone(), clienteRetornado.getTelefone());
    }

    @Test
    @DisplayName("Falha ao atualizar Cliente - service!")
    void FailPutCliente() {
        // arrange
        DadosAtualizarCliente dadosAtualizarCliente = new DadosAtualizarCliente(2L, "968695868", null, null);
        Mockito.when(clienteRepository.findById(2L)).thenReturn(null);

        // act and assert
        assertThrows(EntityNotFoundException.class, () -> clienteService.putCliente(dadosAtualizarCliente));
    }
}
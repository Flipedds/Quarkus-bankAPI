package bank.api.unitarios.cliente;

import bank.api.application.cliente.dtos.*;
import bank.api.application.cliente.resources.ClientesResource;
import bank.api.domain.cliente.entities.Cliente;
import bank.api.domain.cliente.services.IClienteService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@QuarkusTest
@TestSecurity(user = "test", roles = {"manager"})
class ClientesResourceUnitTest {

    @Inject
    ClientesResource clientesResource;

    @InjectMock
    IClienteService clienteService;


    private static DadosCadastroCliente getDadosCadastroCliente() {
        DadosEndereco endereco = new DadosEndereco(
                "rua teste", "teste", "95849584", "teste", "te", "", 20);

        return new DadosCadastroCliente(
                "teste", "59584958495", "feminino", "968695869", "teste@gmail.com", endereco);
    }

    @Test
    @DisplayName("Recuperando clientes com sucesso !")
    void recuperaClientes() {

        // arrange
        DadosCadastroCliente dadosCadastroCliente = getDadosCadastroCliente();

        DadosListagemCliente clienteUm = new DadosListagemCliente(new Cliente(dadosCadastroCliente));
        DadosListagemCliente clienteDois = new DadosListagemCliente(new Cliente(dadosCadastroCliente));
        DadosListagemCliente clienteTres = new DadosListagemCliente(new Cliente(dadosCadastroCliente));
        DadosListagemCliente clienteQuatro = new DadosListagemCliente(new Cliente(dadosCadastroCliente));

        List<DadosListagemCliente> dadosListagemClientes = new ArrayList<>();
        dadosListagemClientes.add(clienteUm);
        dadosListagemClientes.add(clienteDois);
        dadosListagemClientes.add(clienteTres);
        dadosListagemClientes.add(clienteQuatro);

        Mockito.when(clienteService.findAllClientes(0, 10, "reversed")).thenReturn(dadosListagemClientes);

        // act
        Response response = clientesResource.recuperaClientes(0, 10, "reversed");

        // assert
        assertNotNull(response);

        List<DadosListagemCliente> dadosListagemClientesRetornado = (List<DadosListagemCliente>) response.getEntity();

        assertNotNull(dadosListagemClientesRetornado);

        for (int i = 0; i < 4; i++) {
            assertEquals(dadosListagemClientes.get(i).id(), dadosListagemClientesRetornado.get(i).id());
            assertEquals(dadosListagemClientes.get(i).nome(), dadosListagemClientesRetornado.get(i).nome());
            assertEquals(dadosListagemClientes.get(i).email(), dadosListagemClientesRetornado.get(i).email());
            assertEquals(dadosListagemClientes.get(i).telefone(), dadosListagemClientesRetornado.get(i).telefone());
        }
    }

    @Test
    @DisplayName("Recuperando cliente com sucesso !")
    void recuperaCliente() {

        // arrange
        DadosCadastroCliente dadosCadastroCliente = getDadosCadastroCliente();

        Cliente cliente = new Cliente(dadosCadastroCliente);
        Mockito.when(clienteService.findCliente(any())).thenReturn(cliente);

        // act
        Response response = clientesResource.recuperaCliente(3L);

        // assert
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        DadosDetalhamentoCliente dadosDoClienteRetornado = (DadosDetalhamentoCliente) response.getEntity();
        assertNotNull(response.getEntity());
        assertNull(dadosDoClienteRetornado.id());
        assertEquals(dadosCadastroCliente.nome(), dadosDoClienteRetornado.nome());
        assertEquals(dadosCadastroCliente.cpf(), dadosDoClienteRetornado.cpf());
        assertEquals(dadosCadastroCliente.genero(), dadosDoClienteRetornado.genero());
        assertEquals(dadosCadastroCliente.email(), dadosDoClienteRetornado.email());
        assertEquals(dadosCadastroCliente.telefone(), dadosDoClienteRetornado.telefone());

        assertEquals(dadosCadastroCliente.endereco().logradouro(), dadosDoClienteRetornado.endereco().getLogradouro());
        assertEquals(dadosCadastroCliente.endereco().bairro(), dadosDoClienteRetornado.endereco().getBairro());
        assertEquals(dadosCadastroCliente.endereco().cep(), dadosDoClienteRetornado.endereco().getCep());
        assertEquals(dadosCadastroCliente.endereco().cidade(), dadosDoClienteRetornado.endereco().getCidade());
        assertEquals(dadosCadastroCliente.endereco().uf(), dadosDoClienteRetornado.endereco().getUf());
        assertEquals(dadosCadastroCliente.endereco().complemento(), dadosDoClienteRetornado.endereco().getComplemento());
        assertEquals(dadosCadastroCliente.endereco().numero(), dadosDoClienteRetornado.endereco().getNumero());
    }

    @Test
    @DisplayName("Falha ao recuperar Cliente!")
    void falhaAoRecuperarCliente() {
        Mockito.when(clienteService.findCliente(1L)).thenThrow(EntityNotFoundException.class);
        // assert
        assertThrows(EntityNotFoundException.class, () -> clientesResource.recuperaCliente(1L));
    }


    @Test
    @DisplayName("Criando novo cliente com sucesso !")
    void novoCliente(){
        // arrange
        DadosCadastroCliente dadosCadastroCliente = getDadosCadastroCliente();

        Cliente cliente = new Cliente(dadosCadastroCliente);
        Mockito.when(clienteService.addCliente(dadosCadastroCliente)).thenReturn(cliente);

        // act
        Response response = clientesResource.addCliente(dadosCadastroCliente);

        //assert
        assertNotNull(response);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        Cliente clienteRetornado = (Cliente) response.getEntity();
        assertNotNull(response.getEntity());
        assertNull(clienteRetornado.getId());
        assertEquals(dadosCadastroCliente.nome(), clienteRetornado.getNome());
        assertEquals(dadosCadastroCliente.cpf(), clienteRetornado.getCpf());
        assertEquals(dadosCadastroCliente.genero(), clienteRetornado.getGenero());
        assertEquals(dadosCadastroCliente.email(), clienteRetornado.getEmail());
        assertEquals(dadosCadastroCliente.telefone(), clienteRetornado.getTelefone());

        assertEquals(dadosCadastroCliente.endereco().logradouro(), clienteRetornado.getEndereco().getLogradouro());
        assertEquals(dadosCadastroCliente.endereco().bairro(), clienteRetornado.getEndereco().getBairro());
        assertEquals(dadosCadastroCliente.endereco().cep(), clienteRetornado.getEndereco().getCep());
        assertEquals(dadosCadastroCliente.endereco().cidade(), clienteRetornado.getEndereco().getCidade());
        assertEquals(dadosCadastroCliente.endereco().uf(), clienteRetornado.getEndereco().getUf());
        assertEquals(dadosCadastroCliente.endereco().complemento(), clienteRetornado.getEndereco().getComplemento());
        assertEquals(dadosCadastroCliente.endereco().numero(), clienteRetornado.getEndereco().getNumero());
    }

    @Test
    @DisplayName("Removendo Cliente com sucesso!")
    void removendoCliente() {
        clienteService.deleteCliente(100L);
        Mockito.verify(clienteService).deleteCliente(100L);

        assertDoesNotThrow(() -> clientesResource.removeCliente(100L));
    }

    @Test
    @DisplayName("Falha ao remover Cliente que não existe!")
    void falhaAoRemoverCliente() {
        Mockito.doThrow(EntityNotFoundException.class).when(clienteService).deleteCliente(20L);
        assertThrows(EntityNotFoundException.class, () -> clientesResource.removeCliente(20L));
    }
    @Test
    @DisplayName("Atualizando Cliente com sucesso !")
    void AtualizandoCliente() {
        // arrange
        DadosAtualizarCliente dadosAtualizarCliente = new DadosAtualizarCliente(1L, "968695868", null, null);
        DadosCadastroCliente dadosCadastroCliente = getDadosCadastroCliente();
        Cliente cliente = new Cliente(dadosCadastroCliente);
        cliente.setId(dadosAtualizarCliente.id());
        cliente.atualizarInformacoes(dadosAtualizarCliente);

        Mockito.when(clienteService.putCliente(dadosAtualizarCliente)).thenReturn(cliente);

        // act
        Response response = clientesResource.atualizaCliente(dadosAtualizarCliente);

        // assert
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        DadosDetalhamentoCliente clienteRetornado = (DadosDetalhamentoCliente) response.getEntity();
        assertNotNull(response.getEntity());
        assertEquals(1L, clienteRetornado.id());
        assertEquals(dadosAtualizarCliente.telefone(), clienteRetornado.telefone());
    }


    @Test
    @DisplayName("Falha ao atualizar Cliente que não existe!")
    void falhaAoAtualizarCliente() {
        DadosAtualizarCliente dadosAtualizarCliente = new DadosAtualizarCliente(100L, "null", null, null);
        Mockito.doThrow(EntityNotFoundException.class).when(clienteService).putCliente(dadosAtualizarCliente);
        assertThrows(EntityNotFoundException.class, () -> clientesResource.atualizaCliente(dadosAtualizarCliente));
    }
}
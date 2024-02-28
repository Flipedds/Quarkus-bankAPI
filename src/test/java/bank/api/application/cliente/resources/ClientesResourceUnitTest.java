package bank.api.application.cliente.resources;

import bank.api.application.cliente.dtos.DadosCadastroCliente;
import bank.api.application.cliente.dtos.DadosEndereco;
import bank.api.domain.cliente.entities.Cliente;
import bank.api.domain.cliente.services.IClienteService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;

@QuarkusTest
class ClientesResourceUnitTest {

    @Inject ClientesResource clientesResource;

    @InjectMock
    IClienteService clienteService;

    @Test
    @TestSecurity(user = "test", roles = {"manager"})
    void recuperaCliente() {
        DadosEndereco endereco = new DadosEndereco(
                "rua teste", "teste", "95849584", "teste", "te", "", 20);

        DadosCadastroCliente dadosCadastroCliente = new DadosCadastroCliente(
                "teste", "59584958495", "feminino", "968695869", "teste@gmail.com", endereco);

        Cliente cliente = new Cliente(dadosCadastroCliente);
        Mockito.when(clienteService.findCliente(any())).thenReturn(cliente);
        Response response = clientesResource.recuperaCliente(3L);
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
}
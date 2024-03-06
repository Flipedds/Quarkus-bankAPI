package bank.api.unitarios.conta;

import bank.api.domain.repositories.IClienteRepository;
import bank.api.presentation.dtos.cliente.DadosCadastroCliente;
import bank.api.presentation.dtos.cliente.DadosEndereco;
import bank.api.presentation.dtos.conta.DadosCadastroConta;
import bank.api.presentation.dtos.conta.DadosDetalhamentoConta;
import bank.api.domain.entities.Cliente;
import bank.api.domain.entities.Conta;
import bank.api.domain.enums.TipoConta;
import bank.api.domain.repositories.IContaRepository;
import bank.api.domain.services.IContaService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestSecurity(user = "test", roles = {"manager"})
public class ContaServiceUnitTest {

    @Inject
    IContaService contaService;

    @InjectMock
    IClienteRepository clienteRepository;

    @InjectMock
    IContaRepository contaRepository;

    private static Conta getConta() {
        DadosEndereco endereco = new DadosEndereco(
                "rua teste", "teste", "95849584", "teste", "te", "", 20);

        DadosCadastroCliente dadosCadastroCliente = new DadosCadastroCliente(
                "teste", "59584958495", "feminino", "968695869", "teste@gmail.com", endereco);

        return new Conta( 1L, new Cliente(dadosCadastroCliente), new BigDecimal(0), TipoConta.CORRENTE, LocalDateTime.now(), true);
    }

    private static DadosCadastroCliente getDadosCadastroCliente() {
        DadosEndereco endereco = new DadosEndereco(
                "rua teste", "teste", "95849584", "teste", "te", "", 20);

        return new DadosCadastroCliente(
                "teste", "59584958495", "feminino", "968695869", "teste@gmail.com", endereco);
    }

    @Test
    public void addConta(){
        // arrange
        DadosCadastroCliente dadosCadastroCliente = getDadosCadastroCliente();
        Cliente cliente = new Cliente(dadosCadastroCliente);
        cliente.setId(1L);
        DadosCadastroConta dadosCadastroConta = new DadosCadastroConta(TipoConta.CORRENTE, 1L);
        Mockito.when(clienteRepository.findById(1L)).thenReturn(cliente);

        // act
        Conta contaRetornada = contaService.addConta(dadosCadastroConta);

        // assert
        assertNotNull(contaRetornada);
        assertNull(contaRetornada.getId());
        assertEquals(TipoConta.CORRENTE, contaRetornada.getTipoConta());
        assertEquals(true, contaRetornada.getEstaAtiva());
        assertEquals(new BigDecimal(0), contaRetornada.getSaldo());
    }

    @Test
    public void findConta(){
        // arrange
        Conta conta = getConta();
        Mockito.when(contaRepository.findById(1L)).thenReturn(conta);

        // act
        Conta contaRetornada  = contaService.findConta(1L);
        DadosDetalhamentoConta dadosDetalhamentoContaRetornada = new DadosDetalhamentoConta(contaRetornada);

        // assert
        assertNotNull(dadosDetalhamentoContaRetornada);
        assertEquals(conta.getId(), dadosDetalhamentoContaRetornada.id());
        assertEquals(conta.getTipoConta(), dadosDetalhamentoContaRetornada.tipoConta());
        assertEquals(conta.getSaldo(), dadosDetalhamentoContaRetornada.saldo());
        assertEquals(conta.getData(), dadosDetalhamentoContaRetornada.dataCriacao());
        assertEquals(conta.getCliente().getId(), dadosDetalhamentoContaRetornada.clienteId());
        assertEquals(conta.getEstaAtiva(), dadosDetalhamentoContaRetornada.estaAtiva());
    }

    @Test
    public void DeleteLogicConta(){
        Conta conta = getConta();
        Mockito.when(contaRepository.findById(1L)).thenReturn(conta);
        assertDoesNotThrow(() -> contaService.DeleteLogicConta(1L));
    }
}

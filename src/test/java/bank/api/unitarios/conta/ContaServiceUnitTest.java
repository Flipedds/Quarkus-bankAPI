package bank.api.unitarios.conta;

import bank.api.application.cliente.dtos.DadosCadastroCliente;
import bank.api.application.cliente.dtos.DadosEndereco;
import bank.api.application.conta.dtos.DadosCadastroConta;
import bank.api.application.conta.dtos.DadosDetalhamentoConta;
import bank.api.domain.cliente.entities.Cliente;
import bank.api.domain.conta.entities.Conta;
import bank.api.domain.conta.enums.TipoConta;
import bank.api.domain.conta.repositories.IContaRepository;
import bank.api.domain.conta.services.IContaService;
import bank.api.domain.transacao.services.ITransacaoService;
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
    ITransacaoService transacaoService;

    @InjectMock
    IContaRepository contaRepository;

    private static Conta getConta() {
        DadosEndereco endereco = new DadosEndereco(
                "rua teste", "teste", "95849584", "teste", "te", "", 20);

        DadosCadastroCliente dadosCadastroCliente = new DadosCadastroCliente(
                "teste", "59584958495", "feminino", "968695869", "teste@gmail.com", endereco);

        return new Conta( 1L, new Cliente(dadosCadastroCliente), new BigDecimal(0), TipoConta.CORRENTE, LocalDateTime.now(), true);
    }

    @Test
    public void addConta(){
        // arrange
        DadosCadastroConta dadosCadastroConta = new DadosCadastroConta(TipoConta.CORRENTE, 1L);
        Mockito.when(contaRepository.findById(1L)).thenReturn(getConta());

        // act
        Conta contaRetornada = contaService.addConta(dadosCadastroConta);

        // assert
        assertNotNull(contaRetornada);
        assertEquals(1L, contaRetornada.getId());
        assertEquals(TipoConta.CORRENTE, contaRetornada.getTipoConta());
        assertEquals(true, contaRetornada.getEstaAtiva());
        assertEquals(new BigDecimal(100), contaRetornada.getSaldo());
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

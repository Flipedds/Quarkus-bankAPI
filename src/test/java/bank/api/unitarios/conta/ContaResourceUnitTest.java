package bank.api.unitarios.conta;

import bank.api.application.cliente.dtos.DadosCadastroCliente;
import bank.api.application.cliente.dtos.DadosEndereco;
import bank.api.application.conta.dtos.DadosCadastroConta;
import bank.api.application.conta.dtos.DadosDetalhamentoConta;
import bank.api.application.conta.dtos.DadosListagemConta;
import bank.api.application.conta.resources.ContasResource;
import bank.api.application.transacao.dtos.DadosListagemTransacao;
import bank.api.domain.cliente.entities.Cliente;
import bank.api.domain.conta.entities.Conta;
import bank.api.domain.conta.enums.TipoConta;
import bank.api.domain.conta.services.IContaService;
import bank.api.domain.transacao.entities.Transacao;
import bank.api.domain.transacao.enums.TipoTransacao;
import bank.api.domain.transacao.services.ITransacaoService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@QuarkusTest
@TestSecurity(user = "test", roles = {"manager"})
public class ContaResourceUnitTest {
    @Inject
    ContasResource contasResource;

    @InjectMock
    IContaService contaService;

    @InjectMock
    ITransacaoService transacaoService;

    private static Conta getConta() {
        DadosEndereco endereco = new DadosEndereco(
                "rua teste", "teste", "95849584", "teste", "te", "", 20);

        DadosCadastroCliente dadosCadastroCliente = new DadosCadastroCliente(
                "teste", "59584958495", "feminino", "968695869", "teste@gmail.com", endereco);

        return new Conta( 1L, new Cliente(dadosCadastroCliente), new BigDecimal(100), TipoConta.CORRENTE, LocalDateTime.now(), true);
    }

    @Test
    @DisplayName("Criando conta com sucesso !")
    void addConta(){
        DadosCadastroConta dadosCadastroConta = new DadosCadastroConta(TipoConta.CORRENTE, 1L);

        // arrange
        Mockito.when(contaService.addConta(dadosCadastroConta)).thenReturn(getConta());
        // act
        Response response = contasResource.addConta(dadosCadastroConta);
        Conta contaRetornada = (Conta) response.getEntity();

        // assert
        assertNotNull(contaRetornada);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());

        assertEquals(1L, contaRetornada.getId());
        assertEquals(TipoConta.CORRENTE, contaRetornada.getTipoConta());
        assertEquals(true, contaRetornada.getEstaAtiva());
        assertEquals(new BigDecimal(100), contaRetornada.getSaldo());
    }

    @Test
    @DisplayName("buscando contas com sucesso !")
    void findAllContas(){
        // arrange
        DadosListagemConta contaUm = new DadosListagemConta(getConta());
        DadosListagemConta contaDois = new DadosListagemConta(getConta());
        DadosListagemConta contaTres = new DadosListagemConta(getConta());

        List<DadosListagemConta> dadosListagemContas = new ArrayList<>();
        dadosListagemContas.add(contaUm);
        dadosListagemContas.add(contaDois);
        dadosListagemContas.add(contaTres);

        Mockito.when(contaService.findAllContas(0, 10)).thenReturn(dadosListagemContas);

        // act
        Response response = contasResource.recuperaContas(0, 10);

        List<DadosListagemConta> dadosListagemContaRetornada = (List<DadosListagemConta>) response.getEntity();

        // assert
        assertNotNull(dadosListagemContaRetornada);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        for (int i = 0; i < 3; i++) {
            assertEquals(dadosListagemContas.get(i).id(), dadosListagemContaRetornada.get(i).id());
            assertEquals(dadosListagemContas.get(i).tipoConta(), dadosListagemContaRetornada.get(i).tipoConta());
            assertEquals(dadosListagemContas.get(i).clienteId(), dadosListagemContaRetornada.get(i).clienteId());
            assertEquals(dadosListagemContas.get(i).saldo(), dadosListagemContaRetornada.get(i).saldo());
        }
    }

    @Test
    @DisplayName("buscando conta com sucesso !")
    void findConta(){
        // arrange
        Conta conta = getConta();
        Mockito.when(contaService.findConta(1L)).thenReturn(conta);
        // act
        Response response = contasResource.recuperaConta(1L);
        DadosDetalhamentoConta dadosDetalhamentoContaRetornada = (DadosDetalhamentoConta) response.getEntity();

        // assert
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(dadosDetalhamentoContaRetornada);
        assertEquals(conta.getId(), dadosDetalhamentoContaRetornada.id());
        assertEquals(conta.getTipoConta(), dadosDetalhamentoContaRetornada.tipoConta());
        assertEquals(conta.getSaldo(), dadosDetalhamentoContaRetornada.saldo());
        assertEquals(conta.getData(), dadosDetalhamentoContaRetornada.dataCriacao());
        assertEquals(conta.getCliente().getId(), dadosDetalhamentoContaRetornada.clienteId());
        assertEquals(conta.getEstaAtiva(), dadosDetalhamentoContaRetornada.estaAtiva());
    }

    @Test
    @DisplayName("recuperando extrato da conta com sucesso !")
    void getExtrato(){
        // arrange
        Conta conta = getConta();
        Transacao transacao = new Transacao(1L, TipoTransacao.SAQUE, new BigDecimal(50), LocalDateTime.now(), conta, null);
        List<DadosListagemTransacao> dadosListagemTransacoes = new ArrayList<>();
        dadosListagemTransacoes.add(new DadosListagemTransacao(transacao));
        dadosListagemTransacoes.add(new DadosListagemTransacao(transacao));
        dadosListagemTransacoes.add(new DadosListagemTransacao(transacao));

        Mockito.when(transacaoService.getExtrato(1L)).thenReturn(dadosListagemTransacoes);

        // act
        Response response = contasResource.recuperaExtrato(1L);
        List<DadosListagemTransacao> dadosListagemTransacoesRetornada = (List<DadosListagemTransacao>) response.getEntity();

        // assert
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(dadosListagemTransacoesRetornada);

        for (int i = 0; i < 3; i++) {
            assertEquals(dadosListagemTransacoes.get(i).id(), dadosListagemTransacoesRetornada.get(i).id());
            assertEquals(dadosListagemTransacoes.get(i).tipoTransacao(), dadosListagemTransacoesRetornada.get(i).tipoTransacao());
            assertEquals(dadosListagemTransacoes.get(i).valor(), dadosListagemTransacoesRetornada.get(i).valor());
            assertEquals(dadosListagemTransacoes.get(i).data(), dadosListagemTransacoesRetornada.get(i).data());
            assertEquals(dadosListagemTransacoes.get(i).conta(), dadosListagemTransacoesRetornada.get(i).conta());
            assertEquals(dadosListagemTransacoes.get(i).contaDestino(), dadosListagemTransacoesRetornada.get(i).contaDestino());
        }
    }

    @Test
    @DisplayName("Removendo logicamente a conta com sucesso !")
    void DeleteLogicConta(){
        contaService.DeleteLogicConta(1L);
        Mockito.verify(contaService).DeleteLogicConta(1L);
        assertDoesNotThrow(() -> contasResource.desativaConta(1L));
    }
}

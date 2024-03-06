package bank.api.presentation.resources;

import bank.api.presentation.dtos.transacao.DadosNovaTransacao;
import bank.api.domain.services.ITransacaoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import java.net.URI;

@Path("/transacoes")
public class TransacoesResource {
    @Inject
    ITransacaoService transacaoService;

    @POST
    @RolesAllowed("manager")
    public Response newTransacao(DadosNovaTransacao dados){
        var transacao = transacaoService.executarESalvarTransacao(dados);
        URI uri = URI.create("/transacoes/"+transacao.getId());
        return Response.created(uri).entity(transacao).build();
    }

    @GET
    @RolesAllowed("manager")
    public Response recuperaTransacoes(@QueryParam("pagina") int pagina,
                                   @QueryParam("tamanho") int tamanho){
        return Response.status(200).entity(transacaoService.findAllTransacoes(pagina, tamanho)).build();
    }
}

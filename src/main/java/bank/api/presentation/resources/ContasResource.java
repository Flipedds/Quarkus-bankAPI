package bank.api.presentation.resources;

import bank.api.presentation.dtos.conta.DadosCadastroConta;
import bank.api.presentation.dtos.conta.DadosDetalhamentoConta;
import bank.api.domain.services.IContaService;
import bank.api.domain.services.ITransacaoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import java.net.URI;

@Path("/contas")
public class ContasResource {
    @Inject
    IContaService contaService;

    @Inject
    ITransacaoService transacaoService;

    @POST
    @RolesAllowed("manager")
    public Response addConta(DadosCadastroConta dados){
        var conta = contaService.addConta(dados);
        URI uri = URI.create("/contas/"+conta.getId());
        return Response.created(uri).entity(conta).build();
    }

    @GET
    @RolesAllowed("manager")
    public Response recuperaContas(@QueryParam("pagina") int pagina,
                                   @QueryParam("tamanho") int tamanho){
        return Response.status(200).entity(contaService.findAllContas(pagina, tamanho)).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("manager")
    public Response recuperaConta(@PathParam("id") Long id){
        var conta = contaService.findConta(id);
        return Response.status(200).entity(new DadosDetalhamentoConta(conta)).build();
    }

    @GET
    @Path("/extrato/{id}")
    @RolesAllowed("manager")
    public Response recuperaExtrato(@PathParam("id") Long id){
        return Response.status(200).entity(transacaoService.getExtrato(id)).build();
    }


    @DELETE
    @Path("/{id}")
    @RolesAllowed("manager")
    public Response desativaConta(@PathParam("id") Long id){
        contaService.DeleteLogicConta(id);
        return Response.noContent().build();
    }
}

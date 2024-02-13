package bank.api.application;

import bank.api.domain.conta.services.ContaService;
import bank.api.domain.conta.dtos.DadosCadastroConta;
import bank.api.domain.conta.dtos.DadosDetalhamentoConta;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import java.net.URI;

@Path("/contas")
public class ContasResource {
    @Inject
    ContaService contaService;

    @POST
    @RolesAllowed("manager")
    public Response addConta(DadosCadastroConta dados){
        var conta = contaService.addConta(dados);
        URI uri = URI.create("/contas/"+conta.getId());
        return Response.created(uri).entity(conta).build();
    }

    @GET
    @RolesAllowed("manager")
    public Response recuperaContas(){
        return Response.status(200).entity(contaService.findAllContas()).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("manager")
    public Response recuperaConta(@PathParam("id") Long id){
        var conta = contaService.findConta(id);
        return Response.status(200).entity(new DadosDetalhamentoConta(conta)).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("manager")
    public Response desativaConta(@PathParam("id") Long id){
        contaService.DeleteLogicConta(id);
        return Response.noContent().build();
    }
}

package bank.api.application.cliente.resources;

import bank.api.application.cliente.dtos.DadosAtualizarCliente;
import bank.api.application.cliente.dtos.DadosCadastroCliente;
import bank.api.application.cliente.dtos.DadosDetalhamentoCliente;
import bank.api.domain.cliente.services.IClienteService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import java.net.URI;

@Path("/clientes")
public class ClientesResource {

    @Inject
    IClienteService clienteService;

    @GET
    @RolesAllowed("manager")
    public Response recuperaClientes(
            @QueryParam("pagina") int pagina,
            @QueryParam("tamanho") int tamanho,
            @QueryParam("order") String order){
        return Response.status(200).entity(clienteService.findAllClientes(pagina, tamanho, order)).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("manager")
    public Response recuperaCliente(@PathParam("id") Long id){
        var cliente = clienteService.findCliente(id);
        return Response.status(200).entity(new DadosDetalhamentoCliente(cliente)).build();
    }

    @POST
    @RolesAllowed("manager")
    public Response addCliente(DadosCadastroCliente dados){
        var cliente = clienteService.addCliente(dados);
        URI uri = URI.create("/clientes/"+cliente.getId());
        return Response.created(uri).entity(cliente).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("manager")
    public Response removeCliente(@PathParam("id") Long id){
        clienteService.deleteCliente(id);
        return Response.noContent().build();
    }

    @PUT
    @RolesAllowed("manager")
    public Response atualizaCliente(DadosAtualizarCliente dados){
        var cliente = clienteService.putCliente(dados);
        return Response.status(200).entity(new DadosDetalhamentoCliente(cliente)).build();
    }
}
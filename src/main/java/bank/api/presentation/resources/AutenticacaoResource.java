package bank.api.presentation.resources;

import bank.api.domain.services.IKeycloakAcessService;
import bank.api.presentation.dtos.autenticacao.Credenciais;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.*;
import java.io.*;

@Path("/autenticacao")
public class AutenticacaoResource {
    @Inject
    IKeycloakAcessService keycloakAcessService;
    @POST
    public Response getJwtToken(Credenciais credenciais) throws IOException {
        return Response.ok().entity(keycloakAcessService.getJwtToken(credenciais)).build();
    }
}

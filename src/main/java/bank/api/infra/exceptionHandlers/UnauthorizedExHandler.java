package bank.api.infra.exceptionHandlers;

import bank.api.domain.exceptions.ErrorMessage;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class UnauthorizedExHandler implements ExceptionMapper<IOException> {

    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public Response toResponse(IOException exception) {
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(new ErrorMessage(exception.getMessage())).build();
    }
}

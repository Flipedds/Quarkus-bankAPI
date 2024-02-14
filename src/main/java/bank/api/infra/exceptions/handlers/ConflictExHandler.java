package bank.api.infra.exceptions.handlers;

import bank.api.infra.exceptions.ConflictException;
import bank.api.infra.exceptions.ErrorMessage;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ConflictExHandler implements ExceptionMapper<ConflictException> {
    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public Response toResponse(ConflictException exception) {
        return Response.status(Response.Status.CONFLICT)
                .entity(new ErrorMessage(exception.getMessage())).build();
    }
}

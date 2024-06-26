package bank.api.infra.exceptionHandlers;

import bank.api.domain.exceptions.ErrorMessage;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class EntityExHandler implements ExceptionMapper<EntityNotFoundException> {
    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public Response toResponse(EntityNotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new ErrorMessage(exception.getMessage())).build();
    }
}

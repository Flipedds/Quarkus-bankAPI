package bank.api.infra.exceptions.handlers;

import bank.api.infra.exceptions.ErrorMessage;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.hibernate.exception.ConstraintViolationException;

@Provider
public class ConstraintExHandler implements ExceptionMapper<ConstraintViolationException> {

    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public Response toResponse(ConstraintViolationException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorMessage(exception.getMessage())).build();
    }
}

package bank.api.infra.exceptionHandlers;

import bank.api.domain.exceptions.ErrorMessage;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.hibernate.PropertyValueException;

@Provider
public class ValidationExHandler implements ExceptionMapper<PropertyValueException> {
    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public Response toResponse(PropertyValueException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorMessage(exception.getMessage())).build();
    }
}
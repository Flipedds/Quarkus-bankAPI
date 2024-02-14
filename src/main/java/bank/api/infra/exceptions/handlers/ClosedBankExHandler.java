package bank.api.infra.exceptions.handlers;

import bank.api.infra.exceptions.ClosedBankException;
import bank.api.infra.exceptions.ErrorMessage;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ClosedBankExHandler implements ExceptionMapper<ClosedBankException> {
    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public Response toResponse(ClosedBankException exception) {
        return Response.status(422)
                .entity(new ErrorMessage(exception.getMessage())).build();
    }
}

package bank.api.domain.exceptions;

public class ConflictException extends RuntimeException {
    public ConflictException(String mensagem){
        super(mensagem);
    }
}

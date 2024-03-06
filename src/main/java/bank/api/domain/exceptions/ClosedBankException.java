package bank.api.domain.exceptions;

public class ClosedBankException extends RuntimeException {
    public ClosedBankException(String mensagem){
        super(mensagem);
    }
}

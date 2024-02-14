package bank.api.infra.exceptions;

public class ClosedBankException extends RuntimeException {
    public ClosedBankException(String mensagem){
        super(mensagem);
    }
}

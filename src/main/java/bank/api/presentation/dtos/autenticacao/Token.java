package bank.api.presentation.dtos.autenticacao;

public  record Token(String token,Integer expiresIn, String refresh) {}

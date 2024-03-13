package bank.api.domain.services;

import bank.api.presentation.dtos.autenticacao.Credenciais;
import bank.api.presentation.dtos.autenticacao.Token;

import java.io.IOException;

public interface IKeycloakAcessService {
    Token getJwtToken(Credenciais credenciais) throws IOException;
}

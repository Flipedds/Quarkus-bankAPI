# Bank Api
Rest Api com funcionalidades de criar clientes, contas e transações em desenvolvimento
utilizando Quarkus.

# Utilizando o Keycloak para autenticação

    KEYCLOAK DOCKER RUN

    docker run --name keycloak -e KEYCLOAK_ADMIN=admin -e 
    KEYCLOAK_ADMIN_PASSWORD=admin -p 8180:8080 
    quay.io/keycloak/keycloak:17.0.0 start-dev

    URL PARA RECEBER O ACESS TOKEN DEPOIS DE CRIAR O USER E A ROLE NO ADMIN DO KEYCLOAK -> http://localhost:8180/realms/quarkus/protocol/openid-connect/token
    
    Requisição postman para a url
    # POST PASSANDO AUTH -> Basic Auth -> Username e Password
    Padrão -> backend-service e secret
    
    BODY -> x-www-form-urlencoded
    username password e grant_type = password
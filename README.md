# Bank API

<img loading="lazy" src="http://img.shields.io/static/v1?label=JAVA&message=V11&color=GREEN&style=for-the-badge"/>
<img loading="lazy" src="http://img.shields.io/static/v1?label=MAVEN&message=3.9.6&color=orange&style=for-the-badge"/>
<img loading="lazy" src="http://img.shields.io/static/v1?label=QUARKUS&message=3.7.2&color=red&style=for-the-badge"/>
<img loading="lazy" src="http://img.shields.io/static/v1?label=JUNIT&message=V5&color=blue&style=for-the-badge"/>

![Alt text](https://drive.usercontent.google.com/download?id=118W_dQP7qWIbqTBoauQHF-a38mvsw2v-&export=view&authuser=0)

# Descrição
Rest API com funcionalidades de criar clientes, contas e transações em desenvolvimento
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
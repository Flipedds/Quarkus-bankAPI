# Bank API

<img loading="lazy" src="http://img.shields.io/static/v1?label=JAVA&message=V11&color=GREEN&style=for-the-badge"/> <img loading="lazy" src="http://img.shields.io/static/v1?label=MAVEN&message=3.9.6&color=orange&style=for-the-badge"/> <img loading="lazy" src="http://img.shields.io/static/v1?label=QUARKUS&message=3.7.2&color=red&style=for-the-badge"/> <img loading="lazy" src="http://img.shields.io/static/v1?label=JUNIT&message=V5&color=blue&style=for-the-badge"/>

![Imagem de um aparelho com duas setas divergentes em sua tela e uma moeda seguidos do nome do projeto centralizado](https://drive.usercontent.google.com/download?id=118W_dQP7qWIbqTBoauQHF-a38mvsw2v-&export=view&authuser=0)

# Descrição
Rest API com funcionalidades de criar clientes, contas e transações em desenvolvimento
utilizando Quarkus.

# Tecnologias utilizadas
* Java 21
* Maven 3.6.9
* Hibernate e panache
* PostgreSQL JDBC
* Flyway
* Rest Assured
* Junit 5
* Quarkus 3.7.2
* Lombok

# Funcionalidades da API
* criar, deletar, atualizar, e buscar clientes
* criar, desativar, buscar contas, e seus respectivos extratos
* efetuar transações entre contas

# Endpoints
### contas
<img loading="lazy" src="http://img.shields.io/static/v1?label=GET&message=/contas&color=GREEN&style=for-the-badge"/> <br>

<pre>Query params: ? pagina=0 & tamanho=20 <br></pre>
<img loading="lazy" src="http://img.shields.io/static/v1?label=POST&message=/contas&color=blue&style=for-the-badge"/> <br>
<pre>
{ <br>
  "tipoConta": "CORRENTE", <br>
  "clienteId": 1 <br>
} <br>
</pre>
<img loading="lazy" src="http://img.shields.io/static/v1?label=GET&message=/contas/extrato/{id}&color=red&style=for-the-badge"/> <br>
<img loading="lazy" src="http://img.shields.io/static/v1?label=GET&message=/contas/{id}&color=orange&style=for-the-badge"/> <br>
<img loading="lazy" src="http://img.shields.io/static/v1?label=DELETE&message=/contas/{id}&color=orange&style=for-the-badge"/> <br>

### transações
<img loading="lazy" src="http://img.shields.io/static/v1?label=POST&message=/transacoes&color=blue&style=for-the-badge"/> <br>
<pre>{<br>
   "tipoTransacao": "DEPOSITO", <br>
   "valor": 100, <br>
   "idConta": 1, <br>
   "idContaDestino": 2 <br>
}</pre>

<img loading="lazy" src="http://img.shields.io/static/v1?label=GET&message=/transacoes&color=GREEN&style=for-the-badge"/> <br>

<pre>Query params: ? pagina=0 & tamanho=20 <br></pre>

### clientes
<img loading="lazy" src="http://img.shields.io/static/v1?label=GET&message=/clientes&color=GREEN&style=for-the-badge"/> <br>

<pre>Query params: ? pagina=0 & tamanho=20 & order=reversed<br></pre>

<img loading="lazy" src="http://img.shields.io/static/v1?label=POST&message=/clientes&color=blue&style=for-the-badge"/> <br>
<pre>{
  "nome": "pessoa",
  "cpf": "999999999",
  "genero": "feminino",
  "telefone": "958595858",
  "email": "pessoa@gmail.com",
  "endereco": {
    "logradouro": "rua qualquer",
    "bairro": "qualquer",
    "cep": "47205791",
    "cidade": "qualquer",
    "uf": "PE",
    "complemento": "n/a",
    "numero": 20
  }
}</pre>
<img loading="lazy" src="http://img.shields.io/static/v1?label=GET&message=/clientes/{id}&color=orange&style=for-the-badge"/> <br>
<img loading="lazy" src="http://img.shields.io/static/v1?label=DELETE&message=/clientes/{id}&color=orange&style=for-the-badge"/> <br>
<img loading="lazy" src="http://img.shields.io/static/v1?label=PUT&message=/clientes&color=blue&style=for-the-badge"/> <br>
<pre>{
  "id": 0,
  "telefone": "string",
  "email": "string",
  "endereco": {
    "logradouro": "string",
    "bairro": "string",
    "cep": "73729352",
    "cidade": "string",
    "uf": "string",
    "complemento": "string",
    "numero": 0
  }
}</pre>

# 🛠 Configurando e Rodando o Projeto

**Algumas configurações devem ser feitas antes de iniciar a aplicação** <br>

## Utilizando o Keycloak para autenticação/ permissões de acesso

    KEYCLOAK DOCKER RUN

    docker run --name keycloak -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin -p 8180:8080 quay.io/keycloak/keycloak:17.0.0 start-dev

    Acessando keycloak -> localhost:8180
    login com credenciais de adm 

    user -> admin
    senha -> admin

    criação de novo realm com nome quarkus -> importando quarkus-realm.json que se encontra na raiz do projeto
    criação de user com login e senha
    criação de role manager e atribuição ao user criado
    
    Requisição postman para a url
    URL PARA RECEBER O ACESS TOKEN -> http://localhost:8180/realms/quarkus/protocol/openid-connect/token

    # Método POST PASSANDO AUTH -> Basic Auth -> Username e Password
    Padrão -> backend-service e secret
    
    BODY -> x-www-form-urlencoded
    username -> criado anteriormente no realm
    password -> criada anteriormente no realm
    grant_type -> password

    Após isso, receberá um jwt token que deve ser utilizado para requisições na api

## Definição dos dados de acesso ao banco de dados
    Criar um arquivo .env na raiz do projeto com as seguintes variáveis
    URL=jdbc:postgresql://host/banco
    SENHA=sua senha
    USUARIO=seu usuário
    alterar na primeria execução -> quarkus.flyway.migrate-at-start=true  
    !!!! logo após as migrações concluídas retornar para false para evitar conflitos

## Verificar instalações
    Verificar as instalações das versões de Java e Maven compatíveis com o projeto.

## Executando o Projeto
    mvn compile quarkus:dev -> o projeto rodará como padrão na porta 8080, outros serviços como Jenkins podem conflitar.

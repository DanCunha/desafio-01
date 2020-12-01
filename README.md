# API - Votação

## Pre-requisitos

* Java (jdk 11)
* Intellij Community / Eclipse
* Spring Boot
* Maven

## Instalação

### Running Database

Com o docker instalado rodar o comando abaixo.
````
docker run --name postgreSQL -p 5433:5432 --restart=always  -e POSTGRES_PASSWORD=postgres -d postgres
````

### Running API
To run the API is required to have **Maven** and **Java 11** installed.
````
mvn clean spring-boot:run
````

## API REST

Para ver a documentação REST da aplicação, acesse [API-Votacao](https://dcc-api-votacao.herokuapp.com/swagger-ui.html).

## Funcionamento

Após criar um Associado e uma Pauta, podemos criar a sessão para votação na pauta criada

Criar a sessão passando o id da Pauta e o tempo da sessão em minutos [Criar Sessão](https://dcc-api-votacao.herokuapp.com/swagger-ui.html#/sessao-controller/addUsingPOST_2).

Com a sessão criada podemos dar início a votação passando o id da sessão e id do associado [Votação](https://dcc-api-votacao.herokuapp.com/swagger-ui.html#/sessao-controller/votacaoUsingPOST).

O resultado da votação pode ser obtido passando o id da sessão [Resultado Votação](https://dcc-api-votacao.herokuapp.com/swagger-ui.html#/sessao-controller/findByIdUsingGET_2).

## Versionamento

A versão da aplicação usa o formato do [Semantic Version](https://semver.org/) no formato `MAJOR.MINOR.PATCH`, exemplo: 1.0.0.

* `MAJOR` - quando há um grande mudança no projeto.
* `MINOR` - Novas funcionalidades, mudanças na API.
* `PATCH` - Correção de bugs ou melhorias nas funcionalidades já existentes, que não alteram a API.

### Running API Test
To run the API is required to have **Maven** and **Java 11** installed.
````
mvn clean test
````
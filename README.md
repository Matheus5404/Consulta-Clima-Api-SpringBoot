# Consulta de Clima

Aplicação de console desenvolvida em Java e Spring Boot para consultar informações climáticas atuais utilizando a API Open-Meteo.

As consultas realizadas são armazenadas em um banco de dados PostgreSQL com Spring Data JPA.

## Funcionalidades

- Consultar o clima atual de uma cidade
- Exibir temperatura, sensação térmica, umidade e velocidade do vento
- Exibir a condição climática em português
- Salvar consultas no PostgreSQL
- Listar o histórico de consultas
- Buscar consultas por cidade
- Listar as cinco consultas mais recentes
- Buscar por temperatura mínima
- Listar as cidades mais consultadas

## Tecnologias utilizadas

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- Jackson
- HttpClient
- API Open-Meteo

## Menu da aplicação

```text
1 - Consultar clima atual
2 - Listar histórico
3 - Buscar histórico por cidade
4 - Listar últimas 5 consultas
5 - Buscar por temperatura mínima
6 - Listar cidades mais consultadas
0 - Sair
```

## Estrutura do projeto

```text
src/main/java/br/com/matheus/consultaclima
│
├── model
│   ├── ConsultaClima.java
│   ├── DadosClimaAtual.java
│   ├── DadosLocalizacao.java
│   ├── ResultadoClima.java
│   └── ResultadoLocalizacoes.java
│
├── principal
│   └── Principal.java
│
├── repository
│   └── ConsultaClimaRepository.java
│
├── service
│   ├── ConsumoApi.java
│   ├── ConverteDados.java
│   ├── IConverteDados.java
│   └── TradutorCodigoClima.java
│
└── ConsultaClimaApplication.java
```
```

## Autor

Desenvolvido por Matheus Duarte.

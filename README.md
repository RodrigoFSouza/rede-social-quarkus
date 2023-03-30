# Projeto criando uma Rede Social em Quarkus

Projeto feito em Quarkus, simula uma pequena rede social.

## Tecnologias utilizadas
1. Java 17
2. Quarkus
3. Panache
4. RestEasy
5. JsonB
6. Postgresql
7. Lombok

Para saber mais sobre quarkus visiste: https://quarkus.io/ .

## Executar o projeto em modo Desenvolvimento

Para executar o projeto em modo dev, no terminal execute:

```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Para acessar a Dev UI do quarkus http://localhost:8080/q/dev/.

## Swagger

Após executar a aplicação vá até a seguinte url: http://localhost:8080/q/swagger-ui

## Empacotando e executando a aplicação

Use esse comando para empacotar a aplicação:

```shell script
./mvnw package
```

Vá até a pasta `target/quarkus-app/`.


```shell script
./mvnw package -Dquarkus.package.type=rede-social-1.0-jar
```

Execute então a aplicação `java -jar target/*-runner.jar`.

## Criando uma executavel nativo 

Para criar um executável nativo use este comando:

```shell script
./mvnw package -Pnative
```

Ou caso tenha a GraalVm instalada:

```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

Para executar a aplicação nativa faça: `./target/rede-social-1.0-SNAPSHOT-runner`


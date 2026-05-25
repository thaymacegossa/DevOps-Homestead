# DevOps-Homestead

[![SonarCloud Status](https://sonarcloud.io/api/project_badges/measure?project=tthaisvale_DevOps-Homestead&metric=alert_status)](https://sonarcloud.io/dashboard?id=tthaisvale_DevOps-Homestead)

Aplicação web simples de gerenciamento de listas de compras, desenvolvida para fins didáticos e implantação em ambiente Docker.

---

## Estrutura do Projeto

- `src/main/java`: código-fonte Java da aplicação
  - `com.uniesp.DevOps_Homestead.config`: configuração da aplicação
  - `com.uniesp.DevOps_Homestead.domain`: entidades de domínio
  - `com.uniesp.DevOps_Homestead.repository`: interfaces de acesso a dados
  - `com.uniesp.DevOps_Homestead.service`: regras de negócio e serviços
  - `com.uniesp.DevOps_Homestead.web`: controladores web e rotas
- `src/main/resources`: recursos de aplicação
  - `application.yaml`: configurações de aplicação
  - `db/migration`: scripts Flyway para versionamento de banco
  - `static/css`: estilos CSS
  - `templates`: templates Thymeleaf
- `src/test/java`: testes de unidade e integração
- `Dockerfile`: imagem da aplicação para Docker
- `docker-compose.yml` / `compose.yaml`: configuração de containers para aplicação e PostgreSQL
- `pom.xml`: dependências e build Maven
- `sonar-project.properties`: configuração do SonarCloud
- `LICENSE`: licença do projeto

---

## Como o Projeto Funciona

O sistema oferece dois CRUDs completos para listas de compras e tarefas. Ele usa:

- Spring Boot para servidor web
- Thymeleaf para renderização de páginas HTML
- Spring Data JPA para persistência em PostgreSQL
- Flyway para versionamento e migração de banco
- JaCoCo + SonarCloud para cobertura de testes e análise de qualidade

O fluxo básico é:

1. Usuário acessa a interface web no navegador
2. O controller recebe a requisição
3. O serviço aplica regras de negócio
4. O repositório salva/consulta dados no PostgreSQL
5. A view exibe o resultado para o usuário

---

## Tecnologias Utilizadas

- Java 21
- Spring Boot 4
- Thymeleaf
- Spring Data JPA
- Flyway
- PostgreSQL
- Maven
- Docker
- Docker Compose
- JaCoCo
- SonarCloud

---

## Requisitos do Sistema

- Docker Desktop instalado e em execução
- Docker Compose habilitado
- Java 21 instalado (apenas para execução local sem Docker)
- Maven 3.8+ instalado ou uso do Maven Wrapper

---

## Executando o Projeto

### Usando Docker (recomendado)

1. Abra o terminal no diretório raiz do projeto
2. Execute:

    ```bash
    docker-compose up --build -d
    ```

3. Aguarde a criação dos containers e a aplicação iniciar
4. Acesse:

- Aplicação web: `http://localhost:8080`

### Usando Maven Wrapper

Se você preferir executar localmente sem Docker, use o Maven Wrapper:

```bash
./mvnw clean verify
```

No Windows:

```powershell
./mvnw.cmd clean verify
```

---

## Executando Testes Localmente

### Com Maven Wrapper

```bash
./mvnw test
./mvnw -B -V -e clean verify
```

### Com Docker

```bash
docker run --rm -v ${PWD}:/workspace -w /workspace maven:3.9.9-eclipse-temurin-21 mvn -B -V -e clean verify
```

---

## Credenciais do Banco PostgreSQL

- Host: `localhost`
- Porta: `5432`
- Banco: `DevOps-Homestead`
- Usuário: `homestead_user`
- Senha: `homestead_pass_secure`

---

## Comandos Úteis

```bash
docker-compose ps
docker logs homestead-app --tail 200
docker-compose down
docker-compose down -v
```

---

## Layout e Navegação

A aplicação usa Thymeleaf e um layout baseado em templates para manter consistência visual entre as páginas:

- `templates/layout/base.html`: base do layout principal
- `templates/index.html`: página inicial
- `templates/lista-afazeres/form.html` e `list.html`: telas de tarefa
- `templates/lista-compras/form.html` e `list.html`: telas de compras

---

## Como usar

1. Acesse a página principal
2. Clique em `Novo item`
3. Preencha descrição, preço unitário e quantidade
4. Clique em `Inserir`
5. Use as opções de editar ou excluir para atualizar os dados

---

## Solução de Problemas

Se a aplicação não abrir:

1. Verifique o status dos containers:

    ```bash
    docker-compose ps
    ```

2. Verifique os logs da aplicação:

    ```bash
    docker logs homestead-app --tail 200
    ```

3. Reinicie os containers:

    ```bash
    docker-compose down
    docker-compose up --build -d
    ```

4. Para limpar dados e reiniciar do zero:

    ```bash
    docker-compose down -v
    docker-compose up --build -d
    ```

---

## Equipe

- Deborah Alves Correia Vigliar
- Thaís Batista Vale
- Thayani Macegossa Rodrigues

---

## Observações

- A aplicação não exige autenticação para uso local
- O foco é demonstrar um fluxo completo de CRUD com Java, Spring Boot e PostgreSQL

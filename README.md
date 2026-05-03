# DevOps-Homestead

Aplicacao web simples para gerenciamento de lista de compras, com persistencia em PostgreSQL.

## Objetivo

Este projeto entrega um CRUD completo da tabela lista_compras:

- Criar item
- Listar itens
- Editar item
- Excluir item

O campo data_hora e preenchido automaticamente no momento do cadastro.

## Stack

- Java 21
- Spring Boot
- Thymeleaf
- Spring Data JPA
- Flyway
- PostgreSQL
- Docker e Docker Compose

## Forma recomendada de execucao (Docker)

### 1) Pre-requisitos

- Docker Desktop instalado e em execucao
- Docker Compose habilitado

### 2) Subir o sistema

No diretorio raiz do projeto, execute:

docker-compose up --build -d

Esse comando:

- cria a imagem da aplicacao
- sobe o banco PostgreSQL
- sobe a aplicacao web
- aplica as migracoes do banco automaticamente

### 3) Acessar o sistema

Aplicacao web:

http://localhost:8080/lista-compras

Banco PostgreSQL:

- Host: localhost
- Porta: 5432
- Banco: DevOps-Homestead
- Usuario: homestead_user
- Senha: homestead_pass_secure

## Comandos uteis

Ver status dos containers:

docker-compose ps

Ver logs da aplicacao:

docker logs homestead-app --tail 200

Parar o sistema:

docker-compose down

Parar e remover banco (reset completo dos dados):

docker-compose down -v

## Fluxo rapido de uso

1. Acesse a URL do sistema
2. Clique em Novo item
3. Informe descricao, preco unitario e quantidade
4. Clique em Inserir
5. Edite ou exclua itens na lista

## Solucao de problemas

Se a aplicacao nao abrir:

1. Verifique o status com docker-compose ps
2. Veja os logs com docker logs homestead-app --tail 200
3. Reinicie com:

docker-compose down
docker-compose up --build -d

Se quiser resetar todo o banco:

docker-compose down -v
docker-compose up --build -d

## Observacoes

- O projeto esta intencionalmente simples para fins didaticos.
- O acesso esta sem login para facilitar testes do CRUD.

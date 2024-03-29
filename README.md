# Desafio Bitzen - 02/2024
## Autor: Thiago Di Santi 
Linkedin: **`[https://www.linkedin.com/in/thiagodisanti/]`**


## Descrição do desafio
Prazo: 01 dia

**Atividade e Objetivo**

Criar uma API Rest de um cadastro de Músicas, utilizando Spring Boot e Java.

**Contexto:**
OK - Cada Artista possui id, nome, nacionalidade, endereço do site e imagem de perfil
OK - Uma Música possui id, título, duração (minutos e segundos).
OK - Um Álbum possui id, título, ano de lançamento e imagem de capa.
OK - Toda Música está relacionada com um Álbum e possui um número identificando a faixa no referido Álbum.
OK - Todo Álbum possui um Artista

**Requisitos da API:**
OK - Deve ser possível inserir, listar, alterar e excluir artistas, álbuns e músicas.
OK - Todas as listagens devem ser unitárias (pelo id) ou paginadas (todos os registros)
OK - Deve ser possível listar todas as músicas de um determinado Artista com informações sobre o Álbum
OK - Deve ser possível listar todas as músicas de um Álbum ordenadas por número de faixa ou alfabeticamente pelo título
OK - Os dados devem ser persistidos utilizando um banco de dados relacional
OK - Todos os campos são obrigatórios
OK - O ano de lançamento de um Álbum não pode ser uma data posterior a atual
OK - Os números de faixa devem ser maiores que zero
OK - A duração das músicas deve ter minutos e segundos validos
OK - O endereço do site do artista deve ser uma URL válida
OK - As fotos de perfil do artista e de capa do álbum devem ser armazenadas pela API (usando um CDN, armazenamento próprio ou alguma outra alternativa)
OK - Os dados devem permanecer sempre consistentes

**Requisitos técnicos da API:**
OK - Deverão ser criados testes unitários
OK - Publicar o código em repositório público
OK - Deve ser utilizado mapeamento objeto relacional
OK - Utilizar Docker para banco e API

**Requisitos do Front-End:**
● Implementar o front-end para consumir a API em ReactJS
● Utilize Docker para o Front-End
OK - Publicar o código em repositório público



## Descrição geral da solução

A arquitetura da aplicação está dividida em três camadas estruturadas de acordo com sua responsabilidade:
  - Controllers: Componentes responsáveis por gerenciar rotas, receber requisições, invocar lógicas de negócios (camada de serviço) e emitir as respostas;
  - Serviços: Componentes responsáveis pela lógica de negócios, invocar lógicas de persistência (camada de integração), validação de dados, tratamento de exceções;
  - Integração / Persistência: Componentes responsáveis pela persistência em banco de dados.


### Tecnologias utilizadas
- Java 17
- Spring Boot 3.x.x
- Spring Data
- H2 - In Memory Database
- Lombok
- Open API / Swagger
- Docker
- JUnit / Mockito / Spring Boot Tests
- Logback


Escolhi o **banco de dados em memória H2** por ser de fácil instalação e manutenção, propício para execução do desafio. Ao executar a aplicação, as tabelas são criadas automaticamente. A estrutura de tabelas está definida no arquivo **schema.sql** localizado na pasta resources. Importante destacar que após baixar a aplicação, todos os dados são perdidos.

Estão implementados **swagger** e o **console do banco de dados H2**, disponibilizados para facilitar o envio das requisições e consultas no banco diretamente via script SQL se necessário (acesso descrito abaixo).

**Spring Data / JPA** - Para simplificar a camada de persistência.

**AWS S3** - Implementei a gravação e recuperação de imagens em um bucket S3. Porém não estão implementados de ponta a ponta pois não tenho uma conta válida. A implementação pode ser encontrada nas classes ImageService e AWSS3OperationsService.

**Endpoints** - São versionados e padronizados para facilitar a manutenibilidade do sistema.

**Tratamento de Erros** - Foram criadas exceções de aplicação para tratamento específico e todo erro genérico foi tratado de forma global pela classe GlobalExceptionHandler.

**Logs** - Para facilitar a auditoria e troubleshooting, são gerados pela aplicação em formato console.


## Modelos JSON

**Artista**

```sh
{
  "name": "My Band",
  "nationality": "Brazil",
  "website": "http://www.bandOfBrazil.com.br",
  "profileImage": ""
}
```

**Álbum**

```sh
{
  "title": "Album Of The Year",
  "releaseYear": 2024,
  "imageCover": "",
  "artista": {
    "id": 1
  }
}
```

**Música**

```sh
{
  "title": "musica 01",
  "durationSeconds": 23,
  "durationMinutes": 4,
  "trackNumber": 7,
  "album": {
    "id": 1
  }
}
```



## Como executar a aplicação

#### Pela execução da IDE
1. Importar como maven project
2. Executar a classe DesafioBitzenApplication.java (start do spring boot)


#### Pela execução do JAR
1. Com o maven instalado, executar o comando na pasta raiz do projeto:
	- mvn clean package
	
2. Após geração do JAR, executar o comando:
    - java -jar target/desafio-bitzen-0.0.1-SNAPSHOT.jar

#### Pela execução do Docker
1. Com o arquivo ZIP descompactado, executar o comando na pasta raiz do projeto:
	- docker build -t desafiobitzen . --no-cache
	- docker run -p 8080:8080 desafiobitzen

#### Como acessar o swagger
```sh
http://localhost:8080/swagger-ui/index.html#/
```

#### Como acessar a console do H2
```sh
http://localhost:8080/h2/
JDBC URL: jdbc:h2:mem:music
Usuário: admin
Senha: admin
```



## Funcionamento
1. Artista
    - A forma mais simples é acessar a aplicação via swagger e utilizar o **artista-controller**, endpoint POST **/artista/v1/**. Clicar no botão **Try it out**, liberando assim os campos para edição. Após o preenchimento dos campos (conforme modelo acima), clicar no botão **Execute**. O resultado será mostrado abaixo.
    
2. Álbum
    - A forma mais simples é acessar a aplicação via swagger e utilizar o **album-controller**, endpoint POST **/album/v1/**. Clicar no botão **Try it out**, liberando assim os campos para edição. Após o preenchimento dos campos (conforme modelo acima), clicar no botão **Execute**. O resultado será mostrado abaixo.
    
3. Música
    - A forma mais simples é acessar a aplicação via swagger e utilizar o **musica-controller**, endpoint POST **/musica/v1/**. Clicar no botão **Try it out**, liberando assim os campos para edição. Após o preenchimento dos campos (conforme modelo acima), clicar no botão **Execute**. O resultado será mostrado abaixo.

4. Buscas
	- As buscas paginadas e por ID estão implementadas em cada endpoint, sendo assim de fácil utilização
	- Buscas adicionais por IdArtista e IdAlbim são encontradas em **musica-controller**, endpoints **/musica/v1/listAllByIdArtista/{idArtista}/** e **/musica/v1/listAllByIdAlbum/{idAlbum}/**. O campo orderBy deve ser preenchido com os valores: TITLE ou TRACK_NUMBER (ver OrderByEnum).

5. Imagens
    - Foi desenvolvida toda camada de serviço para armazenamento e recuperação das imagens via serviço AWS S3. Não está implementado na API pois não tenho uma conta real.
		


## Contatos:
Qualquer dúvida ou informação adicional, seguem meus contatos:
- Thiago Di Santi 
- Linkedin: **`[https://www.linkedin.com/in/thiagodisanti/]`**
- E-mail: **`tdisanti@gmail.com`**


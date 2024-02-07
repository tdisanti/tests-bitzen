# Desafio Bitzen - 02/2024
## Autor: Thiago Di Santi 
Linkedin: **`[https://www.linkedin.com/in/thiagodisanti/]`**


## Descrição do desafio
Prazo: 01 dia

**Atividade e Objetivo**

Criar uma API Rest de um cadastro de Músicas, utilizando Spring Boot e Java.

**Contexto:**
● Cada Artista possui id, nome, nacionalidade, endereço do site e imagem de perfil
● Uma Música possui id, título, duração (minutos e segundos).
● Um Álbum possui id, título, ano de lançamento e imagem de capa.
● Toda Música está relacionada com um Álbum e possui um número identificando a faixa no referido Álbum.
● Todo Álbum possui um Artista

**Requisitos da API:**
● Deve ser possível inserir, listar, alterar e excluir artistas, álbuns e músicas.
● Todas as listagens devem ser unitárias (pelo id) ou paginadas (todos os registros)
● Deve ser possível listar todas as músicas de um determinado Artista com informações sobre o Álbum
● Deve ser possível listar todas as músicas de um Álbum ordenadas por número de faixa ou alfabeticamente pelo título
● Os dados devem ser persistidos utilizando um banco de dados relacional
● Todos os campos são obrigatórios
● O ano de lançamento de um Álbum não pode ser uma data posterior a atual
● Os números de faixa devem ser maiores que zero
● A duração das músicas deve ter minutos e segundos validos
● O endereço do site do artista deve ser uma URL válida
● As fotos de perfil do artista e de capa do álbum devem ser armazenadas pela API (usando um CDN, armazenamento próprio ou alguma outra alternativa)
● Os dados devem permanecer sempre consistentes

**Requisitos técnicos da API:**
● Deverão ser criados testes unitários
● Publicar o código em repositório público
● Deve ser utilizado mapeamento objeto relacional
● Utilizar Docker para banco e API

**Requisitos do Front-End:**
● Implementar o front-end para consumir a API em ReactJS
● Utilize Docker para o Front-End
● Publicar o código em repositório público




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

**JdbcTemplate** - Apesar do JdbcTemplate ser uma forma mais trabalhosa de implementar, preferi escrever de forma mais verbosa para que a avaliação do código SQL seja melhor visualizada. Foram implementados wildcards na criação das sintaxes sql garantindo assim a segurança neste ponto da aplicação, pois não existe risco de *SQL Injection*.

**Endpoints** - São versionados e padronizados para facilitar a manutenibilidade do sistema.

**Tratamento de Erros** - Foram criadas exceções de aplicação para tratamento específico e todo erro genérico foi tratado de forma global pela classe GlobalExceptionHandler.

**Logs** - Para facilitar a auditoria e troubleshooting, são gerados pela aplicação em formato console.

**Design Patterns** - Utilizando o **Design Pattern do tipo criacional Factory** conseguimos deixar a aplicação pronta para suportar outros tipos de arquivo que venham a ser requeridos. Para isso, foi implementada a classe **ProcessaArquivoServiceFactory** onde a mesma, a partir da extensão do arquivo, entrega o serviço que será utilizado para o processamento do arquivo. A implementação está na classe de serviço **ProcessaArquivoBaseService**. A leitura do arquivo CNAB ficou a cargo da classe **ProcessaArquivoCNABService** e a leitura de um suposto arquivo XPTO seria processado a partir da classe **ProcessaArquivoXPTOService**.

**SOLID** - A solução descrita acima também é vista através do primeiro princípio do **SOLID -> [S]ingle Responsability Principle**, onde uma classe tem apenas um objetivo e uma responsabilidade (processar um tipo de arquivo específico). A classe ProcessaArquivoCNABService processa arquivos do tipo CNAB e a classe ProcessaArquivoXPTOService processa arquivos do tipo XPTO.

O segundo princípio do **SOLID -> [O]pen/Closed Principle** também é utilizado, pois delegando a criação de objetos para uma fábrica pode-se criar instâncias de classes concretas específicas, com base em algum critério (neste caso a extensão do arquivo). Isso permite que sejam adicionados novos tipos de arquivo sem modificar o código existente.

O quarto princípio do **SOLID -> [I]nterface Segregation Principle** também está implementado. A camadas de serviços e integração tem suas respectivas interfaces, e assim as classes chamadoras injetam as interfaces e não as classes concretas. Desta forma desacoplamos essas camadas das implementações concretas. Isso facilita a substituição das implementações por outras caso necessário, sem afetar o código que depende delas.

**Documentação do código** - Deixei comentado boa parte do código, garantindo assim a clareza e a facilidade de compreensão do desafio proposto.


### Definições
Existem alguns tópicos em que fiquei em dúvida e por conta disso fiz algumas definições:

**1. Valor da Transação**
Na descrição do desafio não está especificado o formato do valor da transação, apenas é informado que é um formato decimal porém sem a quantidade de casas decimais. Estou considerando duas.

**2. Conta origem e conta destino**
Na descrição do desafio temos as seguintes mensagens de erro:
- "Conta origem não está no formato correto."
- "Conta destino não está no formato correto."

Porém não foi informado qual seria o formato correto para validação. Será validado apenas a obrigatoriedade das mesmas.

**3. Transações válidas x rejeitadas**
No DoD, descrito no desafio, existe o seguinte tópico:
- "As transações válidas devem ser autorizadas e as inválidas rejeitadas."

Porém a definição não tem um retorno de sucesso parcial. Por este motivo implementei um novo tipo de retorno - partial.

**4. Erros genéricos ou problemas no arquivo**
No retorno da mensagem de erro, estarão todos marcados como linha 1.


## Como executar a aplicação

#### Pela execução da IDE
1. Importar como maven project
2. Executar a classe DesafioApplication.java (start do spring boot)


#### Pela execução do JAR
1. Com o maven instalado, executar o comando na pasta raiz do projeto:
	- mvn clean package
	
2. Após geração do JAR, executar o comando:
    - java -jar target/desafio-0.0.1-SNAPSHOT.jar

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
JDBC URL: jdbc:h2:mem:operations
Usuário: admin
Senha: admin
```


## Extras (Não solicitados)
Para destacar alguns dos aspectos dos critérios de avaliação, implementei algumas features não requeridas no desafio:

**1. Preparando a aplicação para receber outros tipos de arquivo**
    - Como descrito na seção "Descrição geral da solução", a solução já deixa resolvido o problema para processamento de outros tipos de arquivo que venham a ser requeridos pelas áreas de negócio. Uma simulação do processamento de um arquivo do tipo XPTO foi deixada como exemplo de uma possível futura implementação.

**2. Somente Validar**
    - Utilizando uma estratégia adquirida ao longo da minha carreira, aproveitei para deixar o endpoint de envio do arquivo com uma funcionalidade de validação do arquivo. Ao enviar o arquivo, juntamente com o flag **Somente Validar** como **true**, a aplicação apenas valida o arquivo, sem fazer a persistência dos dados. Esta estratégia é bastante útil quando temos arquivos grandes e que por vezes são gerados manualmente pelo usuário. É uma funcionalidade auxiliar para que este usuário possa validar o seu arquivo e corrigí-lo se for o caso antes do envio definitivo.

**3. Retorno de Sucesso Parcial**
	- Como descrito na secão "Descrição geral da solução", para o caso de existirem transações efetivadas e rejeitadas na mesma requisição, criei um tipo novo de retorno chamado **partial**, onde o usuário poderá visualizar o resultado completo da solicitação. O HTTP Status que será utilizado é o OK - Código 200.

**4. Docker**
	- Foi implementado o Docker para que o teste seja executado com mais facilidade pelos avaliadores. Esta também é uma forma de assegurar a escalabilidade da aplicação.

**5. Arquivos para testes**
    - Como massa de dados, na pasta resources/static estão os arquivos que utilizei para testes. Temos os testes provenientes do desafio, validação do arquivo, cabeçalho, transação e rodapé.

**6. Busca completa para transação**
	- O sistema dá possibilidade de busca das transações efetivadas com filtros facilitadores. O endpoint **/transacao/v1** implementa uma busca de transações flexível onde o usuário pode recuperar os registros por meio de filtros como page (número da página), order (campo de ordenação), orderDirection (ASC/DESC - Caso não seja informado será tipo ASC), searchType (Caso seja FULL, não exibirá a paginação e fará um full search na tabela), id (id da transação), tipoTransacao (C - Crédito, D - Débito, T - Transferência).
	
**7. Verificar a saúde da aplicação**
	- Geralmente quando se trabalha em um ambiente cloud, existe a necessidade da aplicação ter um endpoint para validar a saúde da aplicação. Implementei um endpoint simples a partir da classe **HealthController** - endpoint **resourceStatus**, onde algumas informações da aplicação são disponibilizadas ao chamador.
	
**8. Interfaces**
	- As interfaces **IBaseService** e **IBaseRepository** já contém algumas definições de métodos que poderiam ser implementados futuramente.


## Funcionamento
1. Como enviar o arquivo CNAB
    - A forma mais simples é acessar a aplicação via swagger e utilizar o **processa-arquivo-controller**, endpoint **/arquivo/v1/upload**. Clicar no botão **Try it out**, liberando assim os campos para edição. Após o preenchimento dos campos, clicar no botão **Execute**. O resultado será mostrado abaixo.

2. Como consultar as transações efetivadas
    - Uma busca direta pelo ID da transação foi implementada a partir do **transacao-controller**, endpoint **/transacao/v1/{id}**.
	- Como descrito na seção "Extras", temos uma busca flexível com filtros facilitadores. Esta busca será encontrada no **transacao-controller**, endpoint **/transacao/v1**. A busca é paginada por default.
		
```sh
FILTROS
- page: Indica o número da página que se quer buscar.
- order: Indica o nome do campo da entidade Transacao pelo qual se quer ordenar a busca.
- orderDirection: Indica a direção da ordenação (ASC/DESC). Caso não seja informado nada, será utilizado ASC.
- searchType: Caso seja indicado **FULL**, a busca não será mais paginada.
- id: Indica o ID da transação que será retornado. Neste caso apenas 1 transação será mostrada pois o campo ID é a chave primária da tabela.
- tipoTransação: Indica **C** para crédito, **D** para débito e **T** para transação.
```


## Contatos:
Qualquer dúvida ou informação adicional, seguem meus contatos:
- Thiago Di Santi 
- Linkedin: **`[https://www.linkedin.com/in/thiagodisanti/]`**
- E-mail: **`tdisanti@gmail.com`**


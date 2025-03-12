# Back Project Task

O projeto Gerenciador de tarefas tem o intuíto de mostrar o potencial da turma de programação da parceria entre IBM do Brasil e Instituto de eEducação Alicerce.
Com as orientações do professor os alunos desenvolveram para aplicar os conhecimentos adquiridos em sala de aula.
 

## Índice

- [Tecnologias](#tecnologias)
- [Instalação](#instalação)
- [Uso](#uso)
- [Testes](#testes)
- [Requests](#requests)
- [Licença](#licença)
- [Site](#site)

## Tecnologias
### Back-end
- Java, Spring Boot, Docker.
### Banco de Dados
- MySQL, H2.
## Instalação

Passos para instalar e configurar o ambiente de desenvolvimento:

1. Clone o repositório:
   ```bash
   git clone https://github.com/infowap/back-project-task.git
   ```
2. Navegue até o diretório do projeto:
   ```bash
   cd back-project-task
   ```
3. Instale as dependências necessárias:
   ```bash
   ./mvnw install
   ```
4. Configure as variáveis de ambiente:
   ```bash
   export CROSCONFIG=http://localhost:8080
   export spring.profiles.active=local
   ````

## Uso

Instruções sobre como executar o projeto e exemplos de uso:

1. Inicie a aplicação:
   ```bash
   ./mvnw spring-boot:run
   ```
2. Acesse a aplicação no navegador:
   ```
   http://localhost:8080
   ```
3. Acesse a documentação da API:
   ```
    http://localhost:8080/swagger-ui.html
    ```
## Testes
Antes de executar os testes apontar a variável de ambiente para test.
```
spring.profiles.active=test
```
Para executar os testes, utilize o comando:
```bash
./mvnw test
```

## Requests
- <span style="color:green">GET</span> Listar todas as tarefas
````bash
curl --request GET \
  --url http://localhost:8080/api/tarefas \
  --header 'User-Agent: insomnia/10.3.1'
````
- <span style="color:green">GET</span> Buscar uma tarefa por ID
```bash
curl --request GET \
  --url http://localhost:8080/api/tarefas/4 \
  --header 'User-Agent: insomnia/10.3.1'
````
- <span style="color:green">GET</span> Filtrar tarefas por status (PENDENTE, CONCLUIDA, EXECUTANDO)
````bash
curl --request GET \
  --url http://localhost:8080/api/tarefas/status/PENDENTE \
  --header 'User-Agent: insomnia/10.3.1'
````
- <span style="color:yellow">POST</span> Criar uma nova tarefa (PENDENTE, CONCLUIDA, EXECUTANDO)
````bash
```bash
curl --request PUT \
--url http://localhost:8080/api/tarefas/2 \
--header 'Content-Type: application/json' \
--header 'User-Agent: insomnia/10.3.1' \
--data '{
"id": 2,
"descricao": "Viagem ao Litoral 2",
"dataInicio": "2025-02-27",
"dataFim": "2025-02-28",
"status": "PENDENTE"
}'
````
- <span style="color:blue">PUT</span> Atualizar uma tarefa pelo ID (PENDENTE, CONCLUIDA, EXECUTANDO)
````bash
curl --request POST \
  --url http://localhost:8080/api/tarefas \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: insomnia/10.3.1' \
  --data '{"descricao": "Viagem ao Litoral 2266", "dataInicio": "2025-02-27", "dataFim": "2025-02-28", status": "CONCLUIDA" }'
````
- <span style="color:red">DELETE</span> Deletar uma tarefa pelo ID
````bash
curl --request DELETE \
  --url http://localhost:8080/api/tarefas/2 \
  --header 'User-Agent: insomnia/10.3.1'
````



## Licença

Este projeto está licenciado sob a Licença MIT - veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## Site
[Project Task Manager](https://front-project-task.onrender.com/)

# Back Project Task

O projeto Gerenciador de tarefas tem o intuíto de mostrar o potencial da turma de programação da parceria entre IBM do Brasil e Instituto de eEducação Alicerce.
Com as orientações do professor os alunos desenvolveram para aplicar os conhecimentos adquiridos em sala de aula.
 

## Índice

- [Tecnologias](#tecnologias)
- [Instalação](#instalação)
- [Uso](#uso)
- [Testes](#testes)
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
## Licença

Este projeto está licenciado sob a Licença MIT - veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## Site
[Project Task Manager](https://front-project-task.onrender.com/)
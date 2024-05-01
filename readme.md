# Events com Java + Spring Boot + jUnity

API feita com a linguagem **Java** utilizando o framework **Spring Boot** com testes unitários usando **jUnity** e **Mockito**.

<hr>

## &#x1F5A5; Tecnologias Usadas
<img alt='Java Logo' height='60' width='60' src='https://raw.githubusercontent.com/get-icon/geticon/fc0f660daee147afb4a56c64e12bde6486b73e39/icons/java.svg' />&nbsp;
<img alt='Docker Logo' height='60' width='60' src='https://raw.githubusercontent.com/get-icon/geticon/fc0f660daee147afb4a56c64e12bde6486b73e39/icons/docker-icon.svg' />&nbsp;
<img alt='Spring Boot framework Logo' height='60' width='60' src='https://raw.githubusercontent.com/get-icon/geticon/fc0f660daee147afb4a56c64e12bde6486b73e39/icons/spring.svg' />&nbsp;
<img alt='Hibernate Logo' height='60' width='60' src='https://raw.githubusercontent.com/get-icon/geticon/fc0f660daee147afb4a56c64e12bde6486b73e39/icons/hibernate.svg' />&nbsp;
<img alt='Postgresql Logo' height='60' width='60' src='https://raw.githubusercontent.com/get-icon/geticon/fc0f660daee147afb4a56c64e12bde6486b73e39/icons/postgresql.svg' />&nbsp;
<img alt='Mockito Logo' height='60' width='60' src='./readme/mockito.svg' />&nbsp;
<img alt='H2 Logo' height='60' width='60' src='./readme/h2.svg' />&nbsp;
<img alt='jUnity Logo' height='60' width='60' src='./readme/junity.svg' />&nbsp;

## &#x2699; Configurações Iniciais

1. No terminal do seu sistema operacional, digite o comando:
```cmd
docker-compose up -d
```
para subir o container docker da aplicação que inclui o banco de dados do **Postgresql**.

<details>
    <summary><b><i>Dados para conexão com o banco</i></b></summary>

    - Host: localhost
    - Port: 5432
    - Database: eventsdb
        - Username: postgres
        - Password: postgres
</details>

## &#x1F4BF; Como Executar

1. Execute o arquivo *src\main\java\com\vitai\events\EventsApplication.java* para iniciar a aplicação.

2. Abra o navegador na URL: https://localhost:8080/swagger-ui/index.html
> Isso vai abrir a página inicial do Swagger UI, onde é possível verificar cada requisição da aplicação

3. Crie um usuário na rota **/auth/register**. Com a *Role* de **ADMIN** para ter acesso a todas as requisições.

## Endpoints

> Informações específicas de cada rota podem ser encotnradas na URL do Swagger UI citada anteriormente

### User
* **POST**:
    * */auth/register*;
    * */auth/login*;

### Event
* **GET**:
    * */events*;
    * */events/{id}*;
* **POST**:
    * */events*;
    * */events/subscribe*;
* **PUT**:
    * */events/{id}*;
* **DELETE**:
    * */events/{id}*;
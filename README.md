# 🧠 Forum Hub

<img width="100%" src="https://capsule-render.vercel.app/api?type=waving&height=200&color=gradient&fontColor=7700ff"/>

Aplicação Java com Spring Boot, que oferece uma API REST para gerenciamento de usuários, tópicos e respostas em um fórum. O projeto possui autenticação via JWT, controle de acesso por perfis e persistência de dados em banco relacional (MySQL). Conta também com versionamento de banco através do Flyway.

> Desenvolvido como desafio na trilha de Back-end do programa Oracle Next Education (ONE), este projeto tem fins didáticos

<p align="center">
  <a href="https://ibb.co/BH2656mP">
    <img src="https://i.ibb.co/BH2656mP/Badge-Spring.png" alt="Badge-Spring" border="0" />
  </a>
</p>

---

## 🛠 Tecnologias utilizadas

- Java 21
- Spring Framework
- Spring Security
- Maven
- JPA + Hibernate
- MySQL
- JWT
- IntelliJ IDEA

---

## 🚀 Como executar o projeto

### Rodando o projeto

```bash
# Clone o repositório

git clone https://github.com/GuiBSantos/forum-hub.git

cd forum-hub

# Execute com o Maven Wrapper

./mvnw spring-boot:run
```

💡 Ou, se preferir, rode a classe `ForumHubApplication`.

---

## ⚙️ Configuração do `application.properties`

No arquivo `src/main/resources/application.properties`, configure:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/forum_hub
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true

spring.flyway.enabled=true

api.security.token.secret=SUA_CHAVE_SECRETA
```

---

## 📌 Funcionalidades

### 🔑 Autenticação e Autorização

- Login e geração de token JWT.
- Controle de acesso por perfis (ADMIN e USUARIO).
- Middleware de segurança com filtros Spring Security.

### 👥 Usuários

- Cadastro de usuário.
- Login.
- Atualização de dados (email, senha, nickname).
- Soft delete de conta (remoção lógica).
- Apenas o próprio usuário ou um admin pode editar ou excluir contas.

### 📄 Tópicos

- Criação de tópicos.
- Listagem de tópicos (com paginação e filtros por status ou usuário).
- Edição e deleção lógica dos tópicos.

### 💬 Respostas

- Adição de respostas a tópicos.
- Listagem de respostas (por tópico ou por usuário).
- Soft delete das respostas.

---

## 🔗 Principais Endpoints

- POST | `v1/register` | **Cadastrar usuário**

- POST |	`v1/login` | **Autenticação (Gera JWT)**
  
- POST | `v1/topicos`	| **Criar tópico**
  
- GET	| `v1/topicos` |	**Listar  tópicos**
  
- POST |	`v1/topicos/{id}/respostas` |	**Adicionar resposta**
  
- GET |	`v1/usuarios/{id}/topicos` |	**Listar tópicos do usuário**
  
- GET |	`v1/usuarios/{id}/respostas` |	**Listar respostas do usuário**
  
- GET |	`v1/usuarios/{id}/config/perfil` |	**Ver dados do perfil**
  
- PUT |	`v1/usuarios/{id}/config/perfil/email` |	**Atualizar email**
  
- PUT |	`v1/usuarios/{id}/config/perfil/senha` |	**Atualizar senha**
  
- DELETE | `v1/usuarios/{id}/config/perfil` |	**Deletar conta (soft delete)**

#### Não se esqueça de incluir o token JWT no cabeçalho das requisições para que elas sejam autorizadas corretamente

## 🌟 Inspiração

Projeto desenvolvido com base no desafio liteAlura da plataforma **Alura**.

<img width="100%" src="https://capsule-render.vercel.app/api?type=waving&height=200&color=gradient&fontColor=d900ff&section=footer"/>

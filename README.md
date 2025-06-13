Sistema de Gerenciamento de Biblioteca (API REST)
📖 Sobre o Projeto

Esta é uma API RESTful desenvolvida em Java com o ecossistema Spring para gerenciar as operações de uma biblioteca. O projeto permite o cadastro e controle de livros, usuários e empréstimos, aplicando conceitos sólidos de desenvolvimento de APIs e boas práticas de programação.

Foi criado como um projeto de estudo para aprofundar os conhecimentos em Spring Boot, Spring Data JPA e na construção de serviços web.
🛠️ Tecnologias Utilizadas

    Java 17
    Spring Boot: Framework principal para a criação da API.
    Spring Data JPA: Para a persistência de dados e comunicação com o banco.
    H2 Database: Banco de dados em memória para testes e ambiente de desenvolvimento.
    Maven: Gerenciador de dependências do projeto.
    Postman/Insomnia: Para testes e validação dos endpoints.

✨ Funcionalidades

    Gerenciamento de Livros: CRUD completo (Criar, Ler, Atualizar, Deletar) para os livros da biblioteca.
    Gerenciamento de Usuários: CRUD completo para os usuários que utilizam a biblioteca.
    Sistema de Empréstimos: Funcionalidades para registrar o empréstimo de um livro a um usuário e para registrar a devolução.

⚙️ Como Executar

Siga os passos abaixo para rodar o projeto localmente:

    Clone o repositório:
    git clone https://github.com/johnbadtrip/SistemadeGerenciamentodeBiblioteca.git

    Navegue até o diretório do projeto:
    cd SistemadeGerenciamentodeBiblioteca

    Execute o projeto com o Maven:
    ./mvnw spring-boot:run

    A aplicação estará disponível em http://localhost:8080.

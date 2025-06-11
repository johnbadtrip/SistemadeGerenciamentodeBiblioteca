# Simulação de Terminal de Autoatendimento Bancário

## Sobre o Projeto

Este projeto é uma simulação via console de um caixa eletrônico. Ele não se conecta a um banco de dados real, mas simula as operações em memória. O objetivo é demonstrar um design de software robusto e a aplicação correta dos pilares da Programação Orientada a Objetos (Encapsulamento, Herança, Polimorfismo).

O usuário interage com o sistema através do terminal, podendo consultar saldo, realizar saques e depósitos em uma conta pré-definida.

## Funcionalidades

* **Autenticação Simples:** O usuário informa o número da conta para iniciar.
* **Menu de Opções:** Exibe um menu interativo (Consultar Saldo, Depositar, Sacar, Sair).
* **Operações Bancárias:**
    * **Consultar Saldo:** Exibe o saldo atual da conta.
    * **Depositar:** Adiciona um valor ao saldo.
    * **Sacar:** Remove um valor do saldo, com validação de saldo suficiente.
* **Encapsulamento:** O saldo da conta não pode ser modificado diretamente, apenas através dos métodos `sacar()` e `depositar()`.

## Tecnologias Utilizadas

* **Java 17 (Core):**
    * **Foco Principal em POO:**
        * **`ContaBancaria` (Classe Principal):** Encapsula os dados (saldo, número) e comportamentos (sacar, depositar).
        * **`Terminal` (Classe de Interface):** Responsável por interagir com o usuário via `Scanner`.
        * **`ValidacaoService` (Classe de Serviço):** Lógica separada para validar entradas do usuário e regras de negócio.
    * **Controle de Fluxo:** Uso de `switch-case` para o menu e `while` para manter a aplicação rodando.
    * **Tratamento de Exceções:** Para lidar com entradas inválidas do usuário (ex: digitar texto em vez de número).

## Como Executar

1.  Clone o repositório: `git clone https://github.com/johnbadtrip/apibiblioteca`
2.  Navegue até o diretório `src`.
3.  Compile o código: `javac com/terminal/*.java`
4.  Execute a classe principal: `java com.terminal.Main`

# Secure-Code-Audit 🛡️

O **SecureCodeAudit** é um analisador estático (Linter) desenvolvido em **Java** para a linguagem de domínio específico **SecureCode Pro**. Seu objetivo é identificar automaticamente vulnerabilidades e padrões inseguros através da definição e execução de regras de auditoria cruzadas com logs dinâmicos em ambientes DevSecOps.

## 🚀 Funcionalidades e Arquitetura

O projeto foi construído aplicando a teoria de Engenharia de Linguagens e implementa as fases clássicas de um compilador:

* **Análise Léxica (Scanner):** Utiliza o gerador **JFlex** para converter Expressões Regulares em um Autômato Finito Determinístico (AFD). O analisador extrai os tokens válidos da linguagem e descarta comentários e espaços em branco.
* **Análise Sintática (Parser):** Implementa um **Parser Descendente Recursivo** para validar a ordem dos tokens contra a gramática **EBNF** oficial do projeto.
* **Análise Semântica:** Utiliza uma **Tabela de Símbolos** instanciada via `HashSet` para gerenciar o escopo e impedir a declaração de regras duplicadas. O sistema também barra inconsistências lógicas de negócio no momento da compilação (ex: regras de nível `BAIXA` acionando a ação `REJEITAR`).
* **Motor de Inferência:** Realiza a leitura dinâmica de arquivos externos (`log_teste.txt`) e cruza as variáveis de ambiente com a Abstract Syntax Tree (AST) gerada pelas regras. Suporta os operadores lógicos `EQUALS`, `CONTAINS`, `NOT` e `MATCHES`.

## 🛠️ Tecnologias Utilizadas

* **Java 17**
* **JFlex 1.9.1** (Gerador de Analisador Léxico)
* **Maven** (Gerenciamento de dependências)
* **EBNF** (Extended Backus-Naur Form)

## ⚙️ Como Executar

1 - **Clone o repositório:**
```bash
    git clone https://github.com/biut07/Secure-Code-Audit.git
```

2 - **Compile o projeto via Maven:**
Na raiz do projeto, execute para baixar as dependências:
```bash
   mvn clean install
```

3 - **Execute o Motor de Auditoria:**
Rode a classe Principal.java. O sistema fará a compilação em tempo real das regras descritas no código e as testará contra os dados presentes no arquivo log_teste.txt (que deve estar na raiz do projeto).
(Caso altere o arquivo securecode.lex, rode a classe Gerador.java primeiro para atualizar o analisador léxico).

Exemplo de Uso
**Código SecureCode:**
```bash
AUDITORIA SECURE_CODE
DEFINIR
    REGRA REGRA_ACESSO_ADMIN NIVEL CRITICA SE USER_ROLE EQUALS "admin" ACAO REJEITAR ;
EXECUTAR
FIM
```

**Arquivo de Log Simulado (log_teste.txt):**
```bash
    USER_ROLE=admin
```

**Saída no Terminal:**
```bash
[ALERTA] Violacao identificada na regra: REGRA_ACESSO_ADMIN
 -> Nivel: CRITICA | Acao requerida: REJEITAR
```

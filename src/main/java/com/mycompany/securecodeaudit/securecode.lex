package com.mycompany.securecodeaudit;

%%

%class AnalisadorLexico
%type Token
%line
%column
%eofval{
    return null;
%eofval}

// Expressoes Regulares baseadas no EBNF do professor
BRANCO = [\n| |\t|\r]
STRING = \"[^\"]*\"
ID = [A-Z_][A-Z0-9_]*
COMENTARIO_LINHA = "//" [^\r\n]*
COMENTARIO_BLOCO = "/*" [^*]* ("*"+ [^/*] [^*]*)* "*"+ "/"

%%

// Palavras-chave de estrutura
"AUDITORIA"      { return new Token("Palavra Reservada (Inicio)", yytext(), yyline + 1, yycolumn + 1); }
"DEFINIR"        { return new Token("Palavra Reservada (Def)", yytext(), yyline + 1, yycolumn + 1); }
"EXECUTAR"       { return new Token("Palavra Reservada (Exec)", yytext(), yyline + 1, yycolumn + 1); }
"FIM"            { return new Token("Palavra Reservada (Fim)", yytext(), yyline + 1, yycolumn + 1); }
"REGRA"          { return new Token("Palavra Reservada (Regra)", yytext(), yyline + 1, yycolumn + 1); }
"NIVEL"          { return new Token("Palavra Reservada (Nivel)", yytext(), yyline + 1, yycolumn + 1); }
"SE"             { return new Token("Palavra Reservada (Condicao)", yytext(), yyline + 1, yycolumn + 1); }
"ACAO"           { return new Token("Palavra Reservada (Acao)", yytext(), yyline + 1, yycolumn + 1); }
";"              { return new Token("Delimitador", yytext(), yyline + 1, yycolumn + 1); }

// Niveis de Criticidade
"BAIXA" | "MEDIA" | "ALTA" | "CRITICA" { return new Token("Nivel de Criticidade", yytext(), yyline + 1, yycolumn + 1); }

// Operadores Logicos
"AND" | "OR"                           { return new Token("Operador Logico", yytext(), yyline + 1, yycolumn + 1); }

// Variaveis da Linguagem
"TOKEN" | "SCOPE" | "USER_ROLE" | "FILE_TYPE" | "PERMISSION" { return new Token("Variavel de Escopo", yytext(), yyline + 1, yycolumn + 1); }

// Operadores de Comparacao
"CONTAINS" | "EQUALS" | "MATCHES" | "NOT" { return new Token("Operador de Comparacao", yytext(), yyline + 1, yycolumn + 1); }

// Acoes Possiveis
"REJEITAR" | "NOTIFICAR" | "SOLICITAR_MFA" | "LOG_ERROR" { return new Token("Acao de Seguranca", yytext(), yyline + 1, yycolumn + 1); }

// Tipos Dinamicos
{STRING}             { return new Token("Valor String", yytext(), yyline + 1, yycolumn + 1); }
{ID}                 { return new Token("Identificador de Regra", yytext(), yyline + 1, yycolumn + 1); }
{BRANCO}             { /* Ignorar espacos em branco */ }
{COMENTARIO_LINHA}   { /* Ignorar comentarios de uma linha */ }
{COMENTARIO_BLOCO}   { /* Ignorar comentarios de multiplas linhas */ }

// Tratamento de Erro Lexico
. { throw new RuntimeException("Erro Lexico - Caractere invalido na linguagem SecureCode: " + yytext()); }

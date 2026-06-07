package com.mycompany.securecodeaudit;

import java.util.List;

public class Parser {
    private List<Token> tokens;
    private int posicao;
    private Token tokenAtual;
    
    private TabelaSimbolos tabelaSimbolos;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.posicao = 0;
        if (!tokens.isEmpty()) {
            this.tokenAtual = tokens.get(0);
        }
        this.tabelaSimbolos = new TabelaSimbolos();
    }
    
    private void avancar() {
        posicao++;
        if (posicao < tokens.size()) {
            tokenAtual = tokens.get(posicao);
        } else {
            tokenAtual = null;
        }
    }

    private void consumir(String tipoEsperado) {
        if (tokenAtual != null && tokenAtual.getTipo().equals(tipoEsperado)) {
            avancar();
        } else {
            String encontrado = (tokenAtual != null) ? tokenAtual.getTipo() : "Fim do Arquivo";
            int linha = (tokenAtual != null) ? tokenAtual.getLinha() : -1;
            throw new RuntimeException("Erro Sintático [Linha " + linha + "]: Esperava o tipo '" 
                                       + tipoEsperado + "', mas encontrou '" + encontrado + "'");
        }
    }

    public void parseProjeto() {
        consumir("Palavra Reservada (Inicio)"); // AUDITORIA
        consumir("Identificador de Regra");     // <id>
        consumir("Palavra Reservada (Def)");    // DEFINIR
        
        parseRegras();
        
        consumir("Palavra Reservada (Exec)");   // EXECUTAR
        consumir("Palavra Reservada (Fim)");    // FIM
        
        System.out.println("-> Sucesso: Estrutura sintática do projeto validada!");
        
        System.out.println("-> Sucesso: Estrutura sintática do projeto validada!");

        tabelaSimbolos.imprimirTabela();
    }
    

    private void parseRegras() {
        do {
            parseRegraUnitaria();
        } while (tokenAtual != null && tokenAtual.getTipo().equals("Palavra Reservada (Regra)"));
    }

    private void parseRegraUnitaria() {
        consumir("Palavra Reservada (Regra)");
        
        Token tokenNomeRegra = tokenAtual;
        consumir("Identificador de Regra");
        
        tabelaSimbolos.adicionarRegra(tokenNomeRegra.getValor(), tokenNomeRegra.getLinha());
        
        consumir("Palavra Reservada (Nivel)");
        consumir("Nivel de Criticidade");
        consumir("Palavra Reservada (Condicao)");
        
        parseCondicaoSeguranca();
        
        consumir("Palavra Reservada (Acao)");
        consumir("Acao de Seguranca");
        consumir("Delimitador");
    }

    private void parseCondicaoSeguranca() {
        parseTermoSeguranca();
        
        while (tokenAtual != null && tokenAtual.getTipo().equals("Operador Logico")) {
            consumir("Operador Logico");
            parseTermoSeguranca();
        }
    }

    private void parseTermoSeguranca() {
        consumir("Variavel de Escopo");
        consumir("Operador de Comparacao");
        consumir("Valor String");
    }
}

package com.mycompany.securecodeaudit;

import java.util.HashSet;
import java.util.Set;

public class TabelaSimbolos {
    
    private Set<String> regrasDeclaradas;

    public TabelaSimbolos() {
        this.regrasDeclaradas = new HashSet<>();
    }

    public void adicionarRegra(String nomeRegra, int linha) {
        if (regrasDeclaradas.contains(nomeRegra)) {
            throw new RuntimeException("Erro Semântico [Linha " + linha + "]: A regra '" + nomeRegra + "' já foi declarada anteriormente neste escopo!");
        }
        
        regrasDeclaradas.add(nomeRegra);
    }
    
    public void imprimirTabela() {
        System.out.println("--- Tabela de Símbolos (Escopo Atual) ---");
        for (String regra : regrasDeclaradas) {
            System.out.println(" -> Regra registrada: " + regra);
        }
        System.out.println("-----------------------------------------");
    }
}

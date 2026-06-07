package com.mycompany.securecodeaudit;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class Principal {

    public static void main(String[] args) throws IOException {
        
        // Um scrip na linguagem EBNF
        String codigoSecureCode = """
            AUDITORIA SECURE_CODE
            DEFINIR
                REGRA REGRA_ACESSO_ADMIN NIVEL CRITICA SE USER_ROLE EQUALS "admin" ACAO REJEITAR ;
                REGRA REGRA_FOTO_PERFIL NIVEL BAIXA SE FILE_TYPE NOT "png" ACAO REJEITAR ;
            EXECUTAR
            FIM
            """;

        System.out.println("====== FASE 1: ANALISE LEXICA ======");
        AnalisadorLexico lexical = new AnalisadorLexico(new StringReader(codigoSecureCode));
        
        List<Token> listaDeTokens = new ArrayList<>();
        Token token;
        
        while ((token = lexical.yylex()) != null) {
            System.out.println(token.toString());
            listaDeTokens.add(token);
        }

        List<Regra> regras = new ArrayList<>();
        regras.add(new Regra("REGRA_ACESSO_ADMIN", "CRITICA", "USER_ROLE", "EQUALS", "\"admin\"", "REJEITAR"));
        
        regras.add(new Regra("REGRA_FOTO_PERFIL", "BAIXA", "FILE_TYPE", "NOT", "\"png\"", "REJEITAR"));

        System.out.println("\n====== FASE 2: ANALISE SINTATICA ======");
        Parser analisadorSintatico = new Parser(listaDeTokens);
        analisadorSintatico.parseProjeto();
        
        System.out.println("\n====== FASE MOTOR DE INFERENCIA ======");
        
        RegistroTeste registro = new RegistroTeste();
        
        registro.carregarDeArquivo("log_teste.txt");
        
        MotorAuditoria.executar(regras, registro);
        }
    }
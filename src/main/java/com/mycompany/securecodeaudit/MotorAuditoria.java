package com.mycompany.securecodeaudit;

import java.util.List;

public class MotorAuditoria {

    public static void executar(List<Regra> regras, RegistroTeste registro) {
        
        System.out.println("\n[LOG] Iniciando Motor de Inferencia SecureCode...\n");
        
        System.out.println("--- Validacao Semantica ---");
        for (Regra regra : regras) {
            if (regra.getNivel().equals("BAIXA") && regra.getAcao().equals("REJEITAR")) {
                System.out.println("[ERRO SEMANTICO] Inconsistencia na regra '" + regra.getNome() + 
                                   "'. Uma regra de nivel BAIXA nao pode ter acao REJEITAR.");
            }
        }
        System.out.println("\n--- Executando Regras contra o Registro ---");
        for (Regra regra : regras) {
            
            String valorRegistro = registro.get(regra.getChave());

            if (valorRegistro == null || valorRegistro.isEmpty()) {
                continue;
            }

            boolean violacao = false;
            String operador = regra.getOperador();

            switch (operador) {
                case "CONTAINS":
                    violacao = valorRegistro.contains(regra.getValor().replace("\"", ""));
                    break;
                case "EQUALS":
                    violacao = valorRegistro.equalsIgnoreCase(regra.getValor().replace("\"", ""));
                    break;
                case "NOT":
                    violacao = !valorRegistro.equalsIgnoreCase(regra.getValor().replace("\"", ""));
                    break;
                case "MATCHES":
                    violacao = valorRegistro.matches(regra.getValor().replace("\"", "")); 
                    break;
                default:
                    System.out.println("[AVISO] Operador desconhecido: " + operador);
                    break;
            }

            if (violacao) {
                System.out.println("[ALERTA] Violacao identificada na regra: " + regra.getNome());
                System.out.println(" -> Nivel: " + regra.getNivel() + " | Acao requerida: " + regra.getAcao());
            }
        }
    }
}
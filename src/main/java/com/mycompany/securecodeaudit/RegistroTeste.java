package com.mycompany.securecodeaudit;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegistroTeste {

    private Map<String, String> dados = new HashMap<>();

    public void adicionar(String chave, String valor) {
        if (chave != null && !chave.trim().isEmpty()) {
            dados.put(chave, valor);
        } else {
            System.out.println("[AVISO] Tentativa de adicionar um registro sem chave valida.");
        }
    }

    public String get(String chave) {
        return dados.get(chave);
    }

    public void carregarDeArquivo(String caminhoArquivo) {
        System.out.println("\n[LOG] Carregando arquivo de teste: " + caminhoArquivo);
        
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            
            while ((linha = br.readLine()) != null) {
                linha = linha.trim();
                
                if (linha.isEmpty() || linha.startsWith("#") || linha.startsWith("//")) {
                    continue;
                }

                String[] partes = linha.split("=");
                if (partes.length == 2) {
                    String chave = partes[0].trim();
                    String valor = partes[1].trim();
                    
                    adicionar(chave, valor);
                    System.out.println(" -> Dado carregado: " + chave + " = " + valor);
                }
            }
        } catch (IOException e) {
            System.out.println("[ERRO] Nao foi possivel ler o arquivo de teste: " + e.getMessage());
        }
    }
}
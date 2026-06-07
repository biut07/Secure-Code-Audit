package com.mycompany.securecodeaudit;

public class Regra {

    private String nome;
    private String nivel;
    private String chave;
    private String operador;
    private String valor;
    private String acao;

    public Regra(String nome, String nivel, String chave,
                 String operador, String valor, String acao) {

        this.nome = nome;
        this.nivel = nivel;
        this.chave = chave;
        this.operador = operador;
        this.valor = valor;
        this.acao = acao;
    }

    public String getNome() {
        return nome;
    }

    public String getNivel() {
        return nivel;
    }

    public String getChave() {
        return chave;
    }

    public String getOperador() {
        return operador;
    }

    public String getValor() {
        return valor;
    }

    public String getAcao() {
        return acao;
    }
}
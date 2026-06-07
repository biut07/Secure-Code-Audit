package com.mycompany.securecodeaudit;

import jflex.exceptions.SilentExit;

/**
 *
 * @author Bruna
 */
public class Gerador {

    public static void main(String[] args) throws SilentExit {
        
        String file = "C:\\Users\\Bruna\\Desktop\\SecureCodeAudit\\src\\main\\java\\com\\mycompany\\securecodeaudit\\securecode.lex";
        
        jflex.Main.generate(new String[]{file});
        
        System.out.println("Analisador gerado com sucesso!");
    }
    
}

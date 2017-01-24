package br.ufc.sangria;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Utils {
	
	public static String readKey() {
        String key = new String();
        String nome = "C:/Users/Lana/ibm.txt";
  
        try {
          FileReader arq = new FileReader(nome);
          BufferedReader lerArq = new BufferedReader(arq);
          key = lerArq.readLine(); 
          arq.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",
              e.getMessage());
        }
        return key;
    }

}

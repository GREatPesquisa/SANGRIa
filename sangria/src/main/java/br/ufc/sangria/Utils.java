package br.ufc.sangria;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Utils {
	
	public static String readKey() {
        String key = new String();
        String path = "C:/Users/Lana/sangria/ibm.txt";
  
        try {
          FileReader arq = new FileReader(path);
          BufferedReader lerArq = new BufferedReader(arq);
          key = lerArq.readLine(); 
          arq.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n "
            		+ "Mude no código a string path em Utils para o caminho correto da chave.\n",
              e.getMessage());
        }
        return key;
    }

}

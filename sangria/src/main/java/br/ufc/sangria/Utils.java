package br.ufc.sangria;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
	
	// to test
	public static void getAlchemyViaRest(String api_key) {
		try {
			URL url = new URL("https://access.alchemyapi.com/calls/data/GetNews?" + "apikey=" + api_key
					+ "&outputMode=json&start=now-1d&end=now&maxResults="
					+ "100&q.enriched.url.enrichedTitle.relations.relation="
					+ "|action.verb.text=acquire,object.entities.entity.type=Company|" + "&return=enriched.url.title");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

}

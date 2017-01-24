package br.ufc.sangria;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.ibm.watson.developer_cloud.alchemy.v1.AlchemyLanguage;
import com.ibm.watson.developer_cloud.alchemy.v1.model.DocumentSentiment;
import com.ibm.watson.developer_cloud.alchemy.v1.model.Entities;
import com.ibm.watson.developer_cloud.alchemy.v1.model.LanguageSelection;


public class App 
{
	
	private static String api_key="";
	
    public static void main( String[] args )
    {
    	api_key = Utils.readKey();
    	//getViaRest();
    	//getViaSDK();
    }
    
    public static void getViaSDK(){
		
		AlchemyLanguage service = new AlchemyLanguage();
		service.setApiKey(api_key);
		service.setLanguage(LanguageSelection.PORTUGUESE);

	    Map<String, Object> params = new HashMap<String, Object>();
	    params.put(AlchemyLanguage.TEXT, "Lana é uma pessoa muito alegre e gosta de viajar.");

	    // get sentiment
	    DocumentSentiment sentiment = service.getSentiment(params).execute();
	    System.out.println("Sentiment: " + sentiment);
	    service.getSentiment(params);

	    // get entities
	    Entities entities = service.getEntities(params).execute();
	    System.out.println("Entities: " + entities);	
		
	}
    
    
    
	public static void getViaRest(){
		try {
	         URL url = new URL("https://access.alchemyapi.com/calls/data/GetNews?"
	         		+ "apikey="+ api_key +"&outputMode=json&start=now-1d&end=now&maxResults="
	         				+ "100&q.enriched.url.enrichedTitle.relations.relation="
	         				+ "|action.verb.text=acquire,object.entities.entity.type=Company|"
	         				+ "&return=enriched.url.title");
	         HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	         conn.setRequestMethod("GET");
	         conn.setRequestProperty("Accept", "application/json");
	 
	         if (conn.getResponseCode() != 200) {
	             throw new RuntimeException("Failed : HTTP error code : "
	                     + conn.getResponseCode());
	         }
	 
	         BufferedReader br = new BufferedReader(new InputStreamReader(
	             (conn.getInputStream())));
	 
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

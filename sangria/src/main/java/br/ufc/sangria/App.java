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
import com.ibm.watson.developer_cloud.alchemy.v1.model.DocumentEmotion;
import com.ibm.watson.developer_cloud.alchemy.v1.model.DocumentEmotion.Emotion;
import com.ibm.watson.developer_cloud.alchemy.v1.model.DocumentSentiment;
import com.ibm.watson.developer_cloud.alchemy.v1.model.Entities;
import com.ibm.watson.developer_cloud.alchemy.v1.model.Keywords;
import com.ibm.watson.developer_cloud.alchemy.v1.model.LanguageSelection;
import com.ibm.watson.developer_cloud.alchemy.v1.model.Sentiment;
import com.ibm.watson.developer_cloud.language_translation.v2.LanguageTranslation;
import com.ibm.watson.developer_cloud.language_translation.v2.model.Language;
import com.ibm.watson.developer_cloud.language_translation.v2.model.TranslationResult;

@SuppressWarnings("unused")
public class App {

	private static String api_key = "";
	private static final String fileAppMeuOnibus = "C:/Users/Lana/sangria/ReviewsMeuÔnibus.xlsx";
	private static final String fileAppWaze = "C:/Users/Lana/sangria/ReviewsWaze.xlsx";
	private static ParseTable docMeuOnibus;
	public static Review[] reviewsMeuOnibus;

	public static void main(String[] args) {
		api_key = Utils.readKey();
		docMeuOnibus = new ParseTable(fileAppMeuOnibus);
		readReviewsEmotion(docMeuOnibus.reviews);

	}



	public static void readReviewsEmotion(Review[] reviews) {
		
		AlchemyLanguage service = new AlchemyLanguage();
		service.setApiKey(api_key);

		Map<String, Object> paramsPt = new HashMap<String, Object>();
		Map<String, Object> paramsEn = new HashMap<String, Object>();

		for (Review x : reviews) {
			service.setLanguage(LanguageSelection.PORTUGUESE);
			
			x.print();
			
			paramsPt.put(AlchemyLanguage.TEXT, x.commentPt);
			paramsEn.put(AlchemyLanguage.TEXT, x.commentEn);
			
			// get sentiment
			DocumentSentiment docSentiment = service.getSentiment(paramsPt).execute();
			x.sentiment = docSentiment.getSentiment();
			System.out.println("Sentiment: " + docSentiment);
			
			service.setLanguage(LanguageSelection.ENGLISH);
			
			// get emotion
			DocumentEmotion docEmotion = service.getEmotion(paramsEn).execute();
			x.emotion = docEmotion.getEmotion();
			System.out.println("Emotion: " + docEmotion);
			
			// get keywords
			Keywords keywords = service.getKeywords(paramsEn).execute();
			x.keywords = keywords.getKeywords();
			System.out.println("Keywords: " + keywords);

			// get entities
			Entities entities = service.getEntities(paramsEn).execute();
			System.out.println("Entities: " + entities);
		}
	}

	public static void getViaRest() {
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

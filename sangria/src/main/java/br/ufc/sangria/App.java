package br.ufc.sangria;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
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
	private static final String fileAppMeuOnibusResults = "C:/Users/Lana/sangria/ReviewsMeuÔnibusResults.xlsx";
	private static final String fileAppWaze = "C:/Users/Lana/sangria/ReviewsWaze.xlsx";
	private static final String fileAppWazeResults = "C:/Users/Lana/sangria/ReviewsWazeResults.xlsx";
	private static ParseTable docMeuOnibus;
	private static ParseTable docWaze;

	public static void main(String[] args) throws FileNotFoundException, IOException {
		System.out.println("Start SANGRIa...\n\n");
		api_key = Utils.readKey();

		System.out.println("Get reviews from apps...\n");
		docMeuOnibus = new ParseTable(fileAppMeuOnibus);
		// docWaze = new ParseTable(fileAppWaze);

		System.out.println("Create file of results...\n");
		docMeuOnibus.prepareResultsFile(fileAppMeuOnibusResults);
		// docWaze.prepareResultsFile(fileAppWazeResults);

		try {
			System.out.println("Get Emotional Results");
			readReviewsEmotion(docMeuOnibus, fileAppMeuOnibusResults);
			// readReviewsEmotion(docWaze, fileAppWazeResults);
		} catch (Exception e) {
			e.printStackTrace();
		}

		docMeuOnibus.closeTable();
		// docWaze.closeTable();

	}

	public static void readReviewsEmotion(ParseTable doc, String file) throws FileNotFoundException, IOException {
		Review[] reviews = doc.reviews;
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

			doc.writeResult(file, x);
		}
	}

}

package br.ufc.sangria;

import java.util.List;

import com.ibm.watson.developer_cloud.alchemy.v1.model.DocumentEmotion.Emotion;
import com.ibm.watson.developer_cloud.alchemy.v1.model.Keyword;
import com.ibm.watson.developer_cloud.alchemy.v1.model.Sentiment;

public class Review {

	String author;
	String date;
	String commentPt;
	String commentEn;
	int stars;

	// Document Sentiment - Identifies the overall positive or negative
	// sentiment within any document or webpage.
	Sentiment sentiment;

	// Document Emotion - Analyzes the emotions in the entire document or
	// webpage.
	Emotion emotion;
	
	// Keywords - Determines important keywords in the text, ranks them by
	// relevance, and optionally detects the sentiment of each keyword.
	List<Keyword> keywords;
	
	public Review(String author, String date, String commentPt, String commentEn, int stars) {
		this.author = author;
		this.date = date;
		this.commentPt = commentPt;
		this.commentEn = commentEn;
		this.stars = stars;
	}

	public void print() {
		System.out.println("Author: " + this.author);
		System.out.println("Date: " + this.date);
		System.out.println("Comment-pt: " + this.commentPt);
		System.out.println("Comment-en: " + this.commentEn);
		System.out.println("Stars: " + this.stars);
		System.out.println();
	}

}

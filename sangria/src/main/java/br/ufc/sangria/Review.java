package br.ufc.sangria;

public class Review {

	String author;
	String date;
	String comment;
	int stars;


	public Review(String author,String date, String comment, int stars) {
		this.author = author;
		this.date = date;
		this.comment = comment;
		this.stars = stars;
	}

	public void print(){
		System.out.println("Author: "+ this.author);
		System.out.println("Date: "+ this.date);
		System.out.println("Comment: "+ this.comment);
		System.out.println("Stars: "+ this.stars);
		System.out.println();
	}

}

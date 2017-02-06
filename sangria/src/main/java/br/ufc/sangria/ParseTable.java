package br.ufc.sangria;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

// doc https://poi.apache.org/apidocs/index.html

public class ParseTable {

	Review[] reviews;

	XSSFWorkbook workbook;
	XSSFSheet sheet;
	int columnQuantity;
	int row = 0;

	public ParseTable() {

	}

	public ParseTable(String file) {
		try {

			FileInputStream excelFile = new FileInputStream(new File(file));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);

			reviews = new Review[datatypeSheet.getPhysicalNumberOfRows() - 1];

			Iterator<Row> iterator = datatypeSheet.iterator();
			Row currentRow = iterator.next();

			int n = 0;

			while (iterator.hasNext()) {

				currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();

				Cell currentCell = cellIterator.next();
				String author = currentCell.getStringCellValue();

				currentCell = cellIterator.next();
				String date = currentCell.getStringCellValue();

				currentCell = cellIterator.next();
				String commentPt = currentCell.getStringCellValue();

				currentCell = cellIterator.next();
				String commentEn = currentCell.getStringCellValue();

				currentCell = cellIterator.next();
				int stars = (int) currentCell.getNumericCellValue();

				reviews[n] = new Review(author, date, commentPt, commentEn, stars);

				n++;

			}

			workbook.close();
			excelFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void prepareResultsFile(String file) throws FileNotFoundException, IOException {
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("Results");

		// column title
		String[] bookData = { "author", "date", "commentPt", "commentEn", "stars", "emotion Anger", "emotion Disgust",
				"emotion Fear", "emotion Joy", "emotion Sadness", "sentiment Score", "sentiment Type",
				"sentiment Mixed", "keywords size", "keywords" };
		columnQuantity = bookData.length;

		int rowCount = 0;
		int columnCount = 0;
		Row row = sheet.createRow(rowCount);
		Cell cell = row.createCell(columnCount);

		for (String aBook : bookData) {
			cell.setCellValue((String) aBook);
			columnCount++;
			cell = row.createCell(columnCount);
		}

		try (FileOutputStream outputStream = new FileOutputStream(file)) {
			workbook.write(outputStream);
		}

		this.row = this.row + 1;
	}

	public void closeTable() throws IOException {
		this.workbook.close();
	}

	public void writeResult(String file, Review review) throws FileNotFoundException, IOException {
		Row row = sheet.createRow(this.row);
		Cell cell;

		int c = 0;
		cell = row.createCell(c);
		cell.setCellValue(review.author);
		c++;

		cell = row.createCell(c);
		cell.setCellValue(review.date);
		c++;

		cell = row.createCell(c);
		cell.setCellValue(review.commentPt);
		c++;

		cell = row.createCell(c);
		cell.setCellValue(review.commentEn);
		c++;

		cell = row.createCell(c);
		cell.setCellValue(review.stars);
		c++;

		cell = row.createCell(c);
		cell.setCellValue(review.emotion.getAnger());
		c++;

		cell = row.createCell(c);
		cell.setCellValue(review.emotion.getDisgust());
		c++;

		cell = row.createCell(c);
		cell.setCellValue(review.emotion.getFear());
		c++;

		cell = row.createCell(c);
		cell.setCellValue(review.emotion.getJoy());
		c++;

		cell = row.createCell(c);
		cell.setCellValue(review.emotion.getSadness());
		c++;

		cell = row.createCell(c);
		double score;
		if (review.sentiment.getType().toString().equals("NEUTRAL"))
			score = 0;
		else
			score = review.sentiment.getScore();
		cell.setCellValue(score);
		c++;

		cell = row.createCell(c);
		cell.setCellValue(review.sentiment.getType().toString());
		c++;

		cell = row.createCell(c);
		cell.setCellValue(review.sentiment.getMixed());
		c++;

		cell = row.createCell(c);
		cell.setCellValue(review.keywords.size());
		c++;

		cell = row.createCell(c);
		String keywords = "";
		for (int i = 0; i < review.keywords.size(); i++)
			keywords = keywords + review.keywords.get(i).getText() + "; " + review.keywords.get(i).getRelevance()
					+ "; ";
		cell.setCellValue(keywords);
		c++;

		// reviews[n].print();
		try (FileOutputStream outputStream = new FileOutputStream(file)) {
			workbook.write(outputStream);
		}

		this.row = this.row + 1;
	}

	public void writeResults(String file) throws FileNotFoundException, IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Java Books");

		// column title
		String[] bookData = { "author", "date", "commentPt", "commentEn", "stars", "emotion Anger", "emotion Disgust",
				"emotion Fear", "emotion Joy", "emotion Sadness", "sentiment Score", "sentiment Type",
				"sentiment Mixed", "keywords size", "keywords" };

		int rowCount = 0;
		int columnCount = 0;
		Row row = sheet.createRow(rowCount);
		Cell cell = row.createCell(columnCount);

		for (String aBook : bookData) {
			cell.setCellValue((String) aBook);
			columnCount++;
			cell = row.createCell(columnCount);
		}

		int sizeSheet = reviews.length;
		int c = 0;

		for (int r = 0; r < sizeSheet; r++) {

			cell = row.createCell(c);
			cell.setCellValue(reviews[r].author);
			c++;

			cell = row.createCell(c);
			cell.setCellValue(reviews[r].date);
			c++;

			cell = row.createCell(c);
			cell.setCellValue(reviews[r].commentPt);
			c++;

			cell = row.createCell(c);
			cell.setCellValue(reviews[r].commentEn);
			c++;

			cell = row.createCell(c);
			cell.setCellValue(reviews[r].stars);
			c++;

			cell = row.createCell(c);
			cell.setCellValue(reviews[r].emotion.getAnger());
			c++;

			cell = row.createCell(c);
			cell.setCellValue(reviews[r].emotion.getDisgust());
			c++;

			cell = row.createCell(c);
			cell.setCellValue(reviews[r].emotion.getFear());
			c++;

			cell = row.createCell(c);
			cell.setCellValue(reviews[r].emotion.getJoy());
			c++;

			cell = row.createCell(c);
			cell.setCellValue(reviews[r].emotion.getSadness());
			c++;

			cell = row.createCell(c);
			cell.setCellValue(reviews[r].sentiment.getScore());
			c++;

			cell = row.createCell(c);
			cell.setCellValue(reviews[r].sentiment.getType().toString());
			c++;

			cell = row.createCell(c);
			cell.setCellValue(reviews[r].sentiment.getMixed());
			c++;

			cell = row.createCell(c);
			cell.setCellValue(reviews[r].keywords.size());
			c++;

			cell = row.createCell(c);
			String keywords = "";
			for (int i = 0; i < reviews[r].keywords.size(); i++)
				keywords = keywords + reviews[r].keywords.get(i).getText() + "; "
						+ reviews[r].keywords.get(i).getRelevance() + "; ";
			c++;

			cell = row.createCell(c);
			cell.setCellValue(keywords);

			// reviews[n].print();

		}

		try (FileOutputStream outputStream = new FileOutputStream(file)) {
			workbook.write(outputStream);
		}
		workbook.close();
	}

}
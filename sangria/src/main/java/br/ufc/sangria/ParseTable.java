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
				// System.out.println(author);

				currentCell = cellIterator.next();
				String date = currentCell.getStringCellValue();
				// System.out.println(date);

				currentCell = cellIterator.next();
				String commentPt = currentCell.getStringCellValue();
				// System.out.println(commentPt);

				currentCell = cellIterator.next();
				String commentEn = currentCell.getStringCellValue();
				// System.out.println(comment);

				currentCell = cellIterator.next();
				int stars = (int) currentCell.getNumericCellValue();

				reviews[n] = new Review(author, date, commentPt, commentEn, stars);
				// reviews[n].print();

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
		for (int r = 0; r < sizeSheet; r++) {
			for (int c = 0; c < bookData.length; c++) {
				cell = row.createCell(c);
				cell.setCellValue(reviews[r].author);

				cell = row.createCell(c);
				cell.setCellValue(reviews[r].date);

				cell = row.createCell(c);
				cell.setCellValue(reviews[r].commentPt);

				cell = row.createCell(c);
				cell.setCellValue(reviews[r].commentEn);

				cell = row.createCell(c);
				cell.setCellValue(reviews[r].stars);

				cell = row.createCell(c);
				cell.setCellValue(reviews[r].emotion.getAnger());

				cell = row.createCell(c);
				cell.setCellValue(reviews[r].emotion.getDisgust());

				cell = row.createCell(c);
				cell.setCellValue(reviews[r].emotion.getFear());

				cell = row.createCell(c);
				cell.setCellValue(reviews[r].emotion.getJoy());

				cell = row.createCell(c);
				cell.setCellValue(reviews[r].emotion.getSadness());

				cell = row.createCell(c);
				cell.setCellValue(reviews[r].sentiment.getScore());

				cell = row.createCell(c);
				cell.setCellValue(reviews[r].sentiment.getType().toString());

				cell = row.createCell(c);
				cell.setCellValue(reviews[r].sentiment.getMixed());

				cell = row.createCell(c);
				cell.setCellValue(reviews[r].keywords.size());

				cell = row.createCell(c);

				String keywords = "";
				for (int i = 0; i < reviews[r].keywords.size(); i++)
					keywords = keywords + reviews[r].keywords.get(i).getText() + "; "
							+ reviews[r].keywords.get(i).getRelevance() + "; ";
				
				cell = row.createCell(c);
				cell.setCellValue(keywords);

				// reviews[n].print();
			}
		}

		/*
		 * if (aBook instanceof String) { cell.setCellValue((String) field); }
		 * else if (field instanceof Integer) { cell.setCellValue((Integer)
		 * field); }
		 */

		try (FileOutputStream outputStream = new FileOutputStream(file)) {
			workbook.write(outputStream);
		}
		workbook.close();
	}

	public void writeResults2(String file) {
		try {
			FileInputStream excelFile = new FileInputStream(new File(file));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);

			reviews = new Review[datatypeSheet.getPhysicalNumberOfRows() - 1];

			Iterator<Row> iterator = datatypeSheet.iterator();
			Row currentRow = iterator.next();

			int n = 0;

			Iterator<Cell> cellIterator = currentRow.iterator();

			Cell currentCell = cellIterator.next();
			currentCell.setCellValue("author");

			currentCell = cellIterator.next();
			currentCell.setCellValue("date");

			currentCell = cellIterator.next();
			currentCell.setCellValue("commentPt");

			currentCell = cellIterator.next();
			currentCell.setCellValue("commentEn");

			currentCell = cellIterator.next();
			currentCell.setCellValue("stars");

			currentCell = cellIterator.next();
			currentCell.setCellValue("emotion Anger");

			currentCell = cellIterator.next();
			currentCell.setCellValue("emotion Disgust");

			currentCell = cellIterator.next();
			currentCell.setCellValue("emotion Fear");

			currentCell = cellIterator.next();
			currentCell.setCellValue("emotion Joy");

			currentCell = cellIterator.next();
			currentCell.setCellValue("emotion Sadness");

			currentCell = cellIterator.next();
			currentCell.setCellValue("sentiment Score");

			currentCell = cellIterator.next();
			currentCell.setCellValue("sentiment Type");

			currentCell = cellIterator.next();
			currentCell.setCellValue("sentiment Mixed");

			currentCell = cellIterator.next();
			currentCell.setCellValue("keywords size");

			while (iterator.hasNext()) {

				cellIterator = currentRow.iterator();

				currentCell = cellIterator.next();
				currentCell.setCellValue(reviews[n].author);

				currentCell = cellIterator.next();
				currentCell.setCellValue(reviews[n].date);

				currentCell = cellIterator.next();
				currentCell.setCellValue(reviews[n].commentPt);

				currentCell = cellIterator.next();
				currentCell.setCellValue(reviews[n].commentEn);

				currentCell = cellIterator.next();
				currentCell.setCellValue(reviews[n].stars);

				currentCell = cellIterator.next();
				currentCell.setCellValue(reviews[n].emotion.getAnger());

				currentCell = cellIterator.next();
				currentCell.setCellValue(reviews[n].emotion.getDisgust());

				currentCell = cellIterator.next();
				currentCell.setCellValue(reviews[n].emotion.getFear());

				currentCell = cellIterator.next();
				currentCell.setCellValue(reviews[n].emotion.getJoy());

				currentCell = cellIterator.next();
				currentCell.setCellValue(reviews[n].emotion.getSadness());

				currentCell = cellIterator.next();
				currentCell.setCellValue(reviews[n].sentiment.getScore());

				currentCell = cellIterator.next();
				currentCell.setCellValue(reviews[n].sentiment.getType().toString());

				currentCell = cellIterator.next();
				currentCell.setCellValue(reviews[n].sentiment.getMixed());

				currentCell = cellIterator.next();
				currentCell.setCellValue(reviews[n].keywords.size());

				currentCell = cellIterator.next();

				for (int i = 0; i < reviews[n].keywords.size(); i++)
					currentCell.setCellValue(
							reviews[n].keywords.get(i).getText() + "; " + reviews[n].keywords.get(i).getRelevance());

				// reviews[n].print();

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

}
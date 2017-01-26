package br.ufc.sangria;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

// doc https://poi.apache.org/apidocs/index.html

public class ParseTable{
	
	Review[] reviews;

	public ParseTable(String file) {

		try {

			FileInputStream excelFile = new FileInputStream(new File(file));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);

			reviews = new Review[datatypeSheet.getPhysicalNumberOfRows()-1];

			Iterator<Row> iterator = datatypeSheet.iterator();           
			Row currentRow = iterator.next();

			int n = 0;

			while (iterator.hasNext()) {

				currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();

				Cell currentCell = cellIterator.next();
				String author = currentCell.getStringCellValue();
				//System.out.println(author);
				
				currentCell = cellIterator.next();
				String date = currentCell.getStringCellValue();
				//System.out.println(date);

				currentCell = cellIterator.next();
				String commentPt = currentCell.getStringCellValue();
				//System.out.println(commentPt);
				
				currentCell = cellIterator.next();
				String commentEn = currentCell.getStringCellValue();
				//System.out.println(comment);

				currentCell = cellIterator.next();
				int stars = (int) currentCell.getNumericCellValue();

				reviews[n] = new Review(author, date, commentPt, commentEn, stars);
				//reviews[n].print();
				
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
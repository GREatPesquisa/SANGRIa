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

	public ParseTable(String file, Review[] reviews) {

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

				currentCell = cellIterator.next();
				String date = currentCell.getStringCellValue();

				currentCell = cellIterator.next();
				String comment = currentCell.getStringCellValue();

				currentCell = cellIterator.next();
				int stars = (int) currentCell.getNumericCellValue();

				reviews[n] = new Review(author, date, comment, stars);
				reviews[n].print();
				
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
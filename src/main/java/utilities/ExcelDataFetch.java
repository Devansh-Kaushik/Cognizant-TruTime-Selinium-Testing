package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDataFetch{
	
	private static Row excel() throws FileNotFoundException, IOException
	{
		FileInputStream fs = new FileInputStream("src//main//resources//Data.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fs);
		XSSFSheet sheet = workbook.getSheetAt(0);
		Row row = sheet.getRow(1);
		return row;
	}
	
	public static String getBaseUrl() throws FileNotFoundException, IOException
	{
		Cell cell=excel().getCell(0);
		return cell.getStringCellValue();
		
	}
	
	public static String getBrowser() throws FileNotFoundException, IOException
	{
		Cell cell=excel().getCell(1);
		return cell.getStringCellValue();
		
	}
	
	public static String getCognizantEmail() throws FileNotFoundException, IOException
	{
		Cell cell=excel().getCell(2);
		return cell.getStringCellValue();
		
	}
	
	public static String getCognizantPassword() throws FileNotFoundException, IOException
	{
		Cell cell=excel().getCell(3);
		return cell.getStringCellValue();
		
	}
}

package com.project.MainProject;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import utilities.ExtentReport;



public class TrueTime {
	public static ExtentReports report=ExtentReport.getReportInstance();
	public static ExtentTest logger;
	
	private static Integer MonthMap(String month)
	{
		Map<String, Integer> monthMap = new HashMap<String, Integer>();
		monthMap.put("January", Calendar.JANUARY);
		monthMap.put("February", Calendar.FEBRUARY);
		monthMap.put("March", Calendar.MARCH);
		monthMap.put("April", Calendar.APRIL);
		monthMap.put("May", Calendar.MAY);
		monthMap.put("June", Calendar.JUNE);
		monthMap.put("July", Calendar.JULY);
		monthMap.put("August", Calendar.AUGUST);
		monthMap.put("September", Calendar.SEPTEMBER);
		monthMap.put("October", Calendar.OCTOBER);
		monthMap.put("November", Calendar.NOVEMBER);
		monthMap.put("December", Calendar.DECEMBER);
		return monthMap.get(month);
	}
	
	
	 
	public static void trutime_details(WebDriver driver) throws Exception
	{
		logger = report.createTest("TruTime All Information");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(10));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		//System.out.print(driver.getTitle());
	    wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));
		driver.switchTo().frame("appFrame");
		//Thread.sleep(5000);
		//System.out.print(driver.getWindowHandle());
		WebElement Month = (WebElement) driver.findElement(By.xpath("//*[@id=\"datepicker\"]/div/div/div"));
		logger.log(Status.INFO, "We fetched current month present on webpage");
		
		js.executeScript("arguments[0].style.border = '3px solid red';", Month);
		//System.out.println(Month.getText());
		String[] monthYear=Month.getText().split(" ");
		
		// Calendar Setup 
		Calendar cal= Calendar.getInstance();
		Date today = cal.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMMM");
        
 
        
        System.out.println("--------------------- TrueTime Current Month & Year ----------------");
        System.out.println();
        System.out.println("System Calendar Month and Year: "+cal.get(Calendar.MONTH)+" "+ cal.get(Calendar.YEAR));
		if(cal.get(Calendar.MONTH)==MonthMap(monthYear[0]) && cal.get(Calendar.YEAR)==Integer.parseInt(monthYear[1]))
		{
			System.out.println("TrueTime Current Month and Year: "+ Month.getText()+" matches with the Calendar");
			logger.log(Status.INFO, "TrueTime Current Month and Year: \"+ Month.getText()+\" matches with the Calendar");
		}
		else
		{
			System.out.println("TrueTime Current Month and Year: "+ Month.getText()+" does not matches with the Calendar");
			logger.log(Status.INFO, "TrueTime Current Month and Year: "+ Month.getText()+" does not matches with the Calendar");
		}
		System.out.println();
		
		
		
		System.out.println("--------------------- TrueTime backdated TopUp Check ----------------");
		WebElement backdated_TopUp=driver.findElement(By.className("topupavailablefrom"));
		logger.log(Status.INFO, "We fetched TrueTime backdated TopUp showing on webpage");
		
		js.executeScript("arguments[0].style.border = '3px solid red';", backdated_TopUp);
		String[] backdated_date=backdated_TopUp.findElement(By.tagName("p")).getText().split(" ");
		System.out.println();
		int total=Integer.parseInt(backdated_date[9])-cal.get(Calendar.DATE);
		//System.out.println(total);
		if((Integer.parseInt(backdated_date[9]) - cal.get(Calendar.DAY_OF_MONTH))==15 || 
				(cal.get(Calendar.DATE)-Integer.parseInt(backdated_date[9]))==15)
		{
			System.out.println("Backdated TopUp date is 15 days older than Current date");
			logger.log(Status.INFO, "Backdated TopUp date is 15 days older than Current date");
		}
		else
		{
			System.out.println("Backdated TopUp date is not 15 days older than Current date");
			logger.log(Status.INFO, "Backdated TopUp date is not 15 days older than Current date");
		}
		System.out.println();
		
		
		
	    System.out.println("--------------------- TrueTime Month & Year Average ----------------");
        WebElement yearavg=driver.findElement(By.className("yrAvg"));
        js.executeScript("arguments[0].style.border = '3px solid red';",yearavg);
        WebElement monthavg=driver.findElement(By.className("monthAvg"));
        logger.log(Status.INFO, "We fetched TrueTime Month & Year Average ");
        
        js.executeScript("arguments[0].style.border = '3px solid red';", monthavg);
        String mAvg=monthavg.findElement(By.id("A2")).getText();
        String yAvg=yearavg.findElement(By.id("A3")).getText();
        System.out.println("Month Average: "+ mAvg);
        System.out.println("Year Average: " + yAvg);
        logger.log(Status.INFO, " So we fetched Month Average: "+ mAvg + " "+ "Year Average: " + yAvg);
        System.out.println();
	     

        System.out.println("--------------------- TrueTime WeekDays ----------------");
        System.out.println();
        wait.withTimeout(Duration.ofMinutes(2));
        List<WebElement> elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@id='mCSB_2_container']/div[3]/div[position() >= 2 and position() <= 8]/div[1]")));
        logger.log(Status.INFO, " So we fetched TrueTime WeekDays as list");
        for (WebElement element : elements) {
            String[] dateText = element.getText().split(" ");
            String month=dateText[2];
            try {
                // Parse the date string to a Date object
            	
            	if (Integer.parseInt(dateText[1])!=cal.get(Calendar.DAY_OF_MONTH)) {
            		js.executeScript("arguments[0].style.border = '3px solid red';", element);
            	    System.out.println(element.getText());
            	    logger.log(Status.INFO, "Weekdays dates are fetched and greater todays date ");
            	}
            	else if(Integer.parseInt(dateText[1])==cal.get(Calendar.DAY_OF_MONTH))
            	{
            		js.executeScript("arguments[0].style.border = '5px solid green';", element);
            	    System.out.println("Today's date matches: " + element.getText());
            	    logger.log(Status.INFO, "Todays date is matched");
            	}
            } catch (Exception e) {
                // Handle parsing error
                System.out.println("Error date: " + element.getText());
                logger.log(Status.INFO, "Today's date matches: " + element.getText() + "had error");
            }
        }
        
        DriverSetup.takeScreenshot("TruTime");
        logger.log(Status.INFO, "THE END");
        //driver.quit();
		
	}
	public static void TruTime_process(WebDriver driver) throws Exception
	{
		trutime_details(driver);
	}
}

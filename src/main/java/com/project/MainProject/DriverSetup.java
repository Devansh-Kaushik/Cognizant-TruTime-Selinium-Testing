package com.project.MainProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.io.FileHandler;

import com.aventstack.extentreports.ExtentTest;


import utilities.ExcelDataFetch;
public class DriverSetup {
	private static WebDriver driver;
	public static ExtentTest logger;
	
	public static WebDriver ChromeDriver()
	{
		System.setProperty("webdriver.chrome.driver", "Drivers\\chromedriver.exe");
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		return driver;
	}
	public static WebDriver EdgeDriver()
	{
		System.setProperty("webdriver.msedge.driver", "Drivers\\msedgedriver.exe");
		driver=new EdgeDriver();
		driver.manage().window().maximize();
		return driver;
	}
	public static WebDriver getWebDriver() throws FileNotFoundException, IOException
	{
		String browser_choice=ExcelDataFetch.getBrowser();
		//System.out.print(browser_choice+" "+baseUrl);
		if(browser_choice.equalsIgnoreCase("Chrome"))
		{
			return ChromeDriver();
		}
		else if(browser_choice.equalsIgnoreCase("Edge"))
		{
			return EdgeDriver();
		}
		return null;
	}
	
	public static void takeScreenshot(String name) {
		//DateUtils.getTimeStamp() 
		File destFile = new File("Screenshots/" + name + ".png");
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		if (!destFile.exists()) {
		    try {
		        FileHandler.copy(screenshot, destFile);
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}

		if (logger != null) {
		    try {
		        logger.addScreenCaptureFromPath(destFile.getAbsolutePath());
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
    }
}

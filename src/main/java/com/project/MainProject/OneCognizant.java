package com.project.MainProject;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import utilities.ExtentReport;

public class OneCognizant {
	public static ExtentReports report=ExtentReport.getReportInstance();
	public static ExtentTest logger;
	
    public static void Onecognizant(WebDriver driver) throws Exception {
    	logger = report.createTest("Search TruTime and navigate by clicking");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(5));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));

        WebElement searchbar = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("oneC_searchAutoComplete")));
        searchbar.sendKeys("TruTime");
        logger.log(Status.INFO, "TruTime is entered in searchbox");
        
        wait.withTimeout(Duration.ofMinutes(10));
        js.executeScript("arguments[0].style.border = '3px solid red';", searchbar);
        DriverSetup.takeScreenshot("One_Cognizant_1");
        searchbar.sendKeys(Keys.ENTER);
        logger.log(Status.INFO, "After entering TruTime we clicked Enter");
        
        By truTimeLocator = By.xpath("//div[@id='newSearchAppsLST']//div[contains(@aria-label, 'TruTime')]");
        wait.until(ExpectedConditions.presenceOfElementLocated(truTimeLocator));
        WebElement truTimeElement = wait.until(ExpectedConditions.elementToBeClickable(truTimeLocator));
        js.executeScript("arguments[0].style.border = '3px solid red';", truTimeElement);
        DriverSetup.takeScreenshot("One_Cognizant_2");
        truTimeElement.click();
        logger.log(Status.INFO, "TruTime is found and clicked");
        report.flush();
        
    }
    
    public static void One_Cognizant_process(WebDriver driver ) throws Exception
    {
    	Onecognizant(driver);
    	TrueTime.TruTime_process(driver);
    }
}

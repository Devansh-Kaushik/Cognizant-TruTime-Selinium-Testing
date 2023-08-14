package com.project.MainProject;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import utilities.ExtentReport;

public class Profile_data {
    public static String current_page = "";
    public static ExtentReports report=ExtentReport.getReportInstance();
	public static ExtentTest logger;
	
    public static String handle(WebDriver driver) {
    	current_page = driver.getWindowHandle();
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(current_page)) {
                return windowHandle;
            }
        }
        return null;
    }

    public static void profiledata(WebDriver driver) throws Exception {
    	
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        logger = report.createTest("Getting User Information");
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));
        
        try {
	        WebElement account_icon = driver.findElement(By.xpath("//*[@id=\"O365_HeaderRightRegion\"]"));
	        js.executeScript("arguments[0].style.border = '3px solid red';", account_icon);
	        account_icon.click();
	        logger.log(Status.INFO, "Account icon box is clicked");
        }
        catch(Exception e) {}

        wait.withTimeout(Duration.ofSeconds(30));

        System.out.println("--------------------- Cognizant Associate Details ------------------");
        System.out.println();

        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"mectrl_currentAccount_primary\"]")));
        System.out.println("UserName: " + username.getText());
        logger.log(Status.INFO, "Username is fetched sucessfully");

        WebElement email = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"mectrl_currentAccount_secondary\"]")));
        System.out.println("Email-ID: " + email.getText());
        logger.log(Status.INFO, "Email-ID is fetched sucessfully");

        wait.withTimeout(Duration.ofSeconds(10));

        logger = report.createTest("Navigation to One Cognizant Portal");
        
        Actions act = new Actions(driver);
        wait.withTimeout(Duration.ofSeconds(10));
        WebElement onecognizant_Section = driver.findElement(By.xpath("//*[@id=\"spPageCanvasContent\"]/div/div/div/div/div/div[2]"));
        onecognizant_Section.click();
        logger.log(Status.INFO, "OneCognizant section container class got clicked");
        
        By locator = By.xpath("//div[text()='OneCognizant']");
        WebDriverWait delay = new WebDriverWait(driver, Duration.ofSeconds(20));
        delay.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        
        WebElement onecognizant = onecognizant_Section.findElement(locator);
        act.moveToElement(onecognizant).build().perform();

        js.executeScript("arguments[0].style.border = '3px solid red';", onecognizant);
        DriverSetup.takeScreenshot("Be_Cognizant");
        onecognizant.click();
        logger.log(Status.INFO, "OneCognizant button is clicked");
        driver.switchTo().window(handle(driver));
        report.flush();
        wait.withTimeout(Duration.ofSeconds(40));
        
        
        
    }
    public static void Profile_data_process(WebDriver driver ) throws Exception
    {
    	profiledata(driver);
    	OneCognizant.One_Cognizant_process(driver);
    }
}

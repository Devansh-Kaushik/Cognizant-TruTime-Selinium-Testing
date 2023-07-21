package com.project.MainProject;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import utilities.ExcelDataFetch;
import utilities.ExtentReport;

public class LogIn {
    private static WebDriver driver;
    private static String baseUrl;
    public static ExtentReports report = ExtentReport.getReportInstance();
    public static ExtentTest logger;

    public static void restartLoginProcess() {
        boolean isSuccessful = false;

        while (!isSuccessful) {
            try {
                driver = DriverSetup.getWebDriver();
                loginAccount();
                isSuccessful = true;
            } catch (NoSuchSessionException e) {
                System.err.println("Invalid session ID. Restarting the process...");
                if (driver != null) {
                    driver.quit();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (driver != null) {
                    driver.quit();
                }
            }
        }
    }

    public static void loginAccount() throws Exception {
        baseUrl = ExcelDataFetch.getBaseUrl();
        driver.get(baseUrl);
        Thread.sleep(2500);
        System.out.println("--------------------- Cognizant Log-In----------------");
        System.out.println();

        if (driver.getCurrentUrl().contains("form")) {
            signIn(driver);
        }

        if (driver.getCurrentUrl().contains("/login")) {
            enterAppCode(driver);
        }

        Profile_data.Profile_data_process(driver);
    }

    public static void signIn(WebDriver driver) throws Exception {
        Thread.sleep(1000);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        logger = report.createTest("Entering Email while log-in");

        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(40));
        WebElement loginHeaderElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginHeader")));

        if (loginHeaderElement.isDisplayed() && loginHeaderElement.getText().equalsIgnoreCase("sign in")) {
            WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("i0116")));
            username.click();
            logger.log(Status.INFO, "Email Box is clicked");

            js.executeScript("arguments[0].style.border = '3px solid red';", username);
            username.sendKeys(Keys.chord(Keys.CONTROL, "a"), ExcelDataFetch.getCognizantEmail());
            logger.log(Status.INFO, "Email is Entered successfully");

            WebElement button1 = wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9")));
            js.executeScript("arguments[0].style.border = '3px solid red';", button1);
            DriverSetup.takeScreenshot("Email_Entered");
            button1.click();
            logger.log(Status.INFO, "NextButton is Clicked successfully");

            Thread.sleep(2000);
            logger = report.createTest("Entering Password while log-in");
            WebElement password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("i0118")));
            password.click();
            logger.log(Status.INFO, "Password box is clicked successfully");

            js.executeScript("arguments[0].style.border = '3px solid red';", password);
            password.sendKeys(Keys.chord(Keys.CONTROL, "a"), ExcelDataFetch.getCognizantPassword());
            logger.log(Status.INFO, "Password is entered successfully");

            WebElement button2 = wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9")));
            js.executeScript("arguments[0].style.border = '3px solid red';", button2);
            DriverSetup.takeScreenshot("Password_Entered");
            button2.click();
            logger.log(Status.INFO, "Submit is clicked successfully");
            enterAppCode(driver);
        } else {
            driver.quit();
            report.flush();
            logger.log(Status.INFO, "Email page functionality had a problem");
            restartLoginProcess();
        }
    }

    public static void enterAppCode(WebDriver driver) throws Exception {
        Thread.sleep(2000);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        logger = report.createTest("Is the AppCode Approved");

        WebElement approveSignInElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idDiv_SAOTCAS_Title")));

        if (approveSignInElement.isDisplayed() && approveSignInElement.getText().equals("Approve sign in request")) {
            String five_min_script = "alert('Waiting for 3 minutes');";
            ((JavascriptExecutor) driver).executeScript(five_min_script);
            Thread.sleep(1500);
            driver.switchTo().alert().accept();
            driver.switchTo().defaultContent();
            System.out.println("Waiting for 3 Minutes to enter the code through the phone app");
            Thread.sleep(15000);
            System.out.println("3 Minutes completed ");
            five_min_script = "alert('3 minutes are over. I hope the code was entered correctly');";
            ((JavascriptExecutor) driver).executeScript(five_min_script);
            driver.switchTo().alert().accept();
            driver.switchTo().defaultContent();
            logger.log(Status.INFO, "5 Min Completed to enter the code");

            DriverSetup.takeScreenshot("Approve_sign-in");
            System.out.println();
            Thread.sleep(1000);

            if (driver.getCurrentUrl().contains("SAS/ProcessAuth")) {
                String yes_btn_script = "alert('Click on Yes Button');";
                ((JavascriptExecutor) driver).executeScript(yes_btn_script);
                Thread.sleep(1500);
                driver.switchTo().alert().accept();
                driver.switchTo().defaultContent();
                WebElement yesBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("idSIButton9")));
                js.executeScript("arguments[0].style.border = '3px solid red';", yesBtn);
                DriverSetup.takeScreenshot("Stay_on_page");
                yesBtn.click();
                logger.log(Status.INFO, "Login into your account was successful");
            }
            report.flush();
        } else {
            driver.quit();
            report.flush();
            logger.log(Status.INFO, "While entering the code, a problem occurred");
            restartLoginProcess();
        }
    }

    public static void main(String[] args) {
        restartLoginProcess();
    }
}
 
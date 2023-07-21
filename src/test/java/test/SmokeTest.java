package test;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.project.MainProject.DriverSetup;
import com.project.MainProject.LogIn;

import utilities.ExcelDataFetch;

public class SmokeTest {
	private static WebDriver driver;
	
	@BeforeMethod
    public void setUp() {
        // Set up the WebDriver
		try {
			driver=DriverSetup.getWebDriver();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	@AfterMethod
    public void tearDown() {
        // Close the WebDriver after each test
        driver.quit();
        //report.flush();
    }
	
	@Test
	public void LoginTest() {
		String baseUrl;
		try {
			baseUrl = ExcelDataFetch.getBaseUrl();
			driver.get(baseUrl);
			Thread.sleep(2500);
			LogIn.signIn(driver);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			try {
				LogIn.enterAppCode(driver);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
}
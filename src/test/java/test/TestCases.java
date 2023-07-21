package test;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.project.MainProject.DriverSetup;
import com.project.MainProject.LogIn;
import com.project.MainProject.OneCognizant;
import com.project.MainProject.Profile_data;
import com.project.MainProject.TrueTime;

import utilities.ExcelDataFetch;
import utilities.ExtentReport;


public class TestCases {
	private static WebDriver driver;
	private static String secondUrl="https://onecognizant.cognizant.com/Home?GlobalAppId=926";
	public ExtentReports report=ExtentReport.getReportInstance();
	public ExtentTest logger;
	public static  String currentWindowHandle;
	
	@BeforeMethod
	@Parameters("browser")
    public void setUp(String browser) {
		if (browser.equalsIgnoreCase("chrome")) {
            driver = DriverSetup.ChromeDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            driver = DriverSetup.EdgeDriver();
        } else {
            // Handle unsupported browser value or default case
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }
	
	@AfterSuite
    public void tearDown() {
        // Close the WebDriver after each test
        //driver.quit();
        //report.flush();
    }
	
	@Test
	public void Profile_info_test()
	{
		try {
			String baseUrl = ExcelDataFetch.getBaseUrl();
			driver.get(baseUrl);
			Thread.sleep(1150);
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
		try {
			//Thread.sleep(1000);
			System.out.print("wait_over");
			Profile_data.profiledata(driver);
			Thread.sleep(1500);
			OneCognizant.Onecognizant(driver);
			Thread.sleep(1500);
			TrueTime.TruTime_process(driver);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
package utilities;

import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;


public class ExtentReport {
	public static ExtentReports report;

	public static ExtentReports getReportInstance() {

		if (report == null) {
			Date date = new Date();
	        String Date_format= date.toString().replaceAll(":", "_").replaceAll(" ", "_");
	        
			String reportName = Date_format + ".html";
			ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(
					System.getProperty("user.dir") + "/reports/" + reportName); //  /reports/
			report = new ExtentReports();
			report.attachReporter(htmlReporter);

			report.setSystemInfo("OS", "Windows 11");
			report.setSystemInfo("Environment", "UAT");
			report.setSystemInfo("Browser", "Chrome");

			htmlReporter.config().setDocumentTitle("Automation Results");
			htmlReporter.config().setReportName("TruTime_WebSite_Report");
			htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		}

		return report;
	}
}



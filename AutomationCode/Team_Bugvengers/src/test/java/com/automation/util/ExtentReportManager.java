package com.automation.util;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.automation.util.Constants;

public class ExtentReportManager {
    
	//private static ExtentReports extent;
		 static ExtentReports extent;
	    public static String screenshotFolderPath;
	    
	  //  static ExtentReports extent;
	    final static String filePath = "Extent.html";
	    static Map<Integer, ExtentTest> extentTestMap = new HashMap();
	    
	    public static synchronized ExtentReports createInstance() {
	    	if(extent==null) {
	    		Date d = new Date();
	    		String folderName=d.toString().replace(":", "_").replace(" ", "_");
	    		String reportPath=System.getProperty("user.dir")+Constants.REPORT_PATH;
	    		// directory of the report folder
	    		new File(reportPath+folderName+"/screenshots").mkdirs();
	    		reportPath=reportPath+folderName+"/";
	    		screenshotFolderPath=reportPath+"screenshots/";   		
	    		String finalPath=reportPath+filePath;
	    		    	
	        ExtentSparkReporter spark=new ExtentSparkReporter(finalPath);
	        spark.config().setTheme(Theme.STANDARD);
	        spark.config().setDocumentTitle("Reports");
	        spark.config().setEncoding("utf-8");
	        spark.config().setReportName("Reports - Automation Testing");
	        spark.config().setCSS("css-string");
	        spark.config().enableTimeline(true);
	        spark.config().setJS("js-string");
	        extent = new ExtentReports();
	        extent.attachReporter(spark);
	        }
	        
	        return extent;
	    }
	    
	    
	    public static synchronized ExtentTest getTest() {
	        return (ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId()));
	    }

	    public static synchronized ExtentTest startTest(String testName, String desc) {
	        ExtentTest test = createInstance().createTest(testName, desc);
	        extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
	        return test;
	    }
}
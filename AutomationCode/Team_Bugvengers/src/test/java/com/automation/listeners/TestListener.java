package com.automation.listeners;



import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.automation.util.ExtentReportManager;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import org.testng.annotations.Test;







public class TestListener  implements ITestListener {
	
	private WebDriver driver = null;
	private ExtentTest test=null;
	private String platformName=null;
	private String deviceName=null;
	private String testType=null;
	private String passScreenshot=null;
	
	
	public void onTestFailure(ITestResult result) {
		
		ITestContext context = result.getTestContext();
		 test =  (ExtentTest) context.getAttribute("Test");
		 testType=(String)context.getAttribute("TestType");
		 
		 if (testType.equalsIgnoreCase("Web"))
		 { driver = (WebDriver) context.getAttribute("WebDriver");
	} else if(testType.equalsIgnoreCase("Mobile"))
	{
		driver = (WebDriver) context.getAttribute("AppuimDriver");
	}
		
		if (testType.equalsIgnoreCase("Web") || testType.equalsIgnoreCase("Mobile") )
		{
		if(result.getThrowable() != null) {
			  StringWriter sw = new StringWriter();
			  PrintWriter pw = new PrintWriter(sw);
			  result.getThrowable().printStackTrace(pw);
					
					Map <String, String> params = new HashMap<String, String>();
					params = result.getTestContext().getCurrentXmlTest().getAllParameters();
					
					
					String imagePath= "FailedTestsScreenshots"+File.separator+params.get("platformName") 
					+ "_"  + File.separator 
					+ result.getTestClass().getRealClass().getSimpleName() + File.separator + result.getName() + ".png";
					
					
					TakesScreenshot scr=(TakesScreenshot)driver;
					File sourceFile=scr.getScreenshotAs(OutputType.FILE);
					
					byte[] encoded = null;
					try {
						encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(sourceFile));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					String destfilePath=ExtentReportManager.screenshotFolderPath+"//"+imagePath;
					File destFile=new File(destfilePath);
					try {
						FileUtils.copyFile(sourceFile,destFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					
					
					try {
						ExtentReportManager.getTest().fail("Test Failed", 
								MediaEntityBuilder.createScreenCaptureFromPath(destfilePath).build());
						ExtentReportManager.getTest().fail("Test Failed", 
								MediaEntityBuilder.createScreenCaptureFromBase64String(new String(encoded, StandardCharsets.US_ASCII)).build());
					} catch (IOException e) {
						e.printStackTrace();
					}
		}	
		}
					ExtentReportManager.getTest().fail(result.getThrowable());
					ExtentReportManager.createInstance().flush();
						
					
		

	}

	@Override
	public void onTestStart(ITestResult result) {
		
		ITestContext context = result.getTestContext();
		 platformName=(String) context.getAttribute("Platform");
		 testType=(String)context.getAttribute("TestType");
		 
			if (testType.equalsIgnoreCase("Mobile")  )
			{
				deviceName=(String) context.getAttribute("Device");
		 ExtentReportManager.startTest(result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class).testName(),result.getMethod().getDescription())
		.assignCategory(testType+"_"+platformName + "_" + deviceName)
		.assignAuthor("Bugvengers QA Team");}
			else if (testType.equalsIgnoreCase("Web"))
			{
				 ExtentReportManager.startTest(result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class).testName(),result.getMethod().getDescription())
					.assignCategory(testType+"_"+platformName)
					.assignAuthor("Bugvengers QA Team");}
				
			 else if(testType.equalsIgnoreCase("API"))
			{
				 ExtentReportManager.startTest(result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class).testName(),result.getMethod().getDescription())
					.assignCategory(testType)
					.assignAuthor("Bugvengers QA Team");}
			
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		
		
		ITestContext context = result.getTestContext();
		 driver = (WebDriver) context.getAttribute("AppuimDriver");
		 
		 test =  (ExtentTest) context.getAttribute("Test");
		 testType=(String)context.getAttribute("TestType");
		 if (testType.equalsIgnoreCase("Web"))
		 { driver = (WebDriver) context.getAttribute("WebDriver");
	} else if(testType.equalsIgnoreCase("Mobile"))
	{
		driver = (WebDriver) context.getAttribute("AppuimDriver");
	}

		 
		 if (testType.equalsIgnoreCase("Web") || testType.equalsIgnoreCase("Mobile"))
			{
			 passScreenshot=(String)context.getAttribute("passScreenshotFlag");
			 	if(passScreenshot.equalsIgnoreCase("Yes"))
			 	{
					Map <String, String> params = new HashMap<String, String>();
					params = result.getTestContext().getCurrentXmlTest().getAllParameters();
					
					
					String imagePath= "PassedTestsScreenshots"+File.separator+params.get("platformName") 
					+ "_"  + File.separator 
					+ result.getTestClass().getRealClass().getSimpleName() + File.separator + result.getName() + ".png";
					
					
					
					TakesScreenshot scr=(TakesScreenshot)driver;
					File sourceFile=scr.getScreenshotAs(OutputType.FILE);
					
					byte[] encoded = null;
					try {
						encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(sourceFile));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					try {
						ExtentReportManager.getTest().pass("Test passed", 
								MediaEntityBuilder.createScreenCaptureFromBase64String(new String(encoded, StandardCharsets.US_ASCII)).build());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			 	}} else {
			 		ExtentReportManager.getTest().pass("Test passed");
			 	}
					ExtentReportManager.createInstance().flush();
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		ITestContext context = result.getTestContext();
		 
		 test =  (ExtentTest) context.getAttribute("Test");
		 if (testType.equalsIgnoreCase("Web"))
		 { driver = (WebDriver) context.getAttribute("WebDriver");
	} else if(testType.equalsIgnoreCase("Mobile"))
	{
		driver = (WebDriver) context.getAttribute("AppuimDriver");
	}
		 
		 
		 if (testType.equalsIgnoreCase("Web") || testType.equalsIgnoreCase("Mobile") )
			{
		 
		
		if(result.getThrowable() != null) {
			  StringWriter sw = new StringWriter();
			  PrintWriter pw = new PrintWriter(sw);
			  result.getThrowable().printStackTrace(pw);
					
					Map <String, String> params = new HashMap<String, String>();
					params = result.getTestContext().getCurrentXmlTest().getAllParameters();
					
					
					String imagePath= "SkippedTestsScreenshots"+File.separator+params.get("platformName") 
					+ "_"  + File.separator 
					+ result.getTestClass().getRealClass().getSimpleName() + File.separator + result.getName() + ".png";
					
					
					
					TakesScreenshot scr=(TakesScreenshot)driver;
					File sourceFile=scr.getScreenshotAs(OutputType.FILE);
					
					byte[] encoded = null;
					try {
						encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(sourceFile));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					String destfilePath=ExtentReportManager.screenshotFolderPath+"//"+imagePath;
					File destFile=new File(destfilePath);
					try {
						FileUtils.copyFile(sourceFile,destFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					
					
					try {
						ExtentReportManager.getTest().skip("Test Skipped", 
								MediaEntityBuilder.createScreenCaptureFromPath(destfilePath).build());
						ExtentReportManager.getTest().skip("Test Skipped", 
								MediaEntityBuilder.createScreenCaptureFromBase64String(new String(encoded, StandardCharsets.US_ASCII)).build());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
					ExtentReportManager.getTest().skip(result.getThrowable());
					ExtentReportManager.createInstance().flush();
						
					
		}
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {	
	}

	@Override
	public void onStart(ITestContext context) {
		
	}

	@Override
	public void onFinish(ITestContext context) {
		ExtentReportManager.createInstance().flush();	
	}

}

package com.automation.pages.web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.automation.base.BaseWebPage;
import com.automation.functions.GenericWebFunctions;
import com.automation.util.ExtentReportManager;
import com.aventstack.extentreports.Status;


public class HomePage extends BaseWebPage {
	
	
	@FindBy(xpath="//input[@id='twotabsearchtextbox']")
	protected WebElement searchBox;
	
	@FindBy(xpath="//input[@id='nav-search-submit-button']")
	protected WebElement searchBtn;
	
	
	public void enterDataInSearchBox()
	{
		GenericWebFunctions.waitUntil(searchBox,"visible");
		ExtentReportManager.getTest().log(Status.INFO, "Entering Text in Serch box");
		GenericWebFunctions.sendKeysByElement(searchBox, "phones");
		
	}
	public void clickSearchBtn()
	{
		GenericWebFunctions.waitUntil(searchBtn,"visible");
		ExtentReportManager.getTest().log(Status.INFO, "Clicking on search button");
		GenericWebFunctions.clickByWebElement(searchBtn);
		
	}
	
	
	
	

}

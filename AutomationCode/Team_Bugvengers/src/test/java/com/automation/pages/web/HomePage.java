package com.automation.pages.web;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.automation.base.BaseWebPage;
import com.automation.functions.GenericWebFunctions;
import com.automation.util.Constants;
import com.automation.util.ExtentReportManager;
import com.automation.util.WebLocators;
import com.aventstack.extentreports.Status;


public class HomePage extends BaseWebPage {
	
	
	@FindBy(xpath=WebLocators.FROMCITY_INPUT)
	protected WebElement fromCityTxtBox;
	
	@FindBy(xpath=WebLocators.FROMCITY_TEXT)
	protected WebElement fromCityInputBox;
	
	@FindBy(xpath=WebLocators.FIRSTOPTION_LIST)
	protected WebElement firstOption;
	
	@FindBy(xpath=WebLocators.CITY_COMBOBOX)
	protected WebElement cityCombobox;
	
	@FindBy(xpath=WebLocators.TOCITY_INPUT)
	protected WebElement toCityTxtBox;
	
	@FindBy(xpath=WebLocators.TOCITY_TEXT)
	protected WebElement toCityInputBox;
	
	@FindBy(xpath=WebLocators.SEARCH_BUTTON)
	protected WebElement searchBtn;
	
	@FindBy(xpath=WebLocators.DATE)
	protected WebElement date;
	
	@FindBy(xpath=WebLocators.DATEFUTURE)
	protected WebElement datePicker;
	
	
	
	
	
	
	
	
	
	public void enterFromCityAndEnter()
	{
		GenericWebFunctions.waitUntil(fromCityTxtBox,"visible");
		GenericWebFunctions.clickByWebElement(fromCityTxtBox);
		GenericWebFunctions.waitUntil(cityCombobox,"visible");
		GenericWebFunctions.waitUntil(fromCityInputBox,"visible");
		ExtentReportManager.getTest().log(Status.INFO, "Entering Text in From City: "+Constants.FROM_CITY);
		GenericWebFunctions.sendKeysByElement(fromCityInputBox, Constants.FROM_CITY);
		GenericWebFunctions.sendKeysByElement(fromCityInputBox, Keys.ENTER);	
	}
	//
	public void selectFirstCity() throws InterruptedException
	{
		GenericWebFunctions.waitUntil(firstOption,"visible");
		ExtentReportManager.getTest().log(Status.INFO, "Selecting first option from city dropdown");
		GenericWebFunctions.clickByWebElement(firstOption);	
	}
	
	public void enterToCityAndEnter() throws InterruptedException
	{
		GenericWebFunctions.waitUntil(toCityTxtBox,"visible");
		GenericWebFunctions.clickByWebElement(toCityTxtBox);
		GenericWebFunctions.waitUntil(cityCombobox,"visible");
		GenericWebFunctions.waitUntil(toCityInputBox,"visible");
		ExtentReportManager.getTest().log(Status.INFO, "Entering Text in To City: "+Constants.TO_CITY);
		toCityInputBox.clear();
		Thread.sleep(3000);
		GenericWebFunctions.sendKeysByElement(toCityInputBox, Constants.TO_CITY);
		
		GenericWebFunctions.sendKeysByElement(toCityInputBox, Keys.ENTER);
		Thread.sleep(1000);
		

		
	}
	public void clickSearchBtn()
	{
		GenericWebFunctions.waitUntil(searchBtn,"visible");
		ExtentReportManager.getTest().log(Status.INFO, "Clicking Search Button");
		GenericWebFunctions.clickByWebElement(searchBtn);	
	}
	
	public void clickDate()
	{
		GenericWebFunctions.waitUntil(datePicker,"visible");
		ExtentReportManager.getTest().log(Status.INFO, "Clicking Date");
		//GenericWebFunctions.clickByWebElement(date);
		GenericWebFunctions.clickByWebElement(datePicker);
	}
	

	
	
	

}

package com.automation.pages.web;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.automation.base.BaseWebPage;
import com.automation.functions.GenericWebFunctions;
import com.automation.util.WebLocators;

public class FlightDetails extends BaseWebPage {
	
	@FindBy(xpath=WebLocators.PRICE)
	protected List<WebElement> price;
	
	
	public void findFirstLowestFlight()
	{
		System.out.println(price.get(0).getText(););
		
	}
	

}

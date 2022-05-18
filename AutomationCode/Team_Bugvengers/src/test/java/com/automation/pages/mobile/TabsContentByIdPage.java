package com.automation.pages.mobile;

import com.automation.base.BaseMobilePage;
import com.automation.functions.GenericMobileFunctions;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class TabsContentByIdPage extends BaseMobilePage {

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='TAB1']")
	private AndroidElement tab1Option;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='TAB2']")
	private AndroidElement tab2Option;

	public AndroidElement findElementByText(String text) {
		return findElementByStrategy("text", text);
	}

	public void findElementAndClick(String text) {
		AndroidElement e = (AndroidElement) GenericMobileFunctions.findElementByText(text);
		// test.log(Status.INFO, "Clicking Element");
		GenericMobileFunctions.clickElement(e);
	}

	public String findElementAndGetText(String text) {
		AndroidElement e = findElementByText(text);
		// AndroidElement e= GenericMobileFunctions.findElementByText(driver, text);
		// test.log(Status.INFO, "Getting Text of element");
		return GenericMobileFunctions.getText(e);
	}

	public AndroidElement findElementByStrategy(String locatorStrategy, String locator) {
		// test.log(Status.INFO, "Finding element by Strategy: "+locatorStrategy+" ,with
		// locator as "+locator);
		return (AndroidElement) GenericMobileFunctions.findElementByStrategy(locatorStrategy, locator);
	}

}

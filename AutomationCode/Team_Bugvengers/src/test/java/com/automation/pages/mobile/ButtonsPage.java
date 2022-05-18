package com.automation.pages.mobile;

import com.automation.base.BaseMobilePage;
import com.automation.functions.GenericMobileFunctions;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class ButtonsPage extends BaseMobilePage {

	@AndroidFindBy(accessibility = "Toggle")
	private AndroidElement toggleButton;

	public void clickToggleButton() {
		// test.log(Status.INFO, "Clicking on Toggle Button");
		GenericMobileFunctions.clickElement(toggleButton);
	}

	public String getTextToggleButton() {
		// test.log(Status.INFO, "Getting text of Toggle Button");
		return GenericMobileFunctions.getText(toggleButton);
	}

}

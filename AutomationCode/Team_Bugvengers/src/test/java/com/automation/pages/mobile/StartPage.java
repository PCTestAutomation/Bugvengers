package com.automation.pages.mobile;

import org.testng.Assert;

import com.automation.base.BaseMobilePage;
import com.automation.functions.GenericMobileFunctions;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class StartPage extends BaseMobilePage {

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='API Demos']")
	private AndroidElement apiDemosPageTitle;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Views']")
	private AndroidElement viewOption;

	public ViewsPage goToViewPage() {
		// test.log(Status.INFO, "Clicking on Views Option");
		GenericMobileFunctions.clickElement(viewOption);
		ViewsPage viewPage = new ViewsPage();
		return viewPage;

	}

	public String getTitle() {
		// test.log(Status.INFO, "Getting Title of Page");
		return GenericMobileFunctions.getText(apiDemosPageTitle);
	}

}

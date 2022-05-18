package com.automation.pages.mobile;

import com.automation.base.BaseMobilePage;
import com.automation.functions.GenericMobileFunctions;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class ExpandableListsPage extends BaseMobilePage {

	@AndroidFindBy(accessibility = "1. Custom Adapter")
	private AndroidElement customAdapterOption;

	public CustomAdapterPage goToCustomAdapterPage() {
		GenericMobileFunctions.clickElement(customAdapterOption);
		CustomAdapterPage customAdapterPage = new CustomAdapterPage();
		return customAdapterPage;
	}

}

package com.automation.pages.mobile;

import com.automation.base.BaseMobilePage;
import com.automation.functions.GenericMobileFunctions;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class TabsPage extends BaseMobilePage {

	@AndroidFindBy(accessibility = "1. Content By Id")
	private AndroidElement contentByIdOption;

	public TabsContentByIdPage goToCustomAdapterPage() {
		// test.log(Status.INFO, "Clicking on 1. Content By Id");
		GenericMobileFunctions.clickElement(contentByIdOption);
		TabsContentByIdPage tabsContentByIdPage = new TabsContentByIdPage();
		return tabsContentByIdPage;
	}

}

package com.automation.pages.mobile;

import java.util.List;

import com.automation.base.BaseMobilePage;
import com.automation.functions.GenericMobileFunctions;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class CustomAdapterPage extends BaseMobilePage {

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='People Names']")
	private AndroidElement peopleNamesOption;

	@AndroidFindBy(xpath = "//android.widget.ExpandableListView/android.widget.TextView")
	private List<AndroidElement> listPeople;

	public String getElementText_PeopleTab(int index) {
		// test.log(Status.INFO, "Clicking on peopleNamesOption ");
		GenericMobileFunctions.clickElement(peopleNamesOption);
		// test.log(Status.INFO, "Getting text from element at index: "+index);
		return GenericMobileFunctions.getText(listPeople.get(index));

	}

}

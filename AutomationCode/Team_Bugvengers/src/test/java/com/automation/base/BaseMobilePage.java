package com.automation.base;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.support.PageFactory;

import com.automation.functions.GenericMobileFunctions;
import com.automation.util.ExtentReportManager;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class BaseMobilePage extends BaseFixture {

	// Common Page elements
	/*
	 * @AndroidFindBy(xpath="//android.widget.TextView[@text='Content']")
	 * 
	 * @iOSXCUITFindBy(
	 * xpath="//XCUIElementTypeButton[@label='Content, tab, 3 of 5']") protected
	 * MobileElement exploreLink;
	 * 
	 * @AndroidFindBy(xpath="//android.widget.TextView[@text='Home']")
	 * 
	 * @iOSXCUITFindBy(
	 * xpath="//XCUIElementTypeButton[@label='For you, tab, 1 of 5']") protected
	 * MobileElement homeLink;
	 **/

	public BaseMobilePage() {
		PageFactory.initElements(new AppiumFieldDecorator(getMobileDriver()), this);

	}

	// common verification functions

}

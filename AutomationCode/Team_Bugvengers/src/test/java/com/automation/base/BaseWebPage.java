package com.automation.base;

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class BaseWebPage extends BaseFixture {

	public BaseWebPage() {
		PageFactory.initElements(getWebDriver(), this);

	}

}

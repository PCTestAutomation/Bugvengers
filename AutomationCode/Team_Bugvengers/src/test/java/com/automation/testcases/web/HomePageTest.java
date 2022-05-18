package com.automation.testcases.web;

import org.testng.annotations.Test;

import com.automation.base.BaseFixture;
import com.automation.pages.web.HomePage;

public class HomePageTest extends BaseFixture {

	@Test(testName = "DummyWebTest", description = "Enter Search keyword in searchbox and perform search")
	public void verifySearch() {
		HomePage homePage = new HomePage();
		homePage.enterDataInSearchBox();
		homePage.clickSearchBtn();

	}

}

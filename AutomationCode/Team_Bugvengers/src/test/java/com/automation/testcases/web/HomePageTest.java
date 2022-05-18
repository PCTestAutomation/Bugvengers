package com.automation.testcases.web;

import org.testng.annotations.Test;

import com.automation.base.BaseFixture;
import com.automation.pages.web.FlightDetails;
import com.automation.pages.web.HomePage;

public class HomePageTest extends BaseFixture {

	@Test(testName = "DummyWebTest", description = "Enter Search keyword in searchbox and perform search")
	public void verifySearch() throws InterruptedException {
		HomePage homePage = new HomePage();
		homePage.enterFromCityAndEnter();
		homePage.selectFirstCity();
		homePage.enterToCityAndEnter();
		homePage.selectFirstCity();
		homePage.clickDate();
		homePage.clickSearchBtn();
		FlightDetails flightDetails= new FlightDetails();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

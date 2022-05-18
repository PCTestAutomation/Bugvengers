package com.automation.testcases.api;

import org.testng.annotations.Test;

import com.automation.base.BaseFixture;

public class DummyTestCase extends BaseFixture {
	
	@Test(testName="Dummy API Test",description="Dummy API Description")
	public void dummyTestCase()
	{
		System.out.println("We ran a Dummy API Test");
	}
	
	

}

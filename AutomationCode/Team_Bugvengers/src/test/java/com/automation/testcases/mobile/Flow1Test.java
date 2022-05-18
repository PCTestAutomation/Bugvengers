package com.automation.testcases.mobile;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.automation.base.BaseFixture;
import com.automation.pages.mobile.DragandDropPage;
import com.automation.pages.mobile.StartPage;
import com.automation.pages.mobile.ViewsPage;
import com.automation.util.Constants;



//@Listeners(com.pom.apidemos.listeners.TestListener.class)
public class Flow1Test extends BaseFixture {
	
	StartPage str;
	ViewsPage viewPage;
	DragandDropPage drgaAndDropPage;
	
	@BeforeMethod
	public void beforeMethod()
	{
		str=new StartPage();
	}
	
	
	@Test(testName="Flow 1 Test",description="Performing Drag and Drop")
	public void flow1Test() throws Exception
	{
		
		SoftAssert sa = new SoftAssert();
		
		String actualText=str.getTitle();
		sa.assertEquals(actualText,Constants.API_DEMOS_TITLE);
		viewPage=str.goToViewPage();
		drgaAndDropPage=(DragandDropPage) viewPage.scrollToElementAndClick(Constants.DRAG_AND_DROP_PAGE);
		drgaAndDropPage.dragAndDropCircles();
		sa.assertTrue(drgaAndDropPage.hiddenCircleIsDisplayed(), "Drag and drop failed as Hidden circle not found");
		sa.assertAll();
	}
	
	

}

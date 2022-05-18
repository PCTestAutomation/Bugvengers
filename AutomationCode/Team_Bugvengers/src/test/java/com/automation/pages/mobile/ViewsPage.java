package com.automation.pages.mobile;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.openqa.selenium.support.PageFactory;

import com.automation.base.BaseMobilePage;
import com.automation.functions.GenericMobileFunctions;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class ViewsPage extends BaseMobilePage {

	@AndroidFindBy(accessibility = "Drag and Drop")
	private AndroidElement dragAndDropOption;

	@AndroidFindBy(accessibility = "Expandable Lists")
	private AndroidElement expandableListsOption;

	@AndroidFindBy(accessibility = "Buttons")
	private AndroidElement buttonsOption;

	public DragandDropPage goToDragAndDropPage() {
		// test.log(Status.INFO, "Clicking on Drag and Drop Option");
		GenericMobileFunctions.clickElement(dragAndDropOption);
		DragandDropPage dragAndDropPage = new DragandDropPage();
		return dragAndDropPage;
	}

	public ExpandableListsPage goToExpandableListPage() {
		// test.log(Status.INFO, "Clicking on Expandable List Option");
		GenericMobileFunctions.clickElement(expandableListsOption);
		ExpandableListsPage expandableListPage = new ExpandableListsPage();
		return expandableListPage;
	}

	public ButtonsPage goToButtonsPage() {
		// test.log(Status.INFO, "Clicking on Button Option");
		GenericMobileFunctions.clickElement(buttonsOption);
		ButtonsPage buttonsPage = new ButtonsPage();
		return buttonsPage;
	}

	public String scrollToElementAndGetText(String textToScroll) {
		// test.log(Status.INFO, "Scrolling to element: "+textToScroll);
		AndroidElement e = scrollToElement(textToScroll);
		// test.log(Status.INFO, "Getting text of element: "+textToScroll);
		return GenericMobileFunctions.getText(e);
	}

	public AndroidElement scrollToElement(String textToScroll) {
		// AndroidElement e=GenericFunctions.scrollToElementByText(driver,textToScroll);
		AndroidElement e = GenericMobileFunctions.androidScroll(textToScroll);
		return e;
	}

	public Object scrollToElementAndClick(String textToScroll)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException

	{
		// test.log(Status.INFO, "Scrolling to element: "+textToScroll);
		AndroidElement e = scrollToElement(textToScroll);
		// test.log(Status.INFO, "Clicking on "+textToScroll+" on Views Page");
		GenericMobileFunctions.clickElement(e);

		return getPage(textToScroll);

	}

	public Object getPage(String text) throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		String Page = text.replace(" ", "").concat("Page");
		System.out.println("Page is: " + Page);
		Class pageClass = Class.forName("com.automation.pages.mobile." + Page);
		return pageClass.getConstructor(io.appium.java_client.android.AndroidDriver.class,
				com.aventstack.extentreports.ExtentTest.class).newInstance();

	}

}

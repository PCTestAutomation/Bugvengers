package com.automation.pages.mobile;

import java.time.Duration;

import org.openqa.selenium.By;

import com.automation.base.BaseMobilePage;
import com.automation.functions.GenericMobileFunctions;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class DragandDropPage extends BaseMobilePage {

	@AndroidFindBy(id = "io.appium.android.apis:id/drag_dot_2")
	private AndroidElement circle2;

	@AndroidFindBy(id = "io.appium.android.apis:id/drag_dot_3")
	private AndroidElement circle3;

	@AndroidFindBy(id = "io.appium.android.apis:id/drag_dot_hidden")
	private AndroidElement circleHidden;

	public void dragAndDropCircles() {
		// test.log(Status.INFO, "Dragging "+"circle3"+" to "+"circle2");
		GenericMobileFunctions.dragAndDrop(circle3, circle2);
	}

	public boolean hiddenCircleIsDisplayed() {
		// test.log(Status.INFO, "Checking if "+"circleHidden"+" is displayed");
		return GenericMobileFunctions.isElementDisplayed(circleHidden);
	}

}

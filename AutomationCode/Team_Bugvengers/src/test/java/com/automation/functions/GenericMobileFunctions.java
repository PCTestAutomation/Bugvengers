package com.automation.functions;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import com.automation.base.BaseMobilePage;
import com.automation.util.Constants;
import com.automation.util.ExtentReportManager;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.nativekey.PressesKey;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import io.appium.java_client.clipboard.HasClipboard;

public class GenericMobileFunctions extends BaseMobilePage {

	// both ios and android
	public static void clickElement(MobileElement ele) {
		WaitForElement(ele);
		ele.click();
	}

	// both ios and android
	public static void clear(MobileElement ele) {
		WaitForElement(ele);
		ele.clear();
	}

	// both ios and android
	public static void sendKeys(MobileElement ele, String entryText) {
		WaitForElement(ele);
		clear(ele);
		ele.sendKeys(entryText);
	}

	public static void sendKeysWithoutClearingInitialText(MobileElement ele, String entryText) {
		WaitForElement(ele);
		ele.sendKeys(entryText);
	}

	// both ios and android
	public static int checkSizeoflist(List<MobileElement> ele) {
		return ele.size();
	}

	// both ios and android
	public static String getAttribute(MobileElement ele, String attribute) {
		WaitForElement(ele);
		return ele.getAttribute(attribute);
	}

	// both ios and android
	public static Boolean WaitForElement(MobileElement ele) {
		try {
			WebDriverWait wait = new WebDriverWait(getMobileDriver(), 20);
			wait.until(ExpectedConditions.visibilityOf(ele));
			return true;
		} catch (Exception e) {
			// e.printStackTrace();
			return false;
		}
	}

	public static void setImplicitlyWait() {
		getMobileDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	public static Boolean isElementDisplayed(MobileElement ele) {
		try {
			ele.isDisplayed();
		}

		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// both ios and android
	public static String getText(MobileElement ele) {
		WaitForElement(ele);
		String txt = null;
		switch (getPlatformName()) {
		case "Android":
			txt = getAttribute(ele, "text");
			break;
		case "iOS":
			txt = getAttribute(ele, "label");
			break;
		}

		return txt;
	}

	// both ios and android
	public static String getAccessibilityId(MobileElement ele) {
		WaitForElement(ele);
		String txt = "";
		switch (getPlatformName()) {
		case "Android":
			txt = getAttribute(ele, "content-desc");
			break;
		case "iOS":
			txt = getAttribute(ele, "accessibility-id");
			break;
		}

		return txt;
	}

	// both ios and android
	public static void dragAndDrop(MobileElement from, MobileElement to) {
		WaitForElement(from);
		WaitForElement(to);

		int middleX = from.getLocation().x + (from.getSize().width / 2);// to start from center
		int toY = to.getLocation().y + (to.getSize().height / 2);// to start from center
		int startY = from.getLocation().y + (from.getSize().height / 2);

		TouchAction act = new TouchAction(getMobileDriver());
		act.longPress(PointOption.point(middleX, startY))
				// .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3)))
				.waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).moveTo(PointOption.point(middleX, toY))
				.release().perform();
	}

	// both ios and android
	public static void tapByCordinates(int x, int y) throws InterruptedException {
		Thread.sleep(4000);
		TouchAction act = new TouchAction(getMobileDriver());
		act.tap(PointOption.point(x, y)).perform();

		Thread.sleep(3000);

	}

	public static AndroidElement androidScroll(String text) {
		MobileElement ele = null;
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ele = getMobileDriver().findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()"
					+ ".scrollable(true)).scrollIntoView(" + "new UiSelector().textContains(\"" + text + "\"));"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (AndroidElement) ele;
	}

	// text should be name attribute
	public static void iOSScroll(String text) {

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		try {
			MobileElement element = getMobileDriver().findElement(MobileBy.AccessibilityId(text));
			String elementID = element.getId();
			HashMap<String, String> scrollObject = new HashMap<String, String>();
			scrollObject.put("element", elementID);
//			  scrollObject.put("direction", "down");
//			  scrollObject.put("predicateString", "label == 'ADD TO CART'");
			// scrollObject.put("name", "Discover more");
			scrollObject.put("toVisible", "true");
			getMobileDriver().executeScript("mobile:scroll", scrollObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void scroll(String text) {
		if (getPlatformName().equalsIgnoreCase("Android"))
			androidScroll(text);
		else if (getPlatformName().equalsIgnoreCase("iOS"))
			iOSScroll(text);
		;
	}

	public static void scrollByCordinates() {
		Dimension dim = getMobileDriver().manage().window().getSize();

		int width = dim.getWidth();
		int height = dim.getHeight();

		int middlex = width / 2;
		int starty = (int) (height * 0.8);
		int endy = (int) (height * 0.2);

		TouchAction act = new TouchAction(getMobileDriver());
		act.press(PointOption.point(middlex, starty)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3)))
				.moveTo(PointOption.point(middlex, endy)).release().perform();

	}

	public static void scrollByCordinatesUp() {
		Dimension dim = getMobileDriver().manage().window().getSize();

		int width = dim.getWidth();
		int height = dim.getHeight();

		int middlex = width / 2;
		int starty = (int) (height * 0.8);
		int endy = (int) (height * 0.2);

		TouchAction act = new TouchAction(getMobileDriver());
		act.press(PointOption.point(middlex, endy)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3)))
				.moveTo(PointOption.point(middlex, starty)).release().perform();

	}

	public static MobileElement findElementByText(String text) {
		MobileElement ele = null;
		if (platformName.equals("Android"))
			ele = androidFindElementByText(text);
		else if (platformName.equals("iOS"))
			ele = iOSFindElementByText(text);
		;
		return ele;

	}

	public static MobileElement findElementByEditText(String text) {
		MobileElement ele = null;
		if (platformName.equals("Android"))
			ele = androidFindElementByEditText(text);
		else if (platformName.equals("iOS"))
			ele = iOSFindElementByText(text);
		;
		return ele;

	}

	public static MobileElement findElementByAccessibilityID(String text) {
		MobileElement ele = null;
		try {
			ele = getMobileDriver().findElement(MobileBy.AccessibilityId(text));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ele;
	}

	public static MobileElement findButtonElementByText(String text) {
		System.out.println(getPlatformName());
		MobileElement ele = null;
		if (platformName.equals("Android"))
			ele = androidFindButtonElementByText(text);
		else if (platformName.equals("iOS"))
			ele = iOSFindElementByText(text);
		;
		return ele;

	}

	public static MobileElement androidFindElementByText(String text) {
		MobileElement ele = getMobileDriver()
				.findElement(MobileBy.xpath("//android.widget.TextView[@text='" + text + "\']"));
		return ele;
	}

	public static MobileElement androidFindElementByEditText(String text) {
		MobileElement ele = getMobileDriver()
				.findElement(MobileBy.xpath("//android.widget.EditText[@text='" + text + "\']"));
		return ele;
	}

	public static MobileElement androidFindButtonElementByText(String text) {
		MobileElement ele = getMobileDriver()
				.findElement(MobileBy.xpath("//android.widget.Button[@text='" + text + "\']"));
		return ele;
	}

	public static MobileElement iOSFindElementByText(String text) {
		MobileElement ele = getMobileDriver().findElement(MobileBy.name(text));
		return ele;
	}

	// function customized per application
	public static MobileElement findElementByStrategy(String locatorStrategy, String locator) {

		/**
		 * Use this function when either text or content-desc or resource-id or class is
		 * known for Android Element.
		 * 
		 */

		MobileElement ele = null;

		if (locatorStrategy.equals(Constants.TEXT))
			ele = getMobileDriver().findElement(MobileBy.xpath("//android.widget.TextView[@text='" + locator + "\']"));
		else if (locatorStrategy.equals(Constants.CONTENTDESC))
			ele = getMobileDriver().findElement(MobileBy.AccessibilityId(locator));
		else if (locatorStrategy.equals(Constants.RESOURCEID))
			ele = getMobileDriver().findElement(MobileBy.id(locator));
		else if (locatorStrategy.equals(Constants.CLASSNAME)) {
			ele = getMobileDriver().findElement(MobileBy.className(locator));
		}

		return ele;
	}

	public static String getTextfromClipboard() {
		String text = ((HasClipboard) getMobileDriver()).getClipboardText();
		return text;
	}

	// Generic helper Functions
	public static String generateRandomAlphanumericString() {
		/** Generates random alphanumeric String of 20 characters */
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 20;
		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
		return generatedString;
	}

	public static String generateRandomAlphanumericString500Chars() {
		/** Generates random alphanumeric String of 20 characters */
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 500;
		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
		return generatedString;
	}

	public static String generateRandomDoubleInStringFormat() {

		/** Generates random double upto two decimal places */
		double leftLimit = 1D;
		double rightLimit = 100D;
		double generatedDouble = leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);

		String st = String.format("%.2f", generatedDouble);
		st = st.contains(".") ? st.replaceAll("0*$", "").replaceAll("\\.$", "") : st;

		// return String.format("%.2f", generatedDouble);
		return st;
	}

	public static void takeScreenshot() {
		TakesScreenshot scr = (TakesScreenshot) getMobileDriver();
		File sourceFile = scr.getScreenshotAs(OutputType.FILE);

		byte[] encoded = null;
		try {
			encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(sourceFile));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			ExtentReportManager.getTest().log(Status.INFO, ("Taking Screenshot"), MediaEntityBuilder
					.createScreenCaptureFromBase64String(new String(encoded, StandardCharsets.US_ASCII)).build());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static boolean CheckForDecimalNumberPattern(String textTocheck) {

		String regex = "\\d*\\.?\\d+";

		Pattern p = Pattern.compile(regex);

		if (textTocheck == null) {
			return false;
		}

		Matcher m = p.matcher(textTocheck);

		if (m.matches()) {
			return true;
		} else
			return false;
	}

	public static void pressNumPadKeyForString(String height) {
		int size = height.length();
		for (int i = 0; i < size; i++) {
			char temp = height.charAt(i);

			switch (temp) {

			case '0':
				((PressesKey) getMobileDriver()).pressKey(new KeyEvent(AndroidKey.DIGIT_0));
				break;
			case '1':
				((PressesKey) getMobileDriver()).pressKey(new KeyEvent(AndroidKey.DIGIT_1));
				break;
			case '2':
				((PressesKey) getMobileDriver()).pressKey(new KeyEvent(AndroidKey.DIGIT_2));
				break;
			case '3':
				((PressesKey) getMobileDriver()).pressKey(new KeyEvent(AndroidKey.DIGIT_3));
				break;
			case '4':
				((PressesKey) getMobileDriver()).pressKey(new KeyEvent(AndroidKey.DIGIT_4));
				break;
			case '5':
				((PressesKey) getMobileDriver()).pressKey(new KeyEvent(AndroidKey.DIGIT_5));
				break;
			case '6':
				((PressesKey) getMobileDriver()).pressKey(new KeyEvent(AndroidKey.DIGIT_6));
				break;
			case '7':
				((PressesKey) getMobileDriver()).pressKey(new KeyEvent(AndroidKey.DIGIT_7));
				break;
			case '8':
				((PressesKey) getMobileDriver()).pressKey(new KeyEvent(AndroidKey.DIGIT_8));
				break;
			case '9':
				((PressesKey) getMobileDriver()).pressKey(new KeyEvent(AndroidKey.DIGIT_9));
				break;
			case '.':
				((PressesKey) getMobileDriver()).pressKey(new KeyEvent(AndroidKey.PERIOD));
				break;
			default:
				ExtentReportManager.getTest().log(Status.INFO, "Invalid data");

			}
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		((PressesKey) getMobileDriver()).pressKey(new KeyEvent(AndroidKey.ENTER));

	}

	public static int checkOccurenceOfPatternInString(String text, String pattern) {
		int M = pattern.length();
		int N = text.length();
		int res = 0;

		/* A loop to slide pat[] one by one */
		for (int i = 0; i <= N - M; i++) {
			/*
			 * For current index i, check for pattern match
			 */
			int j;
			for (j = 0; j < M; j++) {
				if (text.charAt(i + j) != pattern.charAt(j)) {
					break;
				}
			}

			// if pat[0...M-1] = txt[i, i+1, ...i+M-1]
			if (j == M) {
				res++;
				j = 0;
			}

		}
		return res;
	}

	public static String generatePastDate(int days, int months, int years)

	{
		/** Generates past date in yyyy-MM-dd format */

		Date currentDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		LocalDateTime localDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		localDateTime = localDateTime.minusDays(days).minusYears(years).minusMonths(months);
		Date currentDatePlusOneDay = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		String newdate = dateFormat.format(currentDatePlusOneDay);

		return newdate;

	}

	public static String generateFutureDate(int days, int months, int years)

	{
		/** Generates Future date in yyyy-MM-dd format */

		Date currentDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		LocalDateTime localDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		localDateTime = localDateTime.plusDays(days).plusYears(years).plusMonths(months);
		Date currentDatePlusOneDay = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

		String newdate = dateFormat.format(currentDatePlusOneDay);

		return newdate;
	}

}

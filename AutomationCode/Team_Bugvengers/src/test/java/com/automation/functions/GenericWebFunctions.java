package com.automation.functions;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.automation.base.BaseWebPage;

public class GenericWebFunctions extends BaseWebPage {
	public static Actions actions = null;

	public static void sendKeysByXpath(String xpath, String key) throws Throwable {
		// WebElement webElement = getWebDriver().findElement(By.xpath(xpath));
		WebElement webElement = getWebElement(xpath, "xpath");
		if (webElement != null) {
			getWebDriverWait().until(ExpectedConditions.visibilityOf(webElement));
			webElement.clear();
			webElement.sendKeys(key);
		}
	}

	public static void sendKeysByXpath(String xpath, Keys key) {
		// WebElement webElement = getWebDriver().findElement(By.xpath(xpath));
		WebElement webElement = getWebElement(xpath, "xpath");
		if (webElement != null) {
			getWebDriverWait().until(ExpectedConditions.visibilityOf(webElement));
			webElement.clear();
			webElement.sendKeys(key);
		}
	}

	public static void sendKeysByElement(WebElement element, String key) {
		getWebDriverWait().until(ExpectedConditions.visibilityOf(element));
		element.clear();
		element.sendKeys(key);
	}

	public static void sendKeysByElement(WebElement element, Keys key) {
		getWebDriverWait().until(ExpectedConditions.visibilityOf(element));
		element.clear();
		element.sendKeys(key);
	}

	public static void sendKeysWithJavaScript(String xpath, String key) {
		WebElement webElement = getWebElement(xpath, "xpath");
		JavascriptExecutor executor = (JavascriptExecutor) getWebDriver();
		if (webElement != null) {
			executor.executeScript("arguments[0].value='" + key + "'", webElement);
			// executor.executeScript("arguments[0].value='qwerty';", webElement);
		}
	}

	public static void sendKeysUsingAction(String xpath, String key) {
		WebElement webElement = getWebElement(xpath, "xpath");
		if (webElement != null) {
			actions = new Actions(getWebDriver());
			actions.moveToElement(webElement);
			actions.sendKeys(key);
			actions.build().perform();
		}
	}

	public static void clickByXpath(String xpath) {
		// WebElement webElement = getWebDriver().findElement(By.xpath(xpath));
		WebElement webElement = getWebElement(xpath, "xpath");
		if (webElement != null) {
			webElement.click();
		}
	}

	public static void clickWithJavaScript(WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) getWebDriver();
		executor.executeScript("arguments[0].click();", element);
	}

	public static void clickWithJavaScript(String xpath) {
		// WebElement webElement = getWebDriver().findElement(By.xpath(xpath));
		WebElement webElement = getWebElement(xpath, "xpath");
		JavascriptExecutor executor = (JavascriptExecutor) getWebDriver();
		if (webElement != null) {
			executor.executeScript("arguments[0].click();", webElement);
		}
	}

	public static void clickByWebElement(WebElement element) {
		try {
			waitUntil(element, "clickable");
			element.click();
		} catch (Exception exception) {

			getSoftAssert().fail("failed to click on the target element -  " + exception.getMessage());
		}
	}

	public static void waitForPageLoad() {
		int timeOut = 180;
		try {
			ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
				public Boolean apply() {
					return ((JavascriptExecutor) getWebDriver()).executeScript("return document.readyState")
							.equals("complete");
				}

				@Override
				public Boolean apply(WebDriver arg0) {
					// TODO Auto-generated method stub
					return null;
				}
			};
			getWebDriverWait().until(pageLoadCondition);
		} catch (TimeoutException tOut) {
			getSoftAssert().fail("Timed Out waiting for page to load.");
			tOut.printStackTrace();
		}
	}

	public static void waitUntil(String xpath, String type) {
		int timeOut = 180;
		if (type.trim().toLowerCase().equals("visible")) {
			try {
				getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			} catch (NoSuchElementException noSuchElementException) {
				getSoftAssert().fail("fail to display a webelement with xpath: " + xpath);
				noSuchElementException.printStackTrace();
			} catch (TimeoutException tOut) {
				getSoftAssert().fail("Timed Out waiting for visibility of webelement with xpath: " + xpath);
				tOut.printStackTrace();
			}
		} else if (type.trim().toLowerCase().equals("clickable")) {
			try {
				getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
			} catch (NoSuchElementException noSuchElementException) {
				getSoftAssert().fail("fail to click a webelement with xpath: " + xpath);
				noSuchElementException.printStackTrace();
			} catch (TimeoutException tOut) {
				getSoftAssert().fail("Timed Out waiting for clickablility of webelement with xpath: " + xpath);
				tOut.printStackTrace();

			}
		} else if (type.trim().toLowerCase().equals("invisible")) {
			try {
				getWebDriverWait().until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
			} catch (NoSuchElementException noSuchElementException) {
				getSoftAssert().fail("fail to wait for a webelement with xpath: " + xpath + " to disappear");
				noSuchElementException.printStackTrace();
			} catch (TimeoutException tOut) {
				getSoftAssert().fail("Timed Out waiting for invisibility of webelement with xpath: " + xpath);
				tOut.printStackTrace();

			}
		} else if (type.trim().toLowerCase().equals("all")) {
			try {
				getWebDriverWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpath)));
			} catch (NoSuchElementException noSuchElementException) {
				getSoftAssert().fail("fail to wait for all webelement to be visible with xpath: " + xpath);
				noSuchElementException.printStackTrace();
			} catch (TimeoutException tOut) {
				getSoftAssert().fail("Timed Out waiting for all webelements to be visible with xpath: " + xpath);
				tOut.printStackTrace();

			}
		} else if (type.trim().toLowerCase().equals("present")) {
			try {
				getWebDriverWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xpath)));
			} catch (NoSuchElementException noSuchElementException) {
				getSoftAssert().fail("fail to wait for all webelement to present with xpath: " + xpath);
				noSuchElementException.printStackTrace();
			} catch (TimeoutException tOut) {
				getSoftAssert().fail("Timed Out waiting for all webelements to present with xpath: " + xpath);
				tOut.printStackTrace();

			}
		}
	}

	public static void waitUntil(WebElement webElement, String type) {
		if (type.trim().toLowerCase().equals("visible")) {
			try {
				getWebDriverWait().until(ExpectedConditions.visibilityOf(webElement));
			} catch (TimeoutException tOut) {
				getSoftAssert().fail("Timed Out waiting for visibility of passed webelement");
				tOut.printStackTrace();

			}
		} else if (type.trim().toLowerCase().equals("clickable")) {
			try {
				getWebDriverWait().until(ExpectedConditions.elementToBeClickable(webElement));
			} catch (TimeoutException tOut) {
				getSoftAssert().fail("Timed Out waiting for clickability of passed webelement");
				tOut.printStackTrace();

			}
		} else if (type.trim().toLowerCase().equals("invisible")) {
			List<WebElement> webElements = new ArrayList<WebElement>();
			webElements.add(webElement);
			try {
				getWebDriverWait().until(ExpectedConditions.invisibilityOfAllElements(webElements));
			} catch (TimeoutException tOut) {
				getSoftAssert().fail("Timed Out waiting for invisibility of passed webelement");
				tOut.printStackTrace();

			}
		}
	}

	public static void waitUntil(List<WebElement> webElements, String type) {
		if (type.trim().toLowerCase().equals("invisible")) {
			try {
				getWebDriverWait().until(ExpectedConditions.invisibilityOfAllElements(webElements));
			} catch (TimeoutException tOut) {
				getSoftAssert().fail("Timed Out waiting for invisibility of all passed webelement");

			}
		} else if (type.trim().toLowerCase().equals("all")) {
			try {
				getWebDriverWait().until(ExpectedConditions.visibilityOfAllElements(webElements));
			} catch (TimeoutException tOut) {
				getSoftAssert().fail("Timed Out waiting for invisibility of all passed webelement");

			}
		}
	}

	public static void waitUntilVisible(String xpath) {
		try {
			getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		} catch (TimeoutException tOut) {
			getSoftAssert().fail("Timed Out waiting for visibility of webelement with xpath: " + xpath);
			tOut.printStackTrace();

		}
	}

	public static boolean xpathElementIsDisplayed(String xpath) {
		if (getWebDriver().findElements(By.xpath(xpath)).size() != 0) {
			if (getWebDriver().findElement(By.xpath(xpath)).isDisplayed()) {
				return true;
			}
		}
		return false;
	}

	public boolean checkWebElementValue(String xpath, String expectedValue) {
		waitUntil(xpath, "clickable");
		WebElement webElement = getWebElement(xpath, "xpath");
		if (webElement != null && webElement.getAttribute("value").equals(expectedValue)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method is used to get an element value displayed on web application
	 * 
	 * @param xpath  This parameter specifies the xpath of web element
	 * @param sValue This parameter the value required by user for e.g. 'text' |
	 *               'attribute value'
	 * @param sType  This parameter specifies the stat of a web element should be
	 *               before fetching its value for e.g. visible | clickable
	 * @return sElementValue This is the value of web element which will be returned
	 */
	public String getWebElementValue(String xpath, String sValue, String sType) {
		String sElementValue = "";
		waitUntil(xpath, sType);
		WebElement webElement = getWebElement(xpath, "xpath");
		if (webElement != null && sValue.trim().toLowerCase().equals("text")) {
			sElementValue = webElement.getText();
		} else if (webElement != null && sValue.trim().toLowerCase().equals("attributevalue")) {
			sElementValue = webElement.getAttribute("value");
		} else if (webElement != null && sValue.trim().toLowerCase().equals("class")) {
			sElementValue = webElement.getAttribute("class");
		} else if (webElement != null && sValue.trim().toLowerCase().equals("innertext")) {
			sElementValue = webElement.getAttribute("innerText").trim();
		}
		return sElementValue;
	}

	/**
	 * This method is used to generate a random combination of different types
	 * 
	 * @param iLength This parameter specifies the length of Random Combination to
	 *                be generated
	 * @param sType   This parameter specifies the type of combination required i.e.
	 *                Numeric | Alphabetic
	 * @return sRandomCombination This is the value which will be returned once a
	 *         random combination is generated
	 */
	public String generateRandomCombination(int iLength, String sType) {
		String sAlpahebets, sNumbers;
		char character = 0;
		sAlpahebets = "abcdefghijklmnopqrstuvwxyz";
		sNumbers = "0123456789";
		StringBuilder sRandomCombination = new StringBuilder();
		Random oRandom = new Random();
		for (int i = 0; i < iLength; i++) {
			if (sType.toLowerCase().equals("alphabet"))
				character = sAlpahebets.toCharArray()[oRandom.nextInt(sAlpahebets.length())];
			else if (sType.toLowerCase().equals("number"))
				character = sNumbers.toCharArray()[oRandom.nextInt(sNumbers.length())];
			sRandomCombination.append(character);
		}
		return sRandomCombination.toString();
	}

	public static void setTextInAutoCompleteDropDown(String sDropDownXpath, String sTextBoxXpath, String sText,
			String sComment) throws Throwable {
		performAction("click", sDropDownXpath, "");
		sendKeysByXpath(sTextBoxXpath, sText);
		performAction("sendkeys", sTextBoxXpath, "tab");
	}

	public static void performAction(String sAction, String sXpath, String sOptionalParameter) {
		waitUntil(sXpath, "visible");
		waitUntil(sXpath, "clickable");
		WebElement webElement = getWebElement(sXpath, "xpath");
		if (webElement != null) {
			actions = new Actions(getWebDriver());
			actions.moveToElement(webElement);
			if (sAction.equalsIgnoreCase("click"))
				performClickAction(actions);
			else if (sAction.equalsIgnoreCase("sendKeys"))
				performSendKeyAction(actions, sOptionalParameter);
		}
		// actions = null;
	}

	public static void performAction(String sAction, WebElement webElement, String sOptionalParameter) {
		waitUntil(webElement, "visible");
		waitUntil(webElement, "clickable");
		// WebElement webElement = getWebElement(sXpath, "xpath");
		if (webElement != null) {
			actions = new Actions(getWebDriver());
			actions.moveToElement(webElement);
			if (sAction.equalsIgnoreCase("click"))
				performClickAction(actions);
			else if (sAction.equalsIgnoreCase("sendKeys"))
				performSendKeyAction(actions, sOptionalParameter);
		}
		// actions = null;
	}

	public static void performClickAction(Actions actions) {
		actions.click();
		actions.build().perform();
	}

	public static void performSendKeyAction(Actions actions, String sKeys) {
		if (sKeys.equalsIgnoreCase("tab"))
			actions.sendKeys(Keys.TAB);
		else if (sKeys.equalsIgnoreCase("enter"))
			actions.sendKeys(Keys.ENTER);
		else if (sKeys.equalsIgnoreCase("return"))
			actions.sendKeys(Keys.RETURN);
		else if (sKeys.equalsIgnoreCase("down"))
			actions.sendKeys(Keys.ARROW_DOWN);
		actions.perform();
	}

	public static WebElement getWebElement(String locator, String locatorType) {
		WebElement webElement = null;
		if (locatorType.trim().toLowerCase().equals("xpath")) {
			try {
				webElement = getWebDriver().findElement(By.xpath(locator));
				return webElement;
			} catch (NoSuchElementException t) {
				getSoftAssert().fail("Element with xpath \"" + locator + "\"does not exist");
				return webElement;
			}
		}
		if (locatorType.trim().toLowerCase().equals("classname")) {
			try {
				webElement = getWebDriver().findElement(By.className(locator));
				return webElement;
			} catch (NoSuchElementException t) {
				getSoftAssert().fail("Element with className \"" + locator + "\"does not exist");
				return webElement;
			}
		}
		if (locatorType.trim().toLowerCase().equals("cssselector")) {
			try {
				webElement = getWebDriver().findElement(By.cssSelector(locator));
				return webElement;
			} catch (NoSuchElementException t) {
				getSoftAssert().fail("Element with CssSelector \"" + locator + "\"does not exist");
				return webElement;
			}
		}
		return webElement;
	}

	public WebElement getWebElement(WebElement parentElement, String remainingXpath) {
		WebElement webElement = null;
		try {
			webElement = parentElement.findElement(By.xpath(remainingXpath));
		} catch (NoSuchElementException t) {
			getSoftAssert().fail("Element with xpath \"" + remainingXpath + "\"does not exist");

		}
		return webElement;
	}

	public static void handleGeolocationAlert() throws InterruptedException {
		Thread.sleep(10000);
		try {
			Alert alert = getWebDriver().switchTo().alert();
			String alertText = alert.getText();
			String text = "Pizza Hut wants to use your location, but first you need to go to Settings and turn on Location";
			if (alertText.contains(text)) {
				alert.accept();
			}
		} catch (NoAlertPresentException e) {
			System.out.println("Geolocation alert is not present");
		}
	}

	public List<WebElement> findElementsForXpath(String xpathString) {
		List<WebElement> webElementList = null;
		try {
			webElementList = getWebDriver().findElements(By.xpath(xpathString));
		} catch (Exception ex) {
			getSoftAssert().fail("findElementsForXpath raised Exception with message: " + ex.getMessage());
		}
		return webElementList;
	}

	public List<WebElement> getWebElementsList(String locator, String locatorType) {
		List<WebElement> webElementList = null;
		if (locatorType.trim().toLowerCase().equals("xpath")) {
			try {
				webElementList = getWebDriver().findElements(By.xpath(locator));
				return webElementList;
			} catch (Exception ex) {
				getSoftAssert().fail("getWebElementsList raised  Exception with message " + ex.getMessage());
				return webElementList;
			}
		}
		if (locatorType.trim().toLowerCase().equals("classname")) {
			try {
				webElementList = getWebDriver().findElements(By.className(locator));
				return webElementList;
			} catch (Exception ex) {
				getSoftAssert().fail("getWebElementsList raised  Exception with message " + ex.getMessage());
				return webElementList;
			}
		}
		return webElementList;
	}

	public String getAttributeByXpath(String xpath, String attribute) {
		String returnValue = "";
		try {
			if (getWebDriver().findElements(By.xpath(xpath)).size() != 0) {
				returnValue = getWebDriver().findElement(By.xpath(xpath)).getAttribute(attribute);
			}
		} catch (NoSuchElementException ex) {
			getSoftAssert().fail("getAttributeByXpath raised Exception with message: " + ex.getMessage());
		}
		return returnValue;
	}

	public String getAttributeByElement(WebElement element, String attribute) {
		String returnValue = "";
		try {
			if (element != null) {
				returnValue = element.getAttribute(attribute);
			}
		} catch (NoSuchElementException ex) {
			getSoftAssert().fail("getAttributeByElement raised Exception with message: " + ex.getMessage());
		}
		return returnValue;
	}

	public boolean isAttributePresent(WebElement element, String attribute) {
		Boolean result = false;
		try {
			String value = element.getAttribute(attribute);
			if (value != null) {
				result = true;
			}
		} catch (Exception e) {
		}

		return result;
	}

	public boolean isAttributePresent(String xpath, String attribute) {
		Boolean result = false;

		WebElement element = getWebElement(xpath, "xpath");
		try {
			String value = element.getAttribute(attribute);
			if (value != null) {
				result = true;
			}
		} catch (Exception e) {
		}

		return result;
	}

	public int randomNumberInRange(int min, int max) {
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	public static void poll() {
		try {
			Thread.sleep((long) (Math.random() * 20000 + 10000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void scrollIntoview(WebElement element) {
		JavascriptExecutor je = (JavascriptExecutor) getWebDriver();
		je.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public static void scrollIntoview(String locator, String locatorType) {
		WebElement element = getWebElement(locator, locatorType);
		if (element != null) {
			JavascriptExecutor je = (JavascriptExecutor) getWebDriver();
			je.executeScript("arguments[0].scrollIntoView(true);", element);
		}
	}

	public static void scrollVerticallyUntilElementIsVisible(String xpath, int scrollCounter) {
		Boolean isElementPresent = xpathElementIsDisplayed(xpath);
		int attempt = 0;
		while (!isElementPresent && attempt < scrollCounter) {
			JavascriptExecutor js = (JavascriptExecutor) getWebDriver();
			js.executeScript("window.scrollBy(0,350)");
			isElementPresent = xpathElementIsDisplayed(xpath);
			attempt++;
		}
	}

	public WebElement returnDisplayedElement(String xpath) {
		WebElement displayedElement = null;
		List<WebElement> elements = findElementsForXpath(xpath);
		for (WebElement el : elements) {
			if (el.isDisplayed()) {
				displayedElement = el;
				break;
			}
		}
		return displayedElement;
	}

	public static void scrollVertically(int verticalPixel) {
		JavascriptExecutor je = (JavascriptExecutor) getWebDriver();
		je.executeScript("window.scrollBy(0," + verticalPixel + ")");
	}

	public static String generateFutureDate(int days, int months, int years, String datePattern) {

		Date currentDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
		LocalDateTime localDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		localDateTime = localDateTime.plusDays(days).plusYears(years).plusMonths(months);
		Date currentDatePlusOneDay = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		String newdate = dateFormat.format(currentDatePlusOneDay);
		return newdate;
	}

	public static String capitalizeFirstCharacterOfWord(String str) {
		str = str.toLowerCase();

		String words[] = str.split("\\s");
		String capitalizeWord = "";
		for (String w : words) {
			String first = w.substring(0, 1);
			String afterfirst = w.substring(1);
			capitalizeWord += first.toUpperCase() + afterfirst + " ";
		}
		return capitalizeWord.trim();
	}

}

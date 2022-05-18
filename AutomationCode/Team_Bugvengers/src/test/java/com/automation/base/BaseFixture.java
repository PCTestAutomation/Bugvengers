package com.automation.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.automation.functions.RestClientWrapper;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public class BaseFixture {

	protected static ThreadLocal<Properties> prop = new ThreadLocal<Properties>();
	protected static ThreadLocal<AppiumDriver<MobileElement>> mobileDriver = new ThreadLocal<AppiumDriver<MobileElement>>();
	protected static ThreadLocal<WebDriver> webDriver = ThreadLocal.withInitial(() -> {
		return null;
	});
	protected static ThreadLocal<String> platformName = new ThreadLocal<String>();
	protected static ThreadLocal<String> deviceName = new ThreadLocal<String>();
	protected static ThreadLocal<String> testType = new ThreadLocal<String>();
	protected static ThreadLocal<WebDriverWait> wait = new ThreadLocal<WebDriverWait>();
	protected static ThreadLocal<SoftAssert> soft = new ThreadLocal<SoftAssert>();
	protected static ThreadLocal<String> passScreenshotReq = new ThreadLocal<String>();
	protected static ThreadLocal<ChromeOptions> chromeOptions = ThreadLocal.withInitial(() -> {
		return null;
	});
	protected static ThreadLocal<FirefoxOptions> fireFoxOptions = ThreadLocal.withInitial(() -> {
		return null;
	});

	public static RestClientWrapper restClient;
	public static String baseUrl;
	public static String Userkey;
	public static String Usertoken;
	protected static RequestSpecification httpRequest;

	public static AppiumDriver<MobileElement> getMobileDriver() {
		return mobileDriver.get();
	}

	public void setMobileDriver(AppiumDriver<MobileElement> driver) {
		mobileDriver.set(driver);
	}

	public static WebDriver getWebDriver() {
		return webDriver.get();
	}

	public void setWebDriver(String browser) {
		switch (browser) {
		case "chrome":
			webDriver = ThreadLocal.withInitial(() -> {
				return WebDriverManager.chromedriver().create();
			});
			break;
		case "fireFox":
			webDriver = ThreadLocal.withInitial(() -> {
				return WebDriverManager.firefoxdriver().create();
			});
			break;
		default:
			throw new IllegalStateException("Unexpected value: " + browser);
		}

	}
	public void setWebDriver(ChromeOptions options) {
		WebDriverManager.chromedriver().setup();
			webDriver = ThreadLocal.withInitial(() -> {
				return new ChromeDriver(options);
			});
	}
	public void setWebDriver(FirefoxOptions options) {
		WebDriverManager.firefoxdriver().setup();
		webDriver = ThreadLocal.withInitial(() -> {
			return new FirefoxDriver(options);
		});
}

	public Properties getProperties() {
		return prop.get();
	}

	public void setProperties(Properties props2) {
		prop.set(props2);
	}

	public static String getPlatformName() {
		return platformName.get();
	}

	public void setPlatformName(String platformName2) {
		platformName.set(platformName2);
	}

	public String getDeviceName() {
		return deviceName.get();
	}

	public void setDeviceName(String deviceName2) {
		deviceName.set(deviceName2);
	}

	public String getTestType() {
		return testType.get();
	}

	public void setTestType(String testType2) {
		testType.set(testType2);
	}

	public static WebDriverWait getWebDriverWait() {
		return wait.get();
	}

	public void setSoftAssert() {
		soft = ThreadLocal.withInitial(() -> {
			return new SoftAssert();
		});

	}

	public static SoftAssert getSoftAssert() {
		return soft.get();
	}

	public void setWebDriverWait() {
		wait = ThreadLocal.withInitial(() -> {
			return new WebDriverWait(getWebDriver(), 90);
		});

	}

	public String getPassScreenshotFlag() {
		return passScreenshotReq.get();
	}
	
	public void setChromeOptions() {
		chromeOptions = ThreadLocal.withInitial(() -> {
			return new ChromeOptions();
		});
			
	}
	
	public ChromeOptions getChromeOptions() {
		return chromeOptions.get();
	}
	public void setFireFoxOptions() {
		fireFoxOptions = ThreadLocal.withInitial(() -> {
			return new FirefoxOptions();
		});
			
	}
	
	public FirefoxOptions getFirefoxOptions() {
		return fireFoxOptions.get();
	}
	
	
	

	public void setPassScreenshotFlag(String passScreenshotFlag) {
		passScreenshotReq.set(passScreenshotFlag);
	}

	@Parameters({ "testType", "emulator", "platformName", "platformVersion", "deviceName", "systemPort",
			"chromeDriverPort", "wdaLocalPort", "webkitDebugProxyPort", "passScreenshotFlag" })
	@BeforeTest()
	public void beforeTest(String testType, @Optional("androidOnly") String emulator,  @Optional String platformName,
			@Optional("MobileOnly") String platformVersion, @Optional("MobileOnly") String deviceName,
			@Optional("androidOnly") String systemPort, @Optional("androidOnly") String chromeDriverPort,
			@Optional("iOSOnly") String wdaLocalPort, @Optional("iOSOnly") String webkitDebugProxyPort,
			@Optional("MobileOnly") String passScreenshotFlag, ITestContext context) throws Exception {

		Properties prop = new Properties();
		FileInputStream fs = new FileInputStream(
				System.getProperty("user.dir") + "/src/test/resources/project.properties");
		prop.load(fs);
		setProperties(prop);
		setTestType(testType);
		context.setAttribute("TestType", getTestType());

		if (testType.equalsIgnoreCase("API")) {
			System.out.println("In Before Class: API");
			baseUrl = getProperties().getProperty("baseurl");
			Userkey = getProperties().getProperty("key");
			Usertoken = getProperties().getProperty("token");
			System.out.println("baseUrl"+baseUrl);
			httpRequest = RestAssured.given();
			restClient = new RestClientWrapper(baseUrl, httpRequest, Userkey, Usertoken);
		}

		if (testType.equalsIgnoreCase("Web") || testType.equalsIgnoreCase("Mobile")) {
			setPlatformName(platformName);
			setPassScreenshotFlag(passScreenshotFlag);
			context.setAttribute("passScreenshotFlag", getPassScreenshotFlag());
			context.setAttribute("Platform", getPlatformName());

		}

		if (testType.equalsIgnoreCase("Web")) {
			// add chrome options
			if (platformName.equalsIgnoreCase("chrome"))
			{
				setChromeOptions();
				getChromeOptions().addArguments("--incognito");
				setWebDriver(getChromeOptions());
			} else if(platformName.equalsIgnoreCase("fireFox"))
			{
				setFireFoxOptions();
				getFirefoxOptions().addArguments("-private");
				setWebDriver(getFirefoxOptions());
			}

			//setWebDriver(platformName);
			context.setAttribute("WebDriver", getWebDriver());

		} else if (testType.equals("Mobile"))

		{
			context.setAttribute("Platform", getPlatformName());
			AppiumDriver<MobileElement> driver;

			try {
				DesiredCapabilities cap = new DesiredCapabilities();
				cap.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
				cap.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
				setDeviceName(deviceName);
				System.out.println("Inside Switch");

				switch (platformName) {

				case "Android":
					System.out.println("Inside Android");
					cap.setCapability("automationName", getProperties().getProperty("androidAutomationName"));
					cap.setCapability("app", System.getProperty("user.dir") + getProperties().getProperty("apkPath"));
					cap.setCapability("appPackage", getProperties().getProperty("appPackage"));
					cap.setCapability("appActivity", getProperties().getProperty("appActivity"));
					cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
					if (emulator.equalsIgnoreCase("true")) {
						cap.setCapability("avd", deviceName);
						cap.setCapability("avdLaunchTimeout", 120000);
					}
					cap.setCapability("systemPort", systemPort);
					cap.setCapability("chromeDriverPort", chromeDriverPort);
					cap.setCapability(MobileCapabilityType.NO_RESET, true);
					cap.setCapability(MobileCapabilityType.FULL_RESET, false);
					cap.setCapability("unicodeKeyboard", true);
					cap.setCapability("resetKeyboard", true);
					driver = new AndroidDriver<MobileElement>(new URL(getProperties().getProperty("hubURL")), cap);
					break;
				case "iOS":
					cap.setCapability("automationName", getProperties().getProperty("iOSAutomationName"));
					cap.setCapability("usePrebuiltWDA", "true");
					cap.setCapability("derivedDataPath",
							"/Users/jaspreetchagger/Library/Developer/Xcode/DerivedData/WebDriverAgent-ewatixzcpuknkndrtzdvtgavzoli");
					cap.setCapability("bundleId", getProperties().getProperty("iOSBundleId"));//
					cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
					cap.setCapability("app",
							"/Users/jaspreetchagger/Library/Developer/Xcode/DerivedData/CrimsonApp-daairteyekdkfuabyfguidkiyjwc/Build/Products/Debug-iphonesimulator/CrimsonApp.app");
					cap.setCapability("toggleSoftwareKeyboard", "true");
					cap.setCapability(MobileCapabilityType.NO_RESET, true);
					cap.setCapability(MobileCapabilityType.FULL_RESET, false);

					driver = new IOSDriver<MobileElement>(new URL(getProperties().getProperty("hubURL")), cap);
					break;
				default:
					throw new Exception("InvalidPlatformName: " + platformName);
				}
				setMobileDriver(driver);

			} catch (Exception e) {
				e.printStackTrace();
			}
			getMobileDriver().manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			context.setAttribute("AppuimDriver", getMobileDriver());
			context.setAttribute("Device", getDeviceName());
		}
	}
	@Parameters({"testType"})
	@BeforeClass()
	public void beforeClass(String testType) throws IOException {
		
		
		
		
		
		
	}

	@BeforeMethod()
	public void beforeMethod() {
		setSoftAssert();
		if (getTestType().equals("Mobile")) {
			getMobileDriver().launchApp();
		} else if (getTestType().equals("Web")) {
			prepareBrowser();
		}
	}

	@AfterMethod()
	public void afterMethod() throws Exception {

		if (getTestType().equals("Mobile")) {
			getMobileDriver().closeApp();
		}
		getSoftAssert().assertAll();
	}

	@AfterTest()
	public void afterTest() {

		if (getTestType().equals("Mobile")) {
			if (getMobileDriver() != null)
				getMobileDriver().quit();
		} else if (getTestType().equals("Web")) {
			if (getWebDriver() != null)
			{
				getWebDriver().quit();
				}
		}

	}

	private void prepareBrowser() {

		setWebDriverWait();
		getWebDriver().manage().window().maximize();
		getWebDriver().manage().deleteAllCookies();
		getWebDriver().manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
		getWebDriver().manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		getWebDriver().get(getProperties().getProperty("webUrl"));

	}

}

package utils;


import java.net.URL;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import commonuseractions.CommonActionMethods;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * The class invokes the requested browser
 *
 * @author aroon
 *
 */
public final class Browserfactory extends CommonActionMethods{

	private Browserfactory() {}
	static String hubUrl = "https://arunsachin.m:t55RwNbMOGuB11M8gFUyh7Z5n6jzBy2Rp5o1T0YATdsMte2NDd@hub.lambdatest.com/wd/hub";
	private static WebDriver driver;
	/**
	 * The method will invoke the requested browser in the requested browsertype
	 *
	 * @param browser
	 * @param browsertype
	 * @return
	 * @throws Exception
	 */
	public synchronized static WebDriver createBrowser(String browser, String browsertype) throws Exception {
		// To lauunch the requested browser in the requested type
		switch (browser.toUpperCase()) {
		// To launch Chrome.
		case "CHROME":
			if (browsertype.equalsIgnoreCase("headless")) {
				WebDriverManager.chromedriver().setup();
				ChromeOptions opt = new ChromeOptions();
				opt.addArguments("--" + browsertype);
				driver = new RemoteWebDriver(opt);
				break;
			} else {
//				WebDriverManager.chromedriver().setup();
				ChromeOptions browserOptions = new ChromeOptions();
				browserOptions.setPlatformName("Windows 10");
				browserOptions.setBrowserVersion("109.0");
				HashMap<String, Object> ltOptions = new HashMap<String, Object>();
				ltOptions.put("username", "arunsachin.m");
				ltOptions.put("accessKey", "t55RwNbMOGuB11M8gFUyh7Z5n6jzBy2Rp5o1T0YATdsMte2NDd");
				ltOptions.put("project", "Untitled");
				ltOptions.put("w3c", true);
				ltOptions.put("plugin", "java-testNG");
				ltOptions.put("selenium_version", "4.7.0");
				browserOptions.setCapability("LT:Options", ltOptions);
//				ChromeOptions opt = new ChromeOptions();
//				opt.addArguments("--remote-allow-origins=*");
				driver = new RemoteWebDriver(new URL(hubUrl) ,browserOptions);
//				driver = new ChromeDriver(opt);
				break;
			}
			// To launch Fire fox
		case "FIREFOX":
			if (browsertype.equalsIgnoreCase("headless")) {
				WebDriverManager.firefoxdriver().setup();
				FirefoxOptions opt = new FirefoxOptions();
				opt.addArguments("--" + browsertype);
				driver = new FirefoxDriver();
			} else {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
			}
			break;
		// To lauch Safari.
		case "SAFARI":
			if (browsertype.equalsIgnoreCase("headless")) {
				WebDriverManager.safaridriver().setup();
				driver = new SafariDriver();
				logMessage("Headless not supported in safari");
			} else {
				WebDriverManager.safaridriver().setup();
				driver = new SafariDriver();
			}
			break;
		default:
			logMessage("Browser not found");

		}
		return driver;
	}
}

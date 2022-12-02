package commonuseractions;

import utils.Browserfactory;
import utils.DriverFactory;
import utils.ExcelReader;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.PropertyConfigurator;
import org.json.JSONObject;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

import org.apache.log4j.*;

/**
 * @author vbaskar
 * @This Class has all CommonActionMethods
 * 
 *
 */
public class CommonActionMethods extends TestListner {
	protected static  AndroidDriver appiumdriver =null;
	protected static boolean invokeMail = false;
	protected static ThreadLocal<String> URL = new ThreadLocal<String>();
	protected static String testName = null;
	public static ExtentReports extentreport;
	public static ExtentHtmlReporter HtmlReporter;
	public static ExtentTest testcase;
	public static String configFilename = "log4j.properties";
	public static Logger log = LogManager.getLogger(CommonActionMethods.class);
	public static ThreadLocal<Map<String, String>> inputdata = ThreadLocal.withInitial(() -> {
		Map<String, String> map = new HashMap<>();
		return map;
	});

	public static Map<String, String> getInputData() {
		return inputdata.get();
	}

	/**
	 * @This method call the pagename,author,category
	 * @param message
	 * @param author
	 * @param category
	 */
	public static void extent(String message, String author, String category) {
		testcase = extentreport.createTest(message).assignAuthor(author).assignCategory(category);
	}

	/**
	 * @This method is used to call the extend report
	 * @param name
	 */
	public static void extentReport(String name) {
		extentreport = new ExtentReports();
		HtmlReporter = new ExtentHtmlReporter(name);
		HtmlReporter.config().setTheme(Theme.DARK);
		extentreport.attachReporter(HtmlReporter);
	}

	/**
	 * 
	 * @This method is used to print the log message in console
	 * @param message -string value about the action being performed
	 */
	public static void logMessage(String message) {
		log.info(message);
		if (extentreport != null) {
			testcase.log(Status.PASS, message);
		}
	}

	/**
	 * @This method is used to print the log error message in console and stop the
	 *       execution
	 * @param MessageStopExecution -string value about the action being performed
	 * @throws Exception
	 */
	public synchronized static void logErrorMessage(String MessageStopExecution) throws Exception {
		log.error(MessageStopExecution);
		String shot = takeSnapShot();
		System.out.println(shot);
		if (invokeMail) {
			FailedScreenShotdestination.set(shot);
			scenarioComments.set(MessageStopExecution);
			scenarioDescription.set(getdata("Scenario"));
			scenarioNo.set(getdata("Number"));
			scenarioStatus.set("Failed");
		}
		if (extentreport != null) {
			testcase.log(Status.FAIL, MessageStopExecution);
			testcase.addScreenCaptureFromPath(shot);
		}
		throw new RuntimeException(MessageStopExecution);
	}

	/**
	 * @This method is used to invoke the browser
	 * @param browser-string     value about the action being performed
	 * @param browsertype-string value about the action being performed
	 * @param url-string         value about the action being performed
	 * @throws Exception
	 */

	public synchronized void invokeBrowser(String browser, String browsertype, String url) throws Exception {
		PropertyConfigurator.configure(configFilename);
		DriverFactory.setDriver(Browserfactory.createBrowser(browser, browsertype));
		DriverFactory.getDriver().manage().window().maximize();
		DriverFactory.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		DriverFactory.getDriver().get(url);
	}

	/**
	 *
	 * @This method is for click the element
	 * @param element -Webelement to click
	 * @param button  -string value about the action being performed
	 * @throws Exception
	 */
	public static void clickMethod(WebElement element, String button) throws Exception {
		try {
			element.click();
			logMessage(button + " button is clicked  ");
		} catch (Exception e) {
			e.printStackTrace();
			logErrorMessage(button + " button is not clicked ");
		}
	}

	/**
	 * @This method is for enter the value
	 * @param key   -Webelement of the textbox to send the text
	 * @param enter -string value about the action being performed
	 * @throws Exception
	 * 
	 */
	public static void sendKeysMethod(WebElement key, String enter) throws Exception {
		try {
			key.sendKeys(enter);
			logMessage(enter + " is entered ");
		} catch (Exception e) {
			logErrorMessage(" Element is not entered in " + enter);
		}
	}

	/**
	 * @This method is for selectByVisibleText
	 * @param element     -Webelement to select an option from the dropdown
	 *                    ByVisibleText
	 * @param text-string value about the action being performed
	 * @throws Exception
	 */
	public static void selectByVisibleText(WebElement element, String text) throws Exception {
		try {
			Select sel = new Select(element);
			sel.selectByVisibleText(text);
			logMessage(text + " is selected in dropdown ");
		} catch (Exception e) {
			logErrorMessage(text + " Element is not selected ");
		}
	}

	/**
	 * 
	 * @This method is for selectByValue
	 * @param element-Webelement to select an option from the dropdown ByValue
	 * @param text-string        value about the action being performed
	 * @throws Exception
	 */
	public static void selectByValue(WebElement element, String text) throws Exception {
		try {
			Select sel = new Select(element);
			sel.selectByValue(text);
			logMessage(text + " is selected in dropdown ");
		} catch (Exception e) {
			logErrorMessage(text + " Element is not selected ");
		}
	}

	/**
	 * 
	 * @This method is for selectByIndex
	 * @param element-Webelement to select an option from the dropdown ByIndex
	 * @param Index-string       value about the action being performed
	 * @throws Exception
	 */
	public static void selectByIndex(WebElement element, int Index) throws Exception {
		try {
			Select sel = new Select(element);
			sel.selectByIndex(Index);
			logMessage(Index + " is selected in dropdown ");
		} catch (Exception e) {
			logErrorMessage(Index + " Element is not selected ");
		}
	}

	/**
	 * @return
	 * @This method is used to take a screenshot
	 * @throws Exception
	 */
	public static String takeSnapShot() throws Exception {
		try {
			File SrcFile = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.FILE);
			File filepath = new File("./Snaps/" + System.currentTimeMillis() + ".png");
			String pathlocation = filepath.getAbsolutePath();
			FileUtils.copyFile(SrcFile, filepath);
			logMessage(" Screenshot taken-stored in the given path ");
			return pathlocation;
		} catch (Exception e) {
			System.err.println(" Screenshot is not taken ");
		}
		return null;
	}

	/**
	 * @This method is used for windowhandle
	 * @throws Exception
	 * 
	 */
	public static void windowHandle() throws Exception {
		try {
			String hand = DriverFactory.getDriver().getWindowHandle();
			List<String> wind = (List<String>) DriverFactory.getDriver().getWindowHandles();
			for (String window : wind) {
				if (!window.equalsIgnoreCase(hand)) {
					DriverFactory.getDriver().switchTo().window(window);
				}
			}
			logMessage(" windowhandle is successful ");
		} catch (Exception e) {
			logErrorMessage(" windowhandle is not successful ");
		}
	}

	/**
	 * 
	 * @This method is used for frameByElement
	 * @param element -Webelement of the frame to switch the driver
	 * @throws Exception
	 */
	public static void frameByElement(WebElement element) throws Exception {
		try {
			DriverFactory.getDriver().switchTo().frame(element);
			logMessage(" framehandle is successful by webelement ");
		} catch (Exception e) {
			logErrorMessage(" no such frame exception ");
		}
	}

	/**
	 *
	 * @This method is used for frameByIndex
	 * @param Index -Integer index of the frame
	 * @throws Exception
	 */
	public static void frameByIndex(int Index) throws Exception {
		try {
			DriverFactory.getDriver().switchTo().frame(Index);
			logMessage(" framehandle is successful by index ");
		} catch (Exception e) {
			logErrorMessage(" no such frame exception ");
		}
	}

	/**
	 *
	 * @This method is used for frameByNameorID
	 * @param nameORid-string value about the action being performed
	 * @throws Exception
	 */
	public static void frameByNameorID(String nameORid) throws Exception {
		try {
			DriverFactory.getDriver().switchTo().frame(nameORid);
			logMessage(" framehandle is successful by name or id ");
		} catch (Exception e) {
			logErrorMessage(" no such frame exception ");
		}
	}

	/**
	 * @This method is used for default window
	 * @throws Exception
	 * 
	 */
	public static void defaultwindow() throws Exception {
		try {
			DriverFactory.getDriver().switchTo().defaultContent();
			logMessage(" Switched to defaultwindow ");
		} catch (Exception e) {
			logErrorMessage(" Not switched to defaultwindow ");
		}
	}

	/**
	 * @This method is for get current page title
	 * @return
	 */
	public static String getTitle() {
		String title = DriverFactory.getDriver().getTitle();
		logMessage(title);
		return title;
	}

	/**
	 * @This method is for get a current url
	 * @return
	 */
	public static String getURL() {
		String url = DriverFactory.getDriver().getCurrentUrl();
		logMessage(url);
		return url;
	}

	/**
	 * 
	 * @This method is for element is displayed
	 * @param element     -WebElement to check whether is displayed or not
	 * @param ElementName
	 * @throws Exception
	 */
	public static void isDisplayed(WebElement element, String ElementName) throws Exception {
		try {
			if (element.isDisplayed()) {
				logMessage(ElementName + " is displayed ");
			} else {
				logErrorMessage(ElementName + " is not displayed in else block ");
			}
		} catch (Exception e) {
			logErrorMessage(ElementName + " is not displayed in catch block ");
		}
	}

	/**
	 *
	 * @This method is for element is selected
	 * @param element     -WebElement to check whether is Selected or not
	 * @param ElementName -string value about the action being performed
	 * @throws Exception
	 */
	public static void isSelected(WebElement element, String ElementName) throws Exception {
		if (element.isSelected()) {
			logMessage(ElementName + " is selected");
		} else {
			logErrorMessage(ElementName + " is not selected ");
		}
	}

	/**
	 * @This method is for element is enabled
	 * @param element     -WebElement to check whether is Enabled or not
	 * @param ElementName -string value about the action being performed
	 * @throws Exception
	 */
	public static void isEnabled(WebElement element, String ElementName) throws Exception {
		try {
			if (element.isEnabled()) {
				logMessage(ElementName + " is enabled ");
			} else {
				logErrorMessage(ElementName + " is not enabled in else block ");
			}
		} catch (Exception e) {
			logErrorMessage(ElementName + " is not enabled in catch block ");
		}
	}

	/**
	 * @This method is used to check the variable are equal
	 * @param intial-object   value about the action being performed
	 * @param end-object      value about the action being performed
	 * @param obj1name-string value about the action being performed
	 * @param obj2name-string value about the action being performed
	 * @throws Exception
	 */
	public static void checkEquality(Object intial, Object end) throws Exception {
		if (String.valueOf(intial).contains(String.valueOf(end))) {
			logMessage(intial + " & " + end + " is equal");
		} else {
			logErrorMessage(intial + " & " + end + " is not equal");
		}
	}

	/**
	 * This method for getting the data from the hash map and returns the value
	 * 
	 * @param Name It is the name of the column
	 * @return
	 * @throws Exception
	 */
	public synchronized static String getdata(String Name) throws Exception {
		String data = "";
		if (inputdata.get().containsKey(Name)) {
			data = inputdata.get().get(Name);
		} else {
			logErrorMessage(" Given Column name is not available in the Excel " + Name);
		}
		return data;
	}

	/**
	 * This method is to click the respective element by its text from the list of
	 * webelements.
	 * 
	 * @param listelement
	 * @param Toselect
	 * @return
	 * @throws Exception
	 */
	public static void listDrop(List<WebElement> listelement, String Toselect) throws Exception {
		boolean flag = true;
		for (WebElement element : listelement) {
			//webWait(element);
			String name = element.getText();
			if (name.contains(Toselect)) {
				flag = false;
				clickMethod(element, Toselect);
				logMessage(Toselect + "  is clicked");
				break;
			}
		}
		if (flag) {
			logErrorMessage(" No such String to click ");
		}
	}

	/**
	 * This method is to split the given given string by comma.
	 * 
	 * @param data
	 * @return
	 */
	public static String[] splitString(String data, String symbol) {
		String arr[] = data.split(symbol);
		return arr;
	}

	public static void scrollToElement(WebElement ele) {
		JavascriptExecutor scrl = (JavascriptExecutor) DriverFactory.getDriver();
		scrl.executeScript("arguments[0].scrollIntoView(true)", ele);
	}

	/**
	 * This method is to get the text data from excel
	 * 
	 * @param sheetname
	 * @return
	 * @throws Exception
	 */
	public static synchronized Iterator<Object[]> getTestData(String sheetname) throws Exception {
		ExcelReader xlRead = null;
		int xlRowCount = 0;
		xlRead = new ExcelReader("database.xlsx", sheetname);
		xlRowCount = xlRead.getRowCount();
		ArrayList<Object[]> data = new ArrayList<Object[]>();
		for (int i = 1; i < xlRowCount; i++) {
			data.add(new Object[] { xlRead.xlReader(i) });
		}
		return data.iterator();
	}

	/**
	 * This method is to get text of the element
	 * 
	 * @param element
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public static String getTextElement(WebElement element, String name) throws Exception {
		String text = "";
		try {
			text = element.getText();
			logMessage(text+" is displayed");
		} catch (Exception e) {
			logErrorMessage(" The object  " + name + " is not displayed");
		}
		return text;
	}

	/**
	 * This method waits for the given element until it is clickable
	 * 
	 * @param ele
	 * @throws Exception
	 */
	public static void webWait(WebElement ele) throws Exception {
		try {
			WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(15));
			wait.until(ExpectedConditions.elementToBeClickable(ele));
		} catch (Exception e) {
			logErrorMessage(" Element not clickable time out waiting for element to be clickable ");
		}
	}

	/**
	 * This methods waits until the element is visible.
	 * 
	 * @param ele
	 * @throws Exception 
	 */
	public static void webwaitVisibility(WebElement ele) throws Exception {
		try {
			WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(15));
			wait.until(ExpectedConditions.visibilityOf(ele));
		} catch (Exception e) {
			logErrorMessage(" Element not clickable time out waiting for element to be clickable ");
		}
	}

	/**
	 * This method deletes every sub-files inside the given directory
	 * 
	 * @param file
	 */
	public static void deleteFolder(File file) {
		for (File subFile : file.listFiles()) {
			if (subFile.isDirectory()) {
				deleteFolder(subFile);
			} else {
				subFile.delete();
			}
		}
		file.delete();
	}

	/**
	 * @This method is used to capitalize the string case provided
	 * 
	 * @param str - String to be capitalized
	 * @return string
	 * 
	 */
	public static String capitalize(String str) {
		if (str == null || str.isEmpty()) {
			return str;
		}
		return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}

	/**
	 * 
	 * @This method is to convert the text response into JSON
	 * 
	 * @param textString
	 * @return JSONObject
	 * 
	 */
	public static JSONObject restConvertTextAsJson(String textString) {
		return new JSONObject(textString);
	}

	/**
	 * 
	 * @This method is to get the correlate parameter value from the array object by
	 *       giving rest response object as input
	 * 
	 * @param response
	 * @param jsonPath
	 * @return jsonString
	 * 
	 */
	public static String restCorrelateJSON(String jsonString, String jsonPath) {

		boolean flag = true;
		JSONObject jsonObj = null;
		Iterator<String> jsonItr = null;

		String[] jsonPathSplit = jsonPath.split(Pattern.quote("."));

		for (String matchKey : jsonPathSplit) {

			if (matchKey.contains("[")) {
				jsonObj = restConvertTextAsJson(jsonString);
				int strLen = matchKey.length();
				jsonString = jsonObj.getJSONArray(matchKey.replaceAll("\\[\\d\\]", ""))
						.getJSONObject(Integer.parseInt(matchKey.substring(strLen - 2, strLen - 1))).toString();
			}

			jsonObj = restConvertTextAsJson(jsonString);
			jsonItr = jsonObj.keys();

			while (jsonItr.hasNext()) {
				String keyvalue = jsonItr.next().toString();
				if (keyvalue.equals(matchKey)) {
					jsonString = jsonObj.get(keyvalue).toString();
					flag = false;
					break;
				}
			}
		}

		if (flag) {
			System.err.println("No value found");
		}

		return jsonString;

	}

	/**
	 * @method returns the requested date from the curent date in the format MMMMMMMMMM/d/yyyy
	 * @param plusdays
	 * @return
	 */
	public String currentDate(int plusdays, String format) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, +plusdays);
		SimpleDateFormat date = new SimpleDateFormat();
		date.applyPattern("MMMMMMMMMM/d/yyyy");
		String dat = date.format(cal.getTime());
		return dat;
	}
}
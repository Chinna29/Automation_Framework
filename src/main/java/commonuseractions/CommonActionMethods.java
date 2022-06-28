package commonuseractions;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class CommonActionMethods {

	static WebDriver driver;

	/**
	 * @author vbaskar
	 * @This method is used to print the log message in console
	 */
	public static Logger logger1 = Logger.getLogger(CommonActionMethods.class);

	/**
	 * 
	 * @param message
	 */
	public static void logMessage(String message) {
		logger1.info(message);
	}

	/**
	 * @This method is used to print the log error message in console
	 * @param MessageStopExecution
	 */

	public static void logErrorMessage(String MessageStopExecution) {

		logger1.error(MessageStopExecution);

	}

	/**
	 * @author vbaskar
	 * @This method is for click the elements
	 * @param elements
	 * @param button
	 */
	public static void clickMethod(WebElement elements, String button) {
		try {

			elements.click();
			logMessage(button + " button is clicked  ");

		} catch (Exception e) {

			logErrorMessage(button + " button is not clicked ");

		}
	}

	/**
	 * @author vbaskar
	 * @This method is for enter the value
	 * @param keys
	 * @param enter
	 * 
	 */
	public static void sendKeysMethod(WebElement keys, String enter) {
		try {
			keys.click();
			keys.sendKeys(enter);
			logMessage(enter + "enter the value ");

		} catch (Exception e) {
			logErrorMessage("Element not entered in" + enter);

		}

	}

	/**
	 * @author vbaskar
	 * @This method is for select the dropdown class
	 * @param element
	 * @param text
	 */

	public static void selectClass(WebElement element, String text) {
		try {
			Select sel = new Select(element);
			sel.selectByVisibleText(text);
			logMessage(text + "is selected in dropdown");

		} catch (Exception e) {
			logErrorMessage(text + "Element not selected");

		}

	}

	/**
	 * @This method is used to take a screenshot
	 * @param Snaps
	 */

	public static void screenShot(String Snaps) {
		try {

			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

			File dest = new File("./snaps/img.png");

			FileUtils.copyFile(screenshot, dest);
			logMessage(Snaps + "snap is taken ");

		} catch (Exception e) {

			logErrorMessage(Snaps + "snap is not taken ");

		}

	}
	/**
	 * @This method is used for windowhandle
	 */
	public static void windowHandle() {
		try {
			String hand=driver.getWindowHandle();
			List<String> wind = (List<String>) driver.getWindowHandles();
			for (String window:wind) {
				if(!window.equalsIgnoreCase(hand)) {
					driver.switchTo().window(window);
				}
			}
			
		} catch (Exception e) {
			
		}
		
	}
	/**
	 * @This method is used for frame windowhandle 
	 * @param iframe
	 */
	
	public static void frameHandle(WebElement iframe) {
		
		driver.switchTo().frame(iframe);
		
	}
	/**
	 * @This method is used for defaultwindow
	 */
	public static void defaultwindow() {
		driver.switchTo().defaultContent();
	}
	
	
	
	

}

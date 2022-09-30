package pageobjects.swaglabs;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import commonuseractions.CommonActionMethods;
import utils.DriverFactory;
/**
 * 
 * @author svenkateshwaran
 * @this class is for methods in confirmation page
 */
public class Confirmation extends CommonActionMethods {

	

	@FindBy(id = "finish")
	WebElement finishButton;

	@FindBy(className = "complete-header")
	WebElement confirmationMsg;
	
	public Confirmation() {
		PageFactory.initElements(DriverFactory.getDriver(), this);
	}

	public void clickOnFinishButton() throws Exception {
		clickMethod(finishButton, "finish");

	}
	/**
	 * @this method is used for validation of the order confirmation
	 * @throws Exception
	 */

	public void verifyOrderConfirmation() throws Exception {
		isDisplayed(confirmationMsg, "THANK YOU FOR YOUR ORDER");
	}

}

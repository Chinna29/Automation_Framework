package pageobjects.swaglabs;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import commonuseractions.CommonActionMethods;
import utils.DriverFactory;

public class Confirmation extends CommonActionMethods {
	
	
	public Confirmation()
	{
		PageFactory.initElements(DriverFactory.getDriver(), this);
	}
	
	@FindBy(id="finish")
	private static WebElement finishButton;
	
	@FindBy (className="complete-header")
	private static WebElement confirmationMsg;
	
	
	public void clickOnFinishButton() throws Exception
	{
		clickMethod(finishButton, "finish");
		logMessage("Finish button is clicked");
	}
	
	public void verifyOrderConfirmation() throws Exception
	{
		isDisplayed(confirmationMsg, "THANK YOU FOR YOUR ORDER");
	}
	
	
	
	

}

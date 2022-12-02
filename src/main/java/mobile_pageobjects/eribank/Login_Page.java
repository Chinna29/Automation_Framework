package mobile_pageobjects.eribank;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.PageFactory;

import commonuseractions.CommonActionMethods;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class Login_Page extends CommonActionMethods {

	static String dollar = null;

	@AndroidFindBy(id = "android:id/button1")
	@CacheLookup
	WebElement popup;

	@AndroidFindBy(id = "com.experitest.ExperiBank:id/usernameTextField")
	@CacheLookup
	WebElement user;

	@AndroidFindBy(id = "com.experitest.ExperiBank:id/passwordTextField")
	@CacheLookup
	WebElement pass;

	@AndroidFindBy(id = "com.experitest.ExperiBank:id/loginButton")
	@CacheLookup
	WebElement sigin;

	@AndroidFindBy(id = "com.experitest.ExperiBank:id/makePaymentButton")
	@CacheLookup
	WebElement makepayment;

	@AndroidFindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout[2]/android.widget.LinearLayout/android.webkit.WebView/android.webkit.WebView/android.view.View")
	@CacheLookup
	WebElement totalamount;

	public Login_Page() throws Exception {
		try {
			PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void login() throws Exception {
		Thread.sleep(5000);
		clickMethod(popup, "popup");
		sendKeysMethod(user, "company");
		sendKeysMethod(pass, "company");
		clickMethod(sigin, "sigin");
		webWait(totalamount);
		dollar = getTextElement(totalamount, "totalamount").replace("Your balance is: ", "").replace("$", "");
		clickMethod(makepayment, "makepayment");

	}

}

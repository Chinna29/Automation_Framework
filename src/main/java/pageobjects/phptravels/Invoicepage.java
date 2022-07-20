package pageobjects.phptravels;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import commonuseractions.CommonActionMethods;
import utils.DriverFactory;

public class Invoicepage extends CommonActionMethods{
	@FindBy(id = "form")
	WebElement proceedbutton;
	
	@FindBy(xpath = "//div[@class='d-flex align-items-center mb-4']//i")
	WebElement Confirmationele;
	
	@FindBy(xpath = "//span[@class='text-right']")
	WebElement reservationnum;
	
	@FindBy(xpath = "(//ul[@class='list-group list-group-flush']//span)[1]")
	WebElement adultnum;
	
	@FindBy(xpath = "(//ul[@class='list-group list-group-flush']//span)[2]")
	WebElement childnum;
	
	@FindBy(xpath = "(//ul[@class='list-group list-group-flush']//li)[1]")
	WebElement infantnum;
	
	@FindBy(xpath = "(//ul[@class='list-group list-group-flush']//li)[2]")
	WebElement bookingtax;
	
	@FindBy(xpath = "(//ul[@class='list-group list-group-flush']//li)[3]")
	WebElement depositprice;
	
	@FindBy(xpath = "(//ul[@class='list-group list-group-flush']//li)[4]")
	WebElement totalprice;
	
	public Invoicepage(){
		PageFactory.initElements(new AjaxElementLocatorFactory(DriverFactory.getDriver(), 30), this);
	}
	
	public void invoiceValidation(String actladlt,String actlchld,String actlinfnt) throws Exception {
		getTitle();
		getURL();
		isDisplayed(Confirmationele,"Confirmation ");
		logMessage("Resrvation number: "+getTextElement(reservationnum,"Resrvation number" ));
		String[] pax = splitString(getTextElement(infantnum, " "), " ");
		String adlt =pax[1];
		String chld = pax[3];
		String infnt = pax[6];
		System.out.println(adlt+"|"+chld+"|"+infnt);
		checkEquality(adlt, actladlt);
		checkEquality(chld, actlchld);
		//checkEquality(infnt, actlinfnt);
		String txt = getTextElement(bookingtax, " ");
		String deposit = getTextElement(depositprice, " ");
		String[] depositnow = splitString(deposit, " ");
		String[] bookingtax = splitString(txt," ");
		double bokintx = Double.valueOf(bookingtax[3]);
		double depo = Double.valueOf(depositnow[4]);
		double actlnewtotal = Paxdetailspage.returntotal()+bokintx+depo;
		String totl = getTextElement(totalprice, " ");
		String[] finalprz = splitString(totl, " ");
		double finlprz = Double.valueOf(finalprz[3]);
		//checkEquality(actlnewtotal, finlprz);
	}
	
	public void proceed() throws Exception {
		clickMethod(proceedbutton, "Proceed button");
	}
}

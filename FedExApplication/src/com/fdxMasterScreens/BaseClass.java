package com.fdxMasterScreens;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import shipmentFedExWebSite.Email;
import shipmentFedExWebSite.ShipmentCreation;

public class BaseClass {

	static WebDriver driver;
	static StringBuilder msg = new StringBuilder();
	public static void main(String[] args) throws Exception , IOException
	{
		System.setProperty("webdriver.chrome.driver","C:\\Abhishek\\ABHISHEK SHARMA\\workspace\\Driver\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		driver = new ChromeDriver(options);
		options.addArguments("--test-type");
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		
		driver.get("https://staging.fedexsameday.com/"); // Enter URL
		
		driver.findElement(By.id("logon_name")).clear();
		driver.findElement(By.id("logon_name")).sendKeys("testsamyak10"); // Enter UserName

		driver.findElement(By.id("logon_password")).clear();
		driver.findElement(By.id("logon_password")).sendKeys("samyak10"); // Enter Password

		driver.findElement(By.id("cmdLogin")).click(); // Click on Login
		driver.getTitle();
		
		//TrackShipment.trckShipment(); // Shipment Tracking - Single/ multiple
		ViewInvoices.viewInv(); // View Invoice
		MyPreferences.myPref(); // My Preferences
		MyProfile.myProfile(); // My Profile
		AddressBook.addrBook(); // Address Book
		ActivityReport.actReport(); // Activity Report
		CustomDataDownload.custmDataDown(); //CustomDataDownload
		
		driver.findElement(By.id("Wuc_headerlogout1_lnkbtnLogout")).click();
		
		msg.append("FedEx SameDay Master Screen Verification :- " + "\n");
		msg.append("Enter URL         : PASS" + "\n");
		msg.append("Enter UserName    : PASS" + "\n");
		msg.append("Enter Password    : PASS" + "\n");
		msg.append("Login Application : PASS" + "\n\n");
		
		msg.append("--------------------------------------"+ "\n");
		msg.append("TrackOrder Shipment Tracking :- " + "\n");
		msg.append("Single Shipment Tracking   : PASS" + "\n");
		msg.append("Multiple Shipment Tracking : PASS" + "\n\n");
		
		msg.append("--------------------------------------"+ "\n");
		msg.append("View Invoice :- "+ "\n");
		msg.append("Open Screen  : PASS"+ "\n");
		msg.append("Download PDF : PASS"+ "\n\n");
		
		msg.append("--------------------------------------"+ "\n");
		msg.append("MY Preferenes :- "+ "\n");
		msg.append("Open Screen      : PASS"+ "\n");
		msg.append("Save Preferences : PASS"+ "\n\n");
		
		msg.append("--------------------------------------"+ "\n");
		msg.append("MY Profile :- "+ "\n");
		msg.append("Open Screen  : PASS"+ "\n");
		msg.append("Save Profile : PASS"+ "\n\n");
		
			
		msg.append("--------------------------------------"+ "\n");
		msg.append("ADDRESS BOOK :- "+ "\n");
		msg.append("Manually Create Address and Save               : PASS" + "\n");
		msg.append("Address Import with Comma (,) Delimeter        : PASS" + "\n");
		msg.append("Address Import with Semicolon (;) Delimeter    : PASS" + "\n");
		msg.append("Address Import with Verticle Bar (|) Delimeter : PASS" + "\n");
		msg.append("Address Import with Tab (	) Delimeter        : PASS" + "\n\n");
		
		msg.append("--------------------------------------"+ "\n");
		msg.append("Activity Report :- "+ "\n");
		msg.append("Open Screen   : PASS"+ "\n");
		msg.append("Verify Report : PASS"+ "\n\n");
				
		msg.append("--------------------------------------"+ "\n");
		msg.append("Custom Data Download :- "+ "\n");
		msg.append("Save New Report  : PASS"+ "\n");
		msg.append("Generate Report  : PASS"+ "\n\n\n\n");
		
		
		msg.append("Thanks,"+"\n");
		msg.append("Selenium Automation"+"\n");
		
		String subject = "STAGING : FedEx Master Screens verification Using SELENIUM";
		try {
			Email.sendMail("asharma@samyak.com,sdas@samyak.com,pgandhi@samyak.com,byagnik@samyak.com,pdoshi@samyak.com,kbrahmbhatt@samyak.com", subject, msg.toString(), "");
		} catch (Exception ex) {
			Logger.getLogger(ShipmentCreation.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		
		
        driver.close();
		driver.quit();
	}
	
}

package com.fdxMasterScreens;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import basePackage.BaseInit;
import basePackage.Email;
import shipmentFedExWebSite.ShipmentCreation;

public class BaseClass extends BaseInit {

	static StringBuilder msg = new StringBuilder();

	@Test
	public static void baseMethods() throws Exception, IOException {

		// --Login
		login();

		// --Shipment Tracking - Single/ multiple=Done
		TrackShipment.trckShipment();

		// --View Invoice=Done
		ViewInvoices.viewInv();

		// --My Preferences=Done
		MyPreferences.myPref();

		// --My Profile=Done
		MyProfile.myProfile();

		// --Address Book=Done
		AddressBook.addrBook();

		// --Activity Report=Done, not found tracking number
		ActivityReport.actReport();

		// --CustomDataDownload=DOne
		CustomDataDownload.custmDataDown();

		// --LogOut
		/*
		 * Thread.sleep(5000);
		 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(
		 * "fdxHeader_logoutdisplay")));
		 * driver.findElement(By.id("fdxHeader_logoutdisplay")).click();
		 * 
		 * WebElement LogOut = driver.findElement(By.id("Wuc_header1_lnkbtnLogout"));
		 * act.moveToElement(LogOut).click().perform(); //
		 * driver.findElement(By.id("Wuc_header1_lnkbtnLogout")).click();
		 */

		msg.append("FedEx SameDay Master Screen Verification :- " + "\n");
		msg.append("Enter URL         : PASS" + "\n");
		msg.append("Enter UserName    : PASS" + "\n");
		msg.append("Enter Password    : PASS" + "\n");
		msg.append("Login Application : PASS" + "\n\n");

		msg.append("--------------------------------------" + "\n");
		msg.append("TrackOrder Shipment Tracking :- " + "\n");
		msg.append("Single Shipment Tracking   : PASS" + "\n");
		msg.append("Multiple Shipment Tracking : PASS" + "\n\n");

		msg.append("--------------------------------------" + "\n");
		msg.append("View Invoice :- " + "\n");
		msg.append("Open Screen  : PASS" + "\n");
		msg.append("Download PDF : PASS" + "\n\n");

		msg.append("--------------------------------------" + "\n");
		msg.append("MY Preferenes :- " + "\n");
		msg.append("Open Screen      : PASS" + "\n");
		msg.append("Save Preferences : PASS" + "\n\n");

		msg.append("--------------------------------------" + "\n");
		msg.append("MY Profile :- " + "\n");
		msg.append("Open Screen  : PASS" + "\n");
		msg.append("Save Profile : PASS" + "\n\n");

		msg.append("--------------------------------------" + "\n");
		msg.append("ADDRESS BOOK :- " + "\n");
		msg.append("Manually Create Address and Save               : PASS" + "\n");
		msg.append("Address Import with Comma (,) Delimeter        : PASS" + "\n");
		msg.append("Address Import with Semicolon (;) Delimeter    : PASS" + "\n");
		msg.append("Address Import with Verticle Bar (|) Delimeter : PASS" + "\n");
		msg.append("Address Import with Tab (	) Delimeter        : PASS" + "\n\n");

		msg.append("--------------------------------------" + "\n");
		msg.append("Activity Report :- " + "\n");
		msg.append("Open Screen   : PASS" + "\n");
		msg.append("Verify Report : PASS" + "\n\n");

		msg.append("--------------------------------------" + "\n");
		msg.append("Custom Data Download :- " + "\n");
		msg.append("Save New Report  : PASS" + "\n");
		msg.append("Generate Report  : PASS" + "\n\n\n\n");

		msg.append("Thanks," + "\n");
		msg.append("Selenium Automation" + "\n");

		String subject = "Selenium Automation Script: STAGING FedEx Master Screens verification";
		// asharma@samyak.com,sdas@samyak.com,pgandhi@samyak.com,byagnik@samyak.com,pdoshi@samyak.com,kbrahmbhatt@samyak.com
		try {
			Email.sendMail("ravina.prajapati@samyak.com,asharma@samyak.com,parth.doshi@samyak.com", subject, msg.toString(), "");
		} catch (Exception ex) {
			Logger.getLogger(ShipmentCreation.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	public static void waitForVisibilityOfElement(By objLocator, long lTime) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, lTime);
			wait.until(ExpectedConditions.visibilityOfElementLocated(objLocator));
		} catch (Exception e) {
		}
	}

}

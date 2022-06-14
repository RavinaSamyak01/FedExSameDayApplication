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
	public void baseMethods() throws Exception, IOException {
		msg.append("FedEx SameDay Master Screen Verification :- " + "\n");

		// --Shipment Tracking - Single/ multiple=Done
		TrackShipment.trckShipment();

		// --View Invoice=Done
		ViewInvoices.viewInv();

		// --My Preferences=Done
		MyPreferences.myPref();

		// --Address Book=Done
		AddressBook.addrBook();

		// --My Profile=Done
		MyProfile.myProfile();

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

		msg.append("Thanks," + "\n");
		msg.append("Selenium Automation" + "\n");

		String Env = storage.getProperty("Env");
		String subject = "Selenium Automation Script: " + Env + " FedEx Master Screens verification";
		// asharma@samyak.com,sdas@samyak.com,pgandhi@samyak.com,byagnik@samyak.com,pdoshi@samyak.com,kbrahmbhatt@samyak.com
		try {
			Email.sendMail("ravina.prajapati@samyak.com,asharma@samyak.com,parth.doshi@samyak.com", subject,
					msg.toString(), "");
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

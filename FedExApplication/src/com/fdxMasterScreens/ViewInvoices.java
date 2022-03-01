package com.fdxMasterScreens;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ViewInvoices extends BaseClass {

	public static void viewInv() throws InterruptedException, IOException {
		driver.findElement(By.linkText("Support")).click();
		waitForVisibilityOfElement(By.linkText("Billing & Invoicing"), 0);
		driver.findElement(By.linkText("Billing & Invoicing")).click();
		waitForVisibilityOfElement(By.id("currentForm"), 0);

		driver.findElement(By.id("dgRpt_lbExport_0")).click();
		Thread.sleep(5000);

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(".\\src\\TestFiles\\InvoiceHistory.png"));
		System.out.println("Invoice History display Proper !!!");
	}

	public static void waitForVisibilityOfElement(By objLocator, long lTime) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, lTime);
			wait.until(ExpectedConditions.visibilityOfElementLocated(objLocator));
		} catch (Exception e) {
		}
	}

	public static void waitForInVisibilityOfElement(By objLocator, long lTime) {

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(objLocator));
	}
}

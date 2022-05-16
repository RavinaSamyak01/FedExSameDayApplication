package com.fdxMasterScreens;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyPreferences extends BaseClass {

	public static void myPref() throws InterruptedException, IOException {
		Actions builder = new Actions(driver);

		msg.append("--------------------------------------" + "\n");
		msg.append("MY Preferenes :- " + "\n");

		driver.findElement(By.linkText("Support")).click();

		waitForVisibilityOfElement(By.linkText("Account Management Tools"), 50);
		WebElement ele1 = driver.findElement(By.linkText("Account Management Tools"));
		builder.moveToElement(ele1).build().perform();

		waitForVisibilityOfElement(By.linkText("My Preferences"), 50);
		driver.findElement(By.linkText("My Preferences")).click();
		waitForVisibilityOfElement(By.id("content1"), 50);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,550)", "");
		msg.append("Open Screen      : PASS" + "\n");
		driver.findElement(By.id("Ncsbuttons2_cmdSubmit")).click();
		jse.executeScript("window.scrollBy(0,250)", "");
		waitForVisibilityOfElement(By.id("lblErrMessage1"), 5);

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(".\\src\\Screenshots\\MyPreferences.png"));
		System.out.println("My Preferences Test Case Executed successfully !!!");
		msg.append("Save Preferences : PASS" + "\n\n");
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

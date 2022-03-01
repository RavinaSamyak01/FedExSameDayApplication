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
		driver.findElement(By.linkText("Support")).click();

		waitForVisibilityOfElement(By.linkText("Account Management Tools"), 5);
		WebElement ele1 = driver.findElement(By.linkText("Account Management Tools"));
		builder.moveToElement(ele1).build().perform();

		driver.findElement(By.linkText("My Preferences")).click();
		waitForVisibilityOfElement(By.id("content1"), 5);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,550)", "");

		driver.findElement(By.id("Ncsbuttons2_cmdSubmit")).click();
		jse.executeScript("window.scrollBy(0,250)", "");
		waitForVisibilityOfElement(By.id("lblErrMessage1"), 5);

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(".\\src\\TestFiles\\MyPreferences.png"));
		System.out.println("My Preferences Test Case Executed successfully !!!");

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
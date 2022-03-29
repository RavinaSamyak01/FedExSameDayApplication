package com.fdxMasterScreens;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CustomDataDownload extends BaseClass {

	public static void custmDataDown() throws Exception {
		Actions builder = new Actions(driver);
		driver.findElement(By.linkText("Support")).click();

		waitForVisibilityOfElement(By.linkText("Run Reports"), 5);
		WebElement ele1 = driver.findElement(By.linkText("Run Reports"));
		builder.moveToElement(ele1).build().perform();

		driver.findElement(By.linkText("Custom Data Download")).click();
		waitForVisibilityOfElement(By.id("currentForm"), 5);

		newReport();
		allColumns();
	}

	public static void newReport() throws Exception {
		driver.findElement(By.id("btnNewReport")).click();
		waitForVisibilityOfElement(By.id("currentForm"), 5);
		driver.findElement(By.id("txtReportName")).clear();
		driver.findElement(By.id("txtReportName")).sendKeys("SeleniumAuto");
		driver.findElement(By.id("txtPUStartDate")).clear();
		driver.findElement(By.id("txtPUStartDate")).sendKeys("02/04/2020");
		driver.findElement(By.id("txtPUEndDate")).clear();
		driver.findElement(By.id("txtPUEndDate")).sendKeys("02/04/2020");

		WebElement acct = driver.findElement(By.id("lbAccount"));
		Select s1 = new Select(acct);
		s1.deselectByVisibleText("(******136) CREATIVE ARTISTS AGENCY");

		Select s2 = new Select(acct);
		s2.selectByVisibleText("(******229) TEST Cheers");

		WebElement fields = driver.findElement(By.id("lbAvailable"));
		Select fld1 = new Select(fields);
		fld1.selectByVisibleText("Account #");
		fld1.selectByVisibleText("Service Type");
		fld1.selectByVisibleText("Pickup #");
		fld1.selectByVisibleText("Pickup Wait Time (CWK) $");
		fld1.selectByVisibleText("Pickup Waiting Time (CWK)");
		fld1.selectByVisibleText("Return Wait Time (CWK) $");
		fld1.selectByVisibleText("Return Waiting Time (CWK)");
		fld1.selectByVisibleText("Delivery Wait Time (CWK) $");
		fld1.selectByVisibleText("Delivery Waiting Time (CWK)");
		fld1.selectByVisibleText("Total Waiting Time (CWK)");
		fld1.selectByVisibleText("Total Wait Time $");

		driver.findElement(By.name("ctl00")).click();
		Thread.sleep(1000);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,-550)", "");

		driver.findElement(By.id("btnSaveReport")).click();
		Thread.sleep(3000);
		System.out.println("New Report Save : PASS");

		driver.findElement(By.id("imgbtnGenerate")).click();
		Thread.sleep(5000);
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(".\\src\\Screenshots\\CustomDataDownload.png"));

		System.out.println("Generate Report !! : PASS");
		driver.findElement(By.id("Back")).click();
	}

	public static void allColumns() throws Exception {
		waitForVisibilityOfElement(By.id("currentForm"), 5);

		driver.findElement(By.id("btnNewReport")).click();
		Thread.sleep(3000);
		driver.findElement(By.id("txtReportName")).clear();
		driver.findElement(By.id("txtReportName")).sendKeys("SeleniumAutomationAllColumn");
		driver.findElement(By.id("txtPUStartDate")).clear();
		driver.findElement(By.id("txtPUStartDate")).sendKeys("02/04/2020");
		driver.findElement(By.id("txtPUEndDate")).clear();
		driver.findElement(By.id("txtPUEndDate")).sendKeys("02/04/2020");

		WebElement acct = driver.findElement(By.id("lbAccount"));
		Select s1 = new Select(acct);
		s1.deselectByVisibleText("(******136) CREATIVE ARTISTS AGENCY");

		Select s2 = new Select(acct);
		s2.selectByVisibleText("(******229) TEST Cheers");

		Thread.sleep(3000);
		WebElement fields = driver.findElement(By.id("lbAvailable"));
		fields.click();
		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_CONTROL);
		r.keyPress(KeyEvent.VK_A);

		Thread.sleep(3000);

		driver.findElement(By.name("ctl00")).click();
		Thread.sleep(1000);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,-550)", "");

		driver.findElement(By.id("btnSaveReport")).click();
		Thread.sleep(3000);
		System.out.println("New Report Save : PASS");

		driver.findElement(By.id("imgbtnGenerate")).click();
		Thread.sleep(5000);
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(".\\src\\Screenshots\\CustomDataDownload_AllColumns.png"));

		System.out.println("Generate Report with All Columns !! : PASS");
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

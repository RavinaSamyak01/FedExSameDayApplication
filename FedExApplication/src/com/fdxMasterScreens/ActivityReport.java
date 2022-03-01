package com.fdxMasterScreens;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import basePackage.BaseInit;

public class ActivityReport extends BaseInit {

	@Test
	public static void actReport() throws IOException, InterruptedException {
		Thread.sleep(2000);
		Actions builder = new Actions(driver);

		driver.findElement(By.linkText("Support")).click();

		waitForVisibilityOfElement(By.linkText("Run Reports"), 5);
		WebElement ele1 = driver.findElement(By.linkText("Run Reports"));
		builder.moveToElement(ele1).build().perform();

		driver.findElement(By.linkText("Activity Report")).click();

		Thread.sleep(2000);
		driver.findElement(By.name("anchor1xx")).click();
		driver.findElement(By.xpath("//*[@id=\"startdate\"]/table/tbody/tr/td/center/table[2]/tbody/tr[8]/td/a"))
				.click();

		driver.findElement(By.name("anchor2xx")).click();
		driver.findElement(By.xpath("//*[@id=\"enddate\"]/table/tbody/tr/td/center/table[2]/tbody/tr[8]/td/a")).click();

		waitForVisibilityOfElement(By.id("ddAcctNum"), 5);

		WebElement el = driver.findElement(By.id("ddAcctNum"));
		Select opt = new Select(el);
		opt.selectByVisibleText("TEST Cheers - # ******889");

		driver.findElement(By.id("cmdRpt")).click();
		waitForVisibilityOfElement(By.id("cmdRpt"), 5);

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(".\\src\\TestFiles\\ActivitySummary.png"));
		System.out.println("Activity Summary Test Case Executed successfully !!!");

		try {
			ExcelDataProvider excelDataProvider = new ExcelDataProvider();
			String tracking = excelDataProvider.getData("Sheet1", 1, 15);
			driver.findElement(By.partialLinkText(tracking)).click();
			Thread.sleep(4000);
			System.out.println("Tracking Number found on Active Report screen !!");
		} catch (Exception e) {
			System.out.println("Not able to found Traking Number on Active Report Screen !");
		}

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

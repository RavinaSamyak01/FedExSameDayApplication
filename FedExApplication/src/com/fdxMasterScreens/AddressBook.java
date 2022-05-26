package com.fdxMasterScreens;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class AddressBook extends BaseClass {

	@Test
	public static void addrBook() throws Exception {
		msg.append("--------------------------------------" + "\n");
		msg.append("ADDRESS BOOK :- " + "\n");

		addressImport();
		addrCreateAddress();
	}

	public static void addrCreateAddress() throws Exception {
		/*
		 * Thread.sleep(5000); Actions builder = new Actions(driver);
		 * driver.findElement(By.linkText("Manage")).click();
		 * 
		 * WebElement ele1 = driver.findElement(By.linkText("Manage My Account"));
		 * builder.moveToElement(ele1).build().perform();
		 * 
		 * WebElement ele2 = driver.findElement(By.linkText("Manage My Account"));
		 * builder.moveToElement(ele2).build().perform();
		 * 
		 * driver.findElement(By.linkText("Address Book")).click();
		 * 
		 */
		msg.append("Manually Create Address and Save  : PASS" + "\n");

		Thread.sleep(5000);
		driver.findElement(By.xpath("//*[@id=\"dvPrintGrid\"]/table/tbody/tr[4]/td[2]/a[37]/img")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("quick_key")).sendKeys("QC_AUTOMAT");
		driver.findElement(By.id("company_name")).sendKeys("AUTOMATION : Nothing Bundt Cakes");
		driver.findElement(By.id("attn")).sendKeys("QC_Abhishek Sharma");
		driver.findElement(By.id("addr_line_1")).sendKeys("304 W Bay Area Blvd.");
		driver.findElement(By.id("zip_code")).sendKeys("77598");
		driver.findElement(By.id("zip_code")).sendKeys(Keys.TAB);
		Thread.sleep(3000);
		driver.findElement(By.id("phone_num")).sendKeys("(310) 123-4482");

		Select s1 = new Select(driver.findElement(By.id("PUBOTHourDropDownList")));
		s1.selectByVisibleText("8");
		Select s2 = new Select(driver.findElement(By.id("PUBOTMinuteDropDownList")));
		s2.selectByVisibleText("00");
		Select s3 = new Select(driver.findElement(By.id("PUBOTFormatDropDownList")));
		s3.selectByVisibleText("AM");

		Select s4 = new Select(driver.findElement(By.id("PUBCTHourDropDownList")));
		s4.selectByVisibleText("5");
		Select s5 = new Select(driver.findElement(By.id("PUBCTMinuteDropDownList")));
		s5.selectByVisibleText("00");
		Select s6 = new Select(driver.findElement(By.id("PUBCTFormatDropDownList")));
		s6.selectByVisibleText("PM");

		driver.findElement(By.id("email_addr")).sendKeys("asharma@samyak.com");
		driver.findElement(By.id("ncsButtons_cmdSubmit")).click();
		System.out.println("Manually Create Address and Save : PASS");
	}

	public static void addressImport() throws InterruptedException, IOException {
		WebDriverWait wait = new WebDriverWait(driver, 50);

		Thread.sleep(5000);
		Actions builder = new Actions(driver);
		driver.findElement(By.linkText("Support")).click();

		WebElement ele1 = driver.findElement(By.linkText("Account Management Tools"));
		builder.moveToElement(ele1).build().perform();

		/*
		 * WebElement ele2 = driver.findElement(By.linkText("Manage My Account"));
		 * builder.moveToElement(ele2).build().perform();
		 */
		driver.findElement(By.linkText("Address Book")).click();
		waitForVisibilityOfElement(By.id("swab.navmenu"), 5);

		for (int i = 0; i < 5; i++) {
			String Env = storage.getProperty("Env");
			System.out.println("Env " + Env);

			String PartialPath = "C:\\Users\\rprajapati\\git\\FedExSameDayApplication\\FedExApplication\\src\\TestFiles\\";

			// --STG File
			String STGAddressImport = PartialPath + "AddressImport_STG.txt";
			String STGComma = PartialPath + "Import_Comma_STG.txt";
			String STGSemiCol = PartialPath + "Import_Semicolon_STG.txt";
			String STGTab = PartialPath + "Import_Tab_STG.txt";
			String STGVerBar = PartialPath + "Import_Verticle Bar_STG.txt";

			// --PreProd File
			String PreProdAddressImport = PartialPath + "AddressImport_PreProd.txt";
			String PreProdComma = PartialPath + "Import_Comma_PreProd.txt";
			String PreProdSemiCol = PartialPath + "Import_Semicolon_PreProd.txt";
			String PreProdTab = PartialPath + "Import_Tab_PreProd.txt";
			String PreProdVerBar = PartialPath + "Import_Verticle Bar_PreProd.txt";

			if (i == 0) {
				driver.findElement(By.linkText("Import")).click();
				waitForVisibilityOfElement(By.id("currentForm"), 5);
				WebElement ele = driver.findElement(By.id("ddlDelimeter"));
				Select opt = new Select(ele);
				opt.selectByVisibleText("Comma");
				if (Env.contains("STG")) {
					driver.findElement(By.id("fileUpload")).sendKeys(STGAddressImport);

				} else if (Env.contains("Pre-Prod")) {
					driver.findElement(By.id("fileUpload")).sendKeys(PreProdAddressImport);

				}
				WebElement ele4 = driver.findElement(By.id("ddlClearAddr"));
				Select opt1 = new Select(ele4);
				opt1.selectByVisibleText("Yes, clear all old addresses");
				File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(scrFile, new File(".\\src\\TestFiles\\AddressImport.png"));
				driver.findElement(By.id("cmdUpload")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cmdProcess")));
				wait.until(ExpectedConditions.elementToBeClickable(By.id("cmdProcess")));
				driver.findElement(By.id("cmdProcess")).click();
				System.out.println("Clear Address and Import !!");
				msg.append("Address Import  : PASS" + "\n");

			}

			else if (i == 1) {
				Thread.sleep(5000);
				driver.findElement(By.linkText("Import")).click();
				WebElement ele = driver.findElement(By.id("ddlDelimeter"));
				Select opt = new Select(ele);
				opt.selectByVisibleText("Comma");
				if (Env.contains("STG")) {
					driver.findElement(By.id("fileUpload")).sendKeys(STGComma);

				} else if (Env.contains("Pre-Prod")) {
					driver.findElement(By.id("fileUpload")).sendKeys(PreProdComma);

				}
				WebElement ele4 = driver.findElement(By.id("ddlClearAddr"));
				Select opt1 = new Select(ele4);
				opt1.selectByVisibleText("No, don't clear old addresses");

				File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(scrFile, new File(".\\src\\Screenshots\\AddressImport01.png"));

				driver.findElement(By.id("cmdUpload")).click();
				Thread.sleep(5000);

				File scrFile1 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(scrFile1, new File(".\\src\\Screenshots\\AddressImport02.png"));
				Thread.sleep(3000);
				driver.findElement(By.id("cmdProcess")).click();
				System.out.println("Address Import with Comma Delimeter : PASS");
			}

			else if (i == 2) {
				Thread.sleep(5000);
				driver.findElement(By.linkText("Import")).click();
				WebElement ele = driver.findElement(By.id("ddlDelimeter"));
				Select opt = new Select(ele);
				opt.selectByVisibleText("Semicolon");
				if (Env.contains("STG")) {
					driver.findElement(By.id("fileUpload")).sendKeys(STGSemiCol);

				} else if (Env.contains("Pre-Prod")) {
					driver.findElement(By.id("fileUpload")).sendKeys(PreProdSemiCol);

				}
				WebElement ele4 = driver.findElement(By.id("ddlClearAddr"));
				Select opt1 = new Select(ele4);
				opt1.selectByVisibleText("No, don't clear old addresses");

				driver.findElement(By.id("cmdUpload")).click();
				Thread.sleep(5000);
				driver.findElement(By.id("cmdProcess")).click();
				System.out.println("Address Import with Semicolon Delimeter : PASS");
				msg.append("Address Import with Semicolon (;) Delimeter    : PASS" + "\n");

			}

			else if (i == 3) {
				Thread.sleep(5000);
				driver.findElement(By.linkText("Import")).click();
				WebElement ele = driver.findElement(By.id("ddlDelimeter"));
				Select opt = new Select(ele);
				opt.selectByVisibleText("Verticle Bar");
				if (Env.contains("STG")) {
					driver.findElement(By.id("fileUpload")).sendKeys(STGVerBar);

				} else if (Env.contains("Pre-Prod")) {
					driver.findElement(By.id("fileUpload")).sendKeys(PreProdVerBar);

				}
				WebElement ele4 = driver.findElement(By.id("ddlClearAddr"));
				Select opt1 = new Select(ele4);
				opt1.selectByVisibleText("No, don't clear old addresses");

				driver.findElement(By.id("cmdUpload")).click();
				Thread.sleep(5000);
				driver.findElement(By.id("cmdProcess")).click();
				System.out.println("Address Import with Verticle Bar Delimeter : PASS");
				msg.append("Address Import with Verticle Bar (|) Delimeter : PASS" + "\n");

			}

			else if (i == 4) {
				Thread.sleep(5000);
				driver.findElement(By.linkText("Import")).click();
				WebElement ele = driver.findElement(By.id("ddlDelimeter"));
				Select opt = new Select(ele);
				opt.selectByVisibleText("Tab");
				if (Env.contains("STG")) {
					driver.findElement(By.id("fileUpload")).sendKeys(STGTab);

				} else if (Env.contains("Pre-Prod")) {
					driver.findElement(By.id("fileUpload")).sendKeys(PreProdTab);

				}
				WebElement ele4 = driver.findElement(By.id("ddlClearAddr"));
				Select opt1 = new Select(ele4);
				opt1.selectByVisibleText("No, don't clear old addresses");

				driver.findElement(By.id("cmdUpload")).click();
				Thread.sleep(5000);
				driver.findElement(By.id("cmdProcess")).click();
				System.out.println("Address Import with Tab Delimeter : PASS");
				msg.append("Address Import with Tab (	) Delimeter        : PASS" + "\n\n");

			}

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

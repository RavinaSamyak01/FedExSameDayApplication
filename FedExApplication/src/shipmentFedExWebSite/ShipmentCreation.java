package shipmentFedExWebSite;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import basePackage.BaseInit;
import basePackage.Email;

public class ShipmentCreation extends BaseInit {

	static StringBuilder msg = new StringBuilder();
	static double ShipmentCreationTime;

	@Test
	public void shipmentCreation() throws Exception {
		Actions act = new Actions(driver);
		msg.append("Shipment Creation Process Start.... " + "\n");
		long start, end;
		// Read data from Excel
		System.out.println("Starting Shipment Creation");
		File src = new File(".\\src\\TestFiles\\FedExShipments.xlsx");
		FileInputStream fis = new FileInputStream(src);
		Workbook workbook = WorkbookFactory.create(fis);
		Sheet sh1 = workbook.getSheet("Sheet1");
//		int rcount = sh1.getLastRowNum();
//		System.out.println("Last row number==" + rcount);

		// try {

		for (int i = 1; i < 26; i++) {
			WebDriverWait wait = new WebDriverWait(driver, 50);
			// --click on shipping menu
			driver.findElement(By.linkText("Shipping")).click(); // Click on ship screen
			Thread.sleep(2000);
			// --click on Create shipment
			start = System.nanoTime();
			driver.findElement(By.linkText("Create a Shipment")).click();
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("content1")));
			driver.getTitle();

			DataFormatter formatter = new DataFormatter();

			if (driver.getPageSource().contains("Change Address")) {
				// If my preferences has setup From Address

				driver.findElement(By.id("cmdChangePUAddr")).click();
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("pnlFullPUAddr")));
			}

			// Pickup Company
			String PUCompany = formatter.formatCellValue(sh1.getRow(i).getCell(0));
			driver.findElement(By.xpath("//*[@id='pu_company']")).clear();
			driver.findElement(By.xpath("//*[@id='pu_company']")).sendKeys(PUCompany);

			// Pickup Name
			String PUName = formatter.formatCellValue(sh1.getRow(i).getCell(1));
			driver.findElement(By.xpath("//*[@id='pu_pertosee']")).clear();
			driver.findElement(By.xpath("//*[@id='pu_pertosee']")).sendKeys(PUName);

			// PU Address line 1
			String PUAddress1 = formatter.formatCellValue(sh1.getRow(i).getCell(2));
			driver.findElement(By.xpath("//*[@id='pu_addr1']")).clear();
			driver.findElement(By.xpath("//*[@id='pu_addr1']")).sendKeys(PUAddress1);

			// Pickup Zip code and tab
			String PUZip = formatter.formatCellValue(sh1.getRow(i).getCell(4));
			driver.findElement(By.xpath("//*[@id='pu_zip']")).clear();
			driver.findElement(By.xpath("//*[@id='pu_zip']")).sendKeys(PUZip);
			Thread.sleep(2000);
			driver.findElement(By.xpath("//*[@id='pu_zip']")).sendKeys(Keys.TAB);
			Thread.sleep(2000);

			// Pickup phone Number
			String PUPhone = formatter.formatCellValue(sh1.getRow(i).getCell(5));
			driver.findElement(By.xpath("//*[@id='pu_phone']")).clear();
			driver.findElement(By.xpath("//*[@id='pu_phone']")).sendKeys(PUPhone);

			// Delivery Information : To Address
			// Delivery Company name
			String DLCompany = formatter.formatCellValue(sh1.getRow(i).getCell(6));
			driver.findElement(By.xpath("//*[@id='dl_company']")).clear();
			driver.findElement(By.xpath("//*[@id='dl_company']")).sendKeys(DLCompany);

			// Delivery Contact Name
			String DLName = formatter.formatCellValue(sh1.getRow(i).getCell(7));
			driver.findElement(By.xpath("//*[@id='dl_attn']")).clear();
			driver.findElement(By.xpath("//*[@id='dl_attn']")).sendKeys(DLName);

			// Del Address line 1
			String DLAddress1 = formatter.formatCellValue(sh1.getRow(i).getCell(8));
			driver.findElement(By.xpath("//*[@id='dl_addr1']")).clear();
			driver.findElement(By.xpath("//*[@id='dl_addr1']")).sendKeys(DLAddress1);

			// Del Zip and tab
			String DLZip = formatter.formatCellValue(sh1.getRow(i).getCell(10));
			driver.findElement(By.xpath("//*[@id='dl_zip']")).clear();
			driver.findElement(By.xpath("//*[@id='dl_zip']")).sendKeys(DLZip);
			driver.findElement(By.xpath("//*[@id='dl_zip']")).sendKeys(Keys.TAB);

			// Del Phone number
			String DLPhone = formatter.formatCellValue(sh1.getRow(i).getCell(11));
			driver.findElement(By.xpath("//*[@id='dl_phone']")).clear();
			driver.findElement(By.xpath("//*[@id='dl_phone']")).sendKeys(DLPhone);

			// click on calander
			driver.findElement(By.id("anchor1xx")).click(); // click on calander
			driver.findElement(By.xpath("//a[contains(.,'Today')]")).click(); // select today
			Thread.sleep(2000);

			// ready time selection
			Select select1 = new Select(driver.findElement(By.id("ddlReadyHour")));
			select1.selectByVisibleText("11");
			Thread.sleep(2000);

			// ready time min selection
			select1 = new Select(driver.findElement(By.id("ddlReadyMinutes")));
			select1.selectByVisibleText("30");
			Thread.sleep(2000);

			// AM/ PM selection
			select1 = new Select(driver.findElement(By.xpath(".//*[@name='ddlReadyTimeType']")));
			select1.selectByVisibleText("AM");
			Thread.sleep(2000);

			// Service ID compare from the Excel
			String serviceid = formatter.formatCellValue(sh1.getRow(i).getCell(13));

			if (serviceid.equals("DRV") || serviceid.equals("AIR") || serviceid.equals("SDC")
					|| serviceid.equals("FRG")) {
				// Enter value in contents text box
				driver.findElement(By.name("txt_content")).clear();
				driver.findElement(By.name("txt_content")).sendKeys("BOX");
			}

			// Compare Pieces from the excel
			String pieces = formatter.formatCellValue(sh1.getRow(i).getCell(14));
			driver.findElement(By.id("pieces")).sendKeys(Keys.BACK_SPACE);
			driver.findElement(By.id("pieces")).sendKeys(Keys.BACK_SPACE);
			pieces = formatter.formatCellValue(sh1.getRow(i).getCell(14));
			driver.findElement(By.id("pieces")).sendKeys(pieces);
			driver.findElement(By.id("pieces")).sendKeys(Keys.TAB);

			// Generate random numbers
			Random rn = new Random();
			int pval = Integer.parseInt(pieces);
			Thread.sleep(2000);
			if (pval == 1) {
				int ans;
				if (serviceid.equals("FRG")) {
					ans = rn.nextInt(200) + 1;
					new Select(driver.findElement(By.id("ddProfile"))).selectByVisibleText("Enter dimensions");
					driver.findElement(By.id("txtDimLen0")).clear();
					driver.findElement(By.id("txtDimLen0")).sendKeys("39");
					driver.findElement(By.id("txtDimWid0")).clear();
					driver.findElement(By.id("txtDimWid0")).sendKeys("39");
					driver.findElement(By.id("txtDimHt0")).clear();
					driver.findElement(By.id("txtDimHt0")).sendKeys("39");
					driver.findElement(By.id("txtActWt0")).clear();
					driver.findElement(By.id("txtActWt0")).sendKeys("142");
				} else {
					ans = rn.nextInt(10) + 1;
					String st = String.valueOf(ans);
					new Select(driver.findElement(By.id("ddProfile"))).selectByVisibleText("Enter dimensions");
					driver.findElement(By.id("txtDimLen0")).clear();
					driver.findElement(By.id("txtDimLen0")).sendKeys(st);
					driver.findElement(By.id("txtDimWid0")).clear();
					driver.findElement(By.id("txtDimWid0")).sendKeys(st);
					driver.findElement(By.id("txtDimHt0")).clear();
					driver.findElement(By.id("txtDimHt0")).sendKeys(st);
					driver.findElement(By.id("txtActWt0")).clear();
					driver.findElement(By.id("txtActWt0")).sendKeys(st);
				}

				driver.findElement(By.id("order_by")).clear();
				driver.findElement(By.id("order_by")).sendKeys("Abhishek Sharma");
				driver.findElement(By.id("order_phone")).clear();
				driver.findElement(By.id("order_phone")).sendKeys("1112223333");

			} else {

				driver.findElement(By.id("rdbNo")).click();
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("multiship")));
				String drpdim;
				String txtqt;
				String dimlen;
				String dimwh;
				String dimhi;
				String ActWt;
				for (int j = 0; j < pval; j++) {

					int ans = rn.nextInt(10) + 1;
					String st = String.valueOf(ans);
					drpdim = "drpdim" + j;
					txtqt = "txtQty" + j;
					dimlen = "txtDimLenN" + j;
					dimwh = "txtDimWidN" + j;
					dimhi = "txtDimHtN" + j;
					ActWt = "txtActWtNew" + j;

					driver.findElement(By.id(txtqt)).clear();
					driver.findElement(By.id(txtqt)).sendKeys("1");

					// --select dim form Dim dropdown
					Select dim = new Select(driver.findElement(By.id(drpdim)));
					dim.selectByVisibleText("Enter dimensions");
					Thread.sleep(2000);

					driver.findElement(By.id(dimlen)).clear();
					driver.findElement(By.id(dimlen)).sendKeys(st);
					driver.findElement(By.id(dimwh)).clear();
					driver.findElement(By.id(dimwh)).sendKeys(st);
					driver.findElement(By.id(dimhi)).clear();
					driver.findElement(By.id(dimhi)).sendKeys(st);
					driver.findElement(By.id(ActWt)).clear();
					driver.findElement(By.id(ActWt)).sendKeys(st);

				}

			}

			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,-850)", "");
			Thread.sleep(2000);
			WebElement Cal = driver.findElement(By.id("lnkCalculate"));
			// Click on calculate link
			act.moveToElement(Cal).click().perform();
			Thread.sleep(2000);
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("divAvailableServicesInternal")));
			Thread.sleep(2000);

			// Service

			File src1 = new File(".\\src\\TestFiles\\FedExShipments.xlsx");
			FileOutputStream fis1 = new FileOutputStream(src1);
			Sheet sh2 = workbook.getSheet("Sheet1");
			// workbook.write(fis1);
			Thread.sleep(2000);

			// If match with PR, below code will execute
			if (serviceid.equals("PR")) {

				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("chkPR")));
				driver.findElement(By.id("chkPR")).click();
				String rate = driver.findElement(By.id("btnPR")).getText();
				System.out.println(rate);
				String ExpectedRate = formatter.formatCellValue(sh1.getRow(i).getCell(12));
				msg.append("PR Service - Actual Rate :" + rate + "\n");
				msg.append("PR Service - Expected Rate :" + ExpectedRate + "\n");
				sh2.getRow(i).createCell(16).setCellValue(rate);

				if (!rate.equals(ExpectedRate)) {
					sh2.getRow(i).createCell(17).setCellValue("FAIL");
					msg.append("Result==" + "FAIL" + "\n");
					fis1.close();
				}

				else {
					sh2.getRow(i).createCell(17).setCellValue("PASS");
					msg.append("Result==" + "PASS " + "\n");
					fis1.close();
				}

			}
			// If match with S2, below code will execute
			else if (serviceid.equals("S2")) {

				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("chkS2")));
				driver.findElement(By.id("chkS2")).click();

				String rate = driver.findElement(By.id("btnS2")).getText();
				System.out.println(rate);
				String ExpectedRate = formatter.formatCellValue(sh1.getRow(i).getCell(12));
				msg.append("S2 Service - Actual Rate :" + rate + "\n");
				msg.append("S2 Service - Expected Rate :" + ExpectedRate + "\n");

				sh2.getRow(i).createCell(16).setCellValue(rate);

				if (!rate.equals(ExpectedRate)) {
					sh2.getRow(i).createCell(17).setCellValue("FAIL");
					msg.append("Result==" + "FAIL" + "\n");
					fis1.close();

				}

				else {
					sh2.getRow(i).createCell(17).setCellValue("PASS");
					msg.append("Result==" + "PASS " + "\n");

					fis1.close();
				}

			} else if (serviceid.equals("EC")) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("chkSDRTS")));
				driver.findElement(By.id("chkSDRTS")).click();
				Thread.sleep(2000);
				try {
					WebElement CheckEC = driver.findElement(By.id("chkEC"));
					wait.until(ExpectedConditions.elementToBeClickable(By.id("chkEC")));
					act.moveToElement(CheckEC).build().perform();
					CheckEC.click();
				} catch (Exception NoSuchEl) {
					WebElement CheckEC = driver.findElement(By.id("chkEC"));
					wait.until(ExpectedConditions.elementToBeClickable(By.id("chkEC")));
					act.moveToElement(CheckEC).build().perform();
					CheckEC.click();
				}
				Thread.sleep(2000);
				String rate = driver.findElement(By.id("btnEC")).getText();
				System.out.println(rate);
				msg.append("EC Service - Actual Rate :" + rate + "\n");

				String ExpectedRate = formatter.formatCellValue(sh1.getRow(i).getCell(12));
				msg.append("EC Service - Expected Rate :" + ExpectedRate + "\n");
				sh2.getRow(i).createCell(16).setCellValue(rate);
				if (!rate.equals(ExpectedRate)) {
					sh2.getRow(i).createCell(17).setCellValue("FAIL");
					msg.append("Result==" + "FAIL" + "\n");

					fis1.close();

				}

				else {
					sh2.getRow(i).createCell(17).setCellValue("PASS");
					msg.append("Result==" + "PASS " + "\n");

					fis1.close();
				}

			} else if (serviceid.equals("DR")) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("chkDR")));
				driver.findElement(By.id("chkDR")).click();
				Thread.sleep(2000);

				String rate = driver.findElement(By.id("btnDR")).getText();
				System.out.println(rate);
				msg.append("DR Service - Actual Rate :" + rate + "\n");

				String ExpectedRate = formatter.formatCellValue(sh1.getRow(i).getCell(12));
				msg.append("DR Service - Expected Rate :" + ExpectedRate + "\n");
				sh2.getRow(i).createCell(16).setCellValue(rate);

				if (!rate.equals(ExpectedRate)) {
					sh2.getRow(i).createCell(17).setCellValue("FAIL");
					msg.append("Result==" + "FAIL" + "\n");

					fis1.close();
				} else {
					sh2.getRow(i).createCell(17).setCellValue("PASS");
					msg.append("Result==" + "PASS " + "\n");

					fis1.close();
				}
			} else if (serviceid.equals("DRV")) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("chkDRV")));
				driver.findElement(By.id("chkDRV")).click();
				Thread.sleep(2000);

				String rate = driver.findElement(By.id("btnDRV")).getText();
				System.out.println(rate);
				msg.append("DRV Service - Actual Rate :" + rate + "\n");

				String ExpectedRate = formatter.formatCellValue(sh1.getRow(i).getCell(12));
				msg.append("DRV Service - Expected Rate :" + ExpectedRate + "\n");
				sh2.getRow(i).createCell(16).setCellValue(rate);

				if (!rate.equals(ExpectedRate)) {
					sh2.getRow(i).createCell(17).setCellValue("FAIL");
					msg.append("Result==" + "FAIL" + "\n");

					fis1.close();

				}

				else {
					sh2.getRow(i).createCell(17).setCellValue("PASS");
					msg.append("Result==" + "PASS " + "\n");

					fis1.close();
				}

			} else if (serviceid.equals("AIR")) {

				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("chkAIR")));
				driver.findElement(By.id("chkAIR")).click();
				Thread.sleep(2000);

				String rate = driver.findElement(By.id("btnAIR")).getText();
				System.out.println(rate);
				msg.append("AIR Service - Actual Rate :" + rate + "\n");

				String ExpectedRate = formatter.formatCellValue(sh1.getRow(i).getCell(12));
				msg.append("AIR Service - Expected Rate :" + ExpectedRate + "\n");
				sh2.getRow(i).createCell(16).setCellValue(rate);
				if (!rate.equals(ExpectedRate)) {
					sh2.getRow(i).createCell(17).setCellValue("FAIL");
					msg.append("Result==" + "FAIL" + "\n");

					fis1.close();

				}

				else {
					sh2.getRow(i).createCell(17).setCellValue("PASS");
					msg.append("Result==" + "PASS " + "\n");

					fis1.close();
				}

			} else if (serviceid.equals("SDC")) {

				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("chkSDC")));
				driver.findElement(By.id("chkSDC")).click();
				Thread.sleep(2000);

				String rate = driver.findElement(By.id("btnSDC")).getText();
				System.out.println(rate);
				msg.append("SDC Service - Actual Rate :" + rate + "\n");

				String ExpectedRate = formatter.formatCellValue(sh1.getRow(i).getCell(12));
				msg.append("SDC Service - Expected Rate :" + ExpectedRate + "\n");
				sh2.getRow(i).createCell(16).setCellValue(rate);
				if (!rate.equals(ExpectedRate)) {
					sh2.getRow(i).createCell(17).setCellValue("FAIL");
					msg.append("Result==" + "FAIL" + "\n");
					fis1.close();

				}

				else {
					sh2.getRow(i).createCell(17).setCellValue("PASS");
					msg.append("Result==" + "PASS " + "\n");

					fis1.close();
				}

			} else if (serviceid.equals("FRG")) {

				driver.findElement(By.id("chkFRG")).click();
				Thread.sleep(2000);

				String rate = driver.findElement(By.id("btnFRG")).getText();
				System.out.println(rate);
				msg.append("FRG Service - Actual Rate :" + rate + "\n");

				String ExpectedRate = formatter.formatCellValue(sh1.getRow(i).getCell(12));
				msg.append("FRG Service - Expected Rate :" + ExpectedRate + "\n");
				sh2.getRow(i).createCell(16).setCellValue(rate);
				if (!rate.equals(ExpectedRate)) {
					sh2.getRow(i).createCell(17).setCellValue("FAIL");
					msg.append("Result==" + "FAIL" + "\n");
					fis1.close();

				}

				else {
					sh2.getRow(i).createCell(17).setCellValue("PASS");
					msg.append("Result==" + "PASS " + "\n");
					fis1.close();

				}
				src1 = new File(".\\src\\TestFiles\\FedExShipments.xlsx");
				fis1 = new FileOutputStream(src1);
				sh2 = workbook.getSheet("Sheet1");
				workbook.write(fis1);
				Thread.sleep(2000);
				fis1.close();

			}

			try {
				WebElement ReCalMsg = driver.findElement(By.id("lblRecalMsg"));
				act.moveToElement(ReCalMsg).build().perform();
				if (ReCalMsg.isDisplayed()) {
					System.out.println(ReCalMsg.getText() + " Message displayed");
					WebElement ReCal = driver.findElement(By.id("lnkCalculate"));
					act.moveToElement(ReCal).click().perform();
					Thread.sleep(2000);
				}

			} catch (Exception ReCalculate) {
				System.out.println("NO need to Recalculate");
			}

			WebElement SHipBTN = driver.findElement(By.id("cmdSubmit"));
			act.moveToElement(SHipBTN).click().perform();
			// Create job button

			try {
				try {
					if (isAlertPresent()) {
						Alert alt = driver.switchTo().alert();
						alt.accept();
					}
				} catch (Exception Alert) {
					System.out.println("Alert is not present");
				}
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblErrMessage")));
				WebElement ACRestriction = driver.findElement(By.id("lblErrMessage"));
				act.moveToElement(ACRestriction).build().perform();
				if (ACRestriction.isDisplayed()) {
					String ErrMsg = driver.findElement(By.id("lblErrMessage")).getText();
					System.out.println("Validation Message is displayed==" + ErrMsg);
					msg.append("Validation Message is displayed==" + ErrMsg + "\n");
					System.out.println("Account is restricted, Please Active the account");
					msg.append("Account is restricted, Please Active the account" + "\n");

				}

			} catch (Exception ACRestriction) {
				System.out.println("Account is Active");
			}
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("currentForm")));
			// Confirm from Shipment Summary screen.
			driver.getTitle();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[id=cmdSubmit]")));
			driver.findElement(By.cssSelector("input[id=cmdSubmit]")).click();

			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("dvPrintGrid")));
			end = System.nanoTime();
			ShipmentCreationTime = (end - start) * 1.0e-9;
			System.out.println("Shipment Creation Time (in Seconds) = " + ShipmentCreationTime);
			msg.append("Shipment Creation Time (in Seconds) = " + ShipmentCreationTime + "\n");
			// Get Shipment tracking number and store in variable
			String VoucherNum = driver.findElement(By.xpath("//*[@id='lblVoucherNum']")).getText();
			System.out.println("Shipment Tracking # " + VoucherNum);

			msg.append("Shipment Tracking # " + VoucherNum + "\n\n");
			sh2.getRow(i).createCell(15).setCellValue(VoucherNum);

			src1 = new File(".\\src\\TestFiles\\FedExShipments.xlsx");
			fis1 = new FileOutputStream(src1);
			sh2 = workbook.getSheet("Sheet1");
			workbook.write(fis1);
			fis1.close();
			Thread.sleep(2000);

			// --copy data to cheetah file
			// --Initialize cheetah file
			File src21 = new File(".\\src\\TestFiles\\CheetahProcessing.xlsx");
			FileInputStream fisI3 = new FileInputStream(src21);
			Workbook workbook2 = WorkbookFactory.create(fisI3);
			FileOutputStream fis3 = new FileOutputStream(src21);
			Sheet sh3 = workbook2.getSheet("Sheet1");

			if (i == 4) {
				// set trackingNo
				sh3.getRow(i).createCell(2).setCellValue(VoucherNum);
				System.out.println("Shipment Tracking No==" + VoucherNum);
				sh3.getRow(i).createCell(3).setCellValue(VoucherNum);
				System.out.println("set the Tracking No in PackageNo");
			} else if (i == 5) {
				// set trackingNo
				sh3.getRow(i).createCell(2).setCellValue(VoucherNum);
				System.out.println("Shipment Tracking No==" + VoucherNum);
				sh3.getRow(i).createCell(3).setCellValue(VoucherNum);
				System.out.println("set the Tracking No in PackageNo");
			} else if (i == 6) {
				// set trackingNo
				sh3.getRow(i).createCell(2).setCellValue(VoucherNum);
				System.out.println("Shipment Tracking No==" + VoucherNum);
				sh3.getRow(i).createCell(3).setCellValue(VoucherNum);
				System.out.println("set the Tracking No in PackageNo");
			} else if (i == 12) {
				// set trackingNo
				sh3.getRow(i).createCell(2).setCellValue(VoucherNum);
				System.out.println("Shipment Tracking No==" + VoucherNum);
				sh3.getRow(i).createCell(3).setCellValue(VoucherNum);
				System.out.println("set the Tracking No in PackageNo");
			} else if (i == 13) {
				// set trackingNo
				sh3.getRow(i).createCell(2).setCellValue(VoucherNum);
				System.out.println("Shipment Tracking No==" + VoucherNum);
				sh3.getRow(i).createCell(3).setCellValue(VoucherNum);
				System.out.println("set the Tracking No in PackageNo");
			} else if (i == 14) {
				// set trackingNo
				sh3.getRow(i).createCell(2).setCellValue(VoucherNum);
				System.out.println("Shipment Tracking No==" + VoucherNum);
				sh3.getRow(i).createCell(3).setCellValue(VoucherNum);
				System.out.println("set the Tracking No in PackageNo");
			} else if (i == 15) {
				// set trackingNo
				sh3.getRow(i).createCell(2).setCellValue(VoucherNum);
				System.out.println("Shipment Tracking No==" + VoucherNum);
				sh3.getRow(i).createCell(3).setCellValue(VoucherNum);
				System.out.println("set the Tracking No in PackageNo");
			} else if (i == 16) {
				// set trackingNo
				sh3.getRow(i).createCell(2).setCellValue(VoucherNum);
				System.out.println("Shipment Tracking No==" + VoucherNum);
				sh3.getRow(i).createCell(3).setCellValue(VoucherNum);
				System.out.println("set the Tracking No in PackageNo");
			} else if (i > 21) {
				System.out.println("No need to add tracking after row 20");
			} else {
				// set trackingNo
				sh3.getRow(i).createCell(2).setCellValue(VoucherNum);
				System.out.println("Shipment Tracking No==" + VoucherNum);
				System.out.println("set the Tracking No");
			}
			workbook2.write(fis3);
			fis3.close();

		}
		msg.append("Shipment Creation Process Completed.... PASS" + "\n");

		/*
		 * } catch (Exception error) {
		 * msg.append("Shipment Creation Process Completed.... FAIL" + "\n");
		 * 
		 * }
		 */
		// If alert pop-up exist, than accept.

		String subject = "Selenium Automation Script : STAGING FedEx Shipment Creation";
		try {
			// asharma@samyak.com,pgandhi@samyak.com,sdas@samyak.com,byagnik@samyak.com,pdoshi@samyak.com,kbrahmbhatt@samyak.com
			// ravina.prajapati@samyak.com,asharma@samyak.com,parth.doshi@samyak.com
			Email.sendMail("ravina.prajapati@samyak.com,asharma@samyak.com,parth.doshi@samyak.com", subject, msg.toString(), "");
		} catch (Exception ex) {
			Logger.getLogger(ShipmentCreation.class.getName()).log(Level.SEVERE, null, ex);
		}
		/*
		 * driver.findElement(By.linkText("Rate")).click(); // Go to Rate screen
		 * driver.findElement(By.id("Wuc_headerlogout1_lnkbtnLogout")).click(); // click
		 * on Logout driver.quit(); // close browser
		 */
	}

	public static void waitForVisibilityOfElement(By objLocator, long lTime) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, lTime);
			wait.until(ExpectedConditions.visibilityOfElementLocated(objLocator));
		} catch (Exception e) {
		}
	}

	public static boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException ex) {
			return false;
		}
	}

	public static void waitForInVisibilityOfElement(By objLocator, long lTime) {

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(objLocator));
	}

	public static void copyData() throws EncryptedDocumentException, InvalidFormatException, IOException {

		// Read data from Excel
		File srcCopy = new File(".\\src\\TestFiles\\FedExShipments.xlsx");
		FileInputStream fisCopy = new FileInputStream(srcCopy);
		Workbook workbookCopy = WorkbookFactory.create(fisCopy);
		Sheet CopySheet = workbookCopy.getSheet("Sheet1");
		// int rcount = sh1.getLastRowNum();

		// --Initialize cheetah file
		File srcWrite = new File(".\\src\\TestFiles\\CheetahProcessing.xlsx");
		FileInputStream FInwrite = new FileInputStream(srcWrite);
		Workbook workbookWrite = WorkbookFactory.create(FInwrite);

		File Outsrc = new File(".\\src\\TestFiles\\CheetahProcessing.xlsx");
		FileOutputStream fisout = new FileOutputStream(Outsrc);
		Sheet shout = workbookWrite.getSheet("Sheet1");
		workbookWrite.write(fisout);

		for (int i = 1; i < 15; i++) {
			DataFormatter formatter = new DataFormatter();
			// get trackingNo
			String TrackingNo = formatter.formatCellValue(CopySheet.getRow(i).getCell(15));
			System.out.println("Shipment Tracking No==" + TrackingNo);
			System.out.println("get the Tracking No");

			// set trackingNo
			shout.getRow(i).createCell(2).setCellValue(TrackingNo);
			System.out.println("Shipment Tracking No==" + TrackingNo);
			System.out.println("set the Tracking No");
			if (i == 4) {
				// set trackingNo
				shout.getRow(i).createCell(3).setCellValue(TrackingNo);

				shout.getRow(i).createCell(3).setCellValue(TrackingNo);
				System.out.println("Shipment Tracking No==" + TrackingNo);
				System.out.println("set the Tracking No in PackageNo");
			} else if (i == 5) {
				// set trackingNo
				shout.getRow(i).createCell(3).setCellValue(TrackingNo);
				System.out.println("Shipment Tracking No==" + TrackingNo);
				System.out.println("set the Tracking No in PackageNo");
			} else if (i == 6) {
				// set trackingNo
				shout.getRow(i).createCell(3).setCellValue(TrackingNo);
				System.out.println("Shipment Tracking No==" + TrackingNo);
				System.out.println("set the Tracking No in PackageNo");
			} else if (i == 12) {
				// set trackingNo
				shout.getRow(i).createCell(3).setCellValue(TrackingNo);
				System.out.println("Shipment Tracking No==" + TrackingNo);
				System.out.println("set the Tracking No in PackageNo");
			} else if (i == 13) {
				// set trackingNo
				shout.getRow(i).createCell(3).setCellValue(TrackingNo);
				System.out.println("Shipment Tracking No==" + TrackingNo);
				System.out.println("set the Tracking No in PackageNo");
			} else if (i == 14) {
				// set trackingNo
				shout.getRow(i).createCell(3).setCellValue(TrackingNo);
				System.out.println("Shipment Tracking No==" + TrackingNo);
				System.out.println("set the Tracking No in PackageNo");
			} else if (i == 15) {
				// set trackingNo
				shout.getRow(i).createCell(3).setCellValue(TrackingNo);
				System.out.println("Shipment Tracking No==" + TrackingNo);
				System.out.println("set the Tracking No in PackageNo");
			} else if (i == 16) {
				// set trackingNo
				shout.getRow(i).createCell(3).setCellValue(TrackingNo);
				System.out.println("Shipment Tracking No==" + TrackingNo);
				System.out.println("set the Tracking No in PackageNo");
			}
		}
		fisout.close();
	}
}

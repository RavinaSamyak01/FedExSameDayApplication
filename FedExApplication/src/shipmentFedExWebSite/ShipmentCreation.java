package shipmentFedExWebSite;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
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
		String Result = null;

		try {
			// 26

			for (int i = 25; i < 26; i++) {
				WebDriverWait wait = new WebDriverWait(driver, 50);
				// --click on shipping menu
				driver.findElement(By.linkText("Shipping")).click(); // Click on ship screen
				Thread.sleep(2000);
				// --click on Create shipment
				start = System.nanoTime();
				driver.findElement(By.linkText("Create a Shipment")).click();
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("content1")));
				driver.getTitle();

				if (driver.getPageSource().contains("Change Address")) {
					// If my preferences has setup From Address

					driver.findElement(By.id("cmdChangePUAddr")).click();
					wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("pnlFullPUAddr")));
				}

				// Pickup Company
				String PUCompany = getData("ShipmentCreation", "Sheet1", i, 0);
				driver.findElement(By.xpath("//*[@id='pu_company']")).clear();
				driver.findElement(By.xpath("//*[@id='pu_company']")).sendKeys(PUCompany);

				// Pickup Name
				String PUName = getData("ShipmentCreation", "Sheet1", i, 1);
				driver.findElement(By.xpath("//*[@id='pu_pertosee']")).clear();
				driver.findElement(By.xpath("//*[@id='pu_pertosee']")).sendKeys(PUName);

				// PU Address line 1
				String PUAddress1 = getData("ShipmentCreation", "Sheet1", i, 2);
				driver.findElement(By.xpath("//*[@id='pu_addr1']")).clear();
				driver.findElement(By.xpath("//*[@id='pu_addr1']")).sendKeys(PUAddress1);

				// Pickup Zip code and tab
				String PUZip = getData("ShipmentCreation", "Sheet1", i, 4);
				driver.findElement(By.xpath("//*[@id='pu_zip']")).clear();
				driver.findElement(By.xpath("//*[@id='pu_zip']")).sendKeys(PUZip);
				Thread.sleep(2000);
				driver.findElement(By.xpath("//*[@id='pu_zip']")).sendKeys(Keys.TAB);
				Thread.sleep(2000);

				// Pickup phone Number
				String PUPhone = getData("ShipmentCreation", "Sheet1", i, 5);
				driver.findElement(By.xpath("//*[@id='pu_phone']")).clear();
				driver.findElement(By.xpath("//*[@id='pu_phone']")).sendKeys(PUPhone);

				// Delivery Information : To Address
				// Delivery Company name
				String DLCompany = getData("ShipmentCreation", "Sheet1", i, 6);
				driver.findElement(By.xpath("//*[@id='dl_company']")).clear();
				driver.findElement(By.xpath("//*[@id='dl_company']")).sendKeys(DLCompany);

				// Delivery Contact Name
				String DLName = getData("ShipmentCreation", "Sheet1", i, 7);
				driver.findElement(By.xpath("//*[@id='dl_attn']")).clear();
				driver.findElement(By.xpath("//*[@id='dl_attn']")).sendKeys(DLName);

				// Del Address line 1
				String DLAddress1 = getData("ShipmentCreation", "Sheet1", i, 8);
				driver.findElement(By.xpath("//*[@id='dl_addr1']")).clear();
				driver.findElement(By.xpath("//*[@id='dl_addr1']")).sendKeys(DLAddress1);

				// Del Zip and tab
				String DLZip = getData("ShipmentCreation", "Sheet1", i, 10);
				driver.findElement(By.xpath("//*[@id='dl_zip']")).clear();
				driver.findElement(By.xpath("//*[@id='dl_zip']")).sendKeys(DLZip);
				driver.findElement(By.xpath("//*[@id='dl_zip']")).sendKeys(Keys.TAB);

				// Del Phone number
				String DLPhone = getData("ShipmentCreation", "Sheet1", i, 11);
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
				String serviceid = getData("ShipmentCreation", "Sheet1", i, 13);

				if (serviceid.equals("DRV") || serviceid.equals("AIR") || serviceid.equals("SDC")
						|| serviceid.equals("FRG")) {
					// Enter value in contents text box
					driver.findElement(By.name("txt_content")).clear();
					driver.findElement(By.name("txt_content")).sendKeys("BOX");
				}

				// Compare Pieces from the excel
				String pieces = getData("ShipmentCreation", "Sheet1", i, 14);
				driver.findElement(By.id("pieces")).sendKeys(Keys.BACK_SPACE);
				driver.findElement(By.id("pieces")).sendKeys(Keys.BACK_SPACE);
				pieces = getData("ShipmentCreation", "Sheet1", i, 14);
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

				try {
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblErrSignatureType")));
					String signVal = driver.findElement(By.id("lblErrSignatureType")).getText();
					System.out.println("validation is displayed==" + signVal);

					// --select signature
					Select sign = new Select(driver.findElement(By.id("ddlSignatureType")));
					sign.selectByValue("ISR");
					Thread.sleep(2000);
					System.out.println("Signature selected");

					Cal = driver.findElement(By.id("lnkCalculate"));
					// Click on calculate link
					act.moveToElement(Cal).click().perform();
					Thread.sleep(2000);
					wait.until(
							ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("divAvailableServicesInternal")));
					Thread.sleep(2000);

					try {
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblPackageType")));
						String ContVal = driver.findElement(By.id("lblPackageType")).getText();
						System.out.println("validation is displayed==" + ContVal);

						// --Enter contents
						WebElement contents = driver.findElement(By.id("txt_content"));
						jse.executeScript("arguments[0].scrollIntoView();", contents);
						act.moveToElement(contents).build().perform();
						contents.sendKeys("AUtomation COntents");
						Thread.sleep(2000);
						System.out.println("Contents entered");

						jse.executeScript("window.scrollBy(0,-850)", "");
						Thread.sleep(2000);
						Cal = driver.findElement(By.id("lnkCalculate"));
						// Click on calculate link
						act.moveToElement(Cal).click().perform();
						Thread.sleep(2000);
						wait.until(ExpectedConditions
								.visibilityOfAllElementsLocatedBy(By.id("divAvailableServicesInternal")));
						Thread.sleep(2000);

					} catch (Exception econt) {

					}

				} catch (Exception e) {

				}

				// Service

				// If match with PR, below code will execute
				if (serviceid.equals("PR")) {

					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("chkPR")));
					driver.findElement(By.id("chkPR")).click();
					String rate = driver.findElement(By.id("btnPR")).getText();
					System.out.println(rate);
					String ExpectedRate = getData("ShipmentCreation", "Sheet1", i, 12);
					msg.append("PR Service - Actual Rate :" + rate + "\n");
					msg.append("PR Service - Expected Rate :" + ExpectedRate + "\n");
					setData("ShipmentCreation", "Sheet1", i, 16, rate);
					if (!rate.equals(ExpectedRate)) {
						setData("ShipmentCreation", "Sheet1", i, 17, "FAIL");
						msg.append("Result==" + "FAIL" + "\n");
					}

					else {
						setData("ShipmentCreation", "Sheet1", i, 17, "PASS");
						msg.append("Result==" + "PASS " + "\n");
					}

				}
				// If match with S2, below code will execute
				else if (serviceid.equals("S2")) {

					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("chkS2")));
					driver.findElement(By.id("chkS2")).click();

					String rate = driver.findElement(By.id("btnS2")).getText();
					System.out.println(rate);
					String ExpectedRate = getData("ShipmentCreation", "Sheet1", i, 12);
					msg.append("S2 Service - Actual Rate :" + rate + "\n");
					msg.append("S2 Service - Expected Rate :" + ExpectedRate + "\n");

					setData("ShipmentCreation", "Sheet1", i, 16, rate);

					if (!rate.equals(ExpectedRate)) {
						setData("ShipmentCreation", "Sheet1", i, 17, "FAIL");
						msg.append("Result==" + "FAIL" + "\n");

					}

					else {
						setData("ShipmentCreation", "Sheet1", i, 17, "PASS");
						msg.append("Result==" + "PASS " + "\n");

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

					String ExpectedRate = getData("ShipmentCreation", "Sheet1", i, 12);
					msg.append("EC Service - Expected Rate :" + ExpectedRate + "\n");
					setData("ShipmentCreation", "Sheet1", i, 16, rate);
					if (!rate.equals(ExpectedRate)) {
						setData("ShipmentCreation", "Sheet1", i, 17, "FAIL");
						msg.append("Result==" + "FAIL" + "\n");

					}

					else {
						setData("ShipmentCreation", "Sheet1", i, 17, "PASS");
						msg.append("Result==" + "PASS " + "\n");

					}

				} else if (serviceid.equals("DR")) {
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("chkDR")));
					driver.findElement(By.id("chkDR")).click();
					Thread.sleep(2000);

					String rate = driver.findElement(By.id("btnDR")).getText();
					System.out.println(rate);
					msg.append("DR Service - Actual Rate :" + rate + "\n");

					String ExpectedRate = getData("ShipmentCreation", "Sheet1", i, 12);
					msg.append("DR Service - Expected Rate :" + ExpectedRate + "\n");
					setData("ShipmentCreation", "Sheet1", i, 16, rate);

					if (!rate.equals(ExpectedRate)) {
						setData("ShipmentCreation", "Sheet1", i, 17, "FAIL");
						msg.append("Result==" + "FAIL" + "\n");

					} else {
						setData("ShipmentCreation", "Sheet1", i, 17, "PASS");
						msg.append("Result==" + "PASS " + "\n");

					}

				} else if (serviceid.equals("DRV")) {
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("chkDRV")));
					driver.findElement(By.id("chkDRV")).click();
					Thread.sleep(2000);

					String rate = driver.findElement(By.id("btnDRV")).getText();
					System.out.println(rate);
					msg.append("DRV Service - Actual Rate :" + rate + "\n");

					String ExpectedRate = getData("ShipmentCreation", "Sheet1", i, 12);
					msg.append("DRV Service - Expected Rate :" + ExpectedRate + "\n");
					setData("ShipmentCreation", "Sheet1", i, 16, rate);

					if (!rate.equals(ExpectedRate)) {
						setData("ShipmentCreation", "Sheet1", i, 17, "FAIL");
						msg.append("Result==" + "FAIL" + "\n");

					}

					else {
						setData("ShipmentCreation", "Sheet1", i, 17, "PASS");
						msg.append("Result==" + "PASS " + "\n");

					}

				} else if (serviceid.equals("AIR")) {

					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("chkAIR")));
					driver.findElement(By.id("chkAIR")).click();
					Thread.sleep(2000);

					String rate = driver.findElement(By.id("btnAIR")).getText();
					System.out.println(rate);
					msg.append("AIR Service - Actual Rate :" + rate + "\n");

					String ExpectedRate = getData("ShipmentCreation", "Sheet1", i, 12);
					msg.append("AIR Service - Expected Rate :" + ExpectedRate + "\n");
					setData("ShipmentCreation", "Sheet1", i, 16, rate);
					if (!rate.equals(ExpectedRate)) {
						setData("ShipmentCreation", "Sheet1", i, 17, "FAIL");
						msg.append("Result==" + "FAIL" + "\n");

					}

					else {
						setData("ShipmentCreation", "Sheet1", i, 17, "PASS");
						msg.append("Result==" + "PASS " + "\n");

					}

				} else if (serviceid.equals("SDC")) {

					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("chkSDC")));
					driver.findElement(By.id("chkSDC")).click();
					Thread.sleep(2000);

					String rate = driver.findElement(By.id("btnSDC")).getText();
					System.out.println(rate);
					msg.append("SDC Service - Actual Rate :" + rate + "\n");

					String ExpectedRate = getData("ShipmentCreation", "Sheet1", i, 12);
					msg.append("SDC Service - Expected Rate :" + ExpectedRate + "\n");
					setData("ShipmentCreation", "Sheet1", i, 16, rate);
					if (!rate.equals(ExpectedRate)) {
						setData("ShipmentCreation", "Sheet1", i, 17, "FAIL");
						msg.append("Result==" + "FAIL" + "\n");

					}

					else {
						setData("ShipmentCreation", "Sheet1", i, 17, "PASS");
						msg.append("Result==" + "PASS " + "\n");

					}

				} else if (serviceid.equals("FRG")) {

					driver.findElement(By.id("chkFRG")).click();
					Thread.sleep(2000);

					String rate = driver.findElement(By.id("btnFRG")).getText();
					System.out.println(rate);
					msg.append("FRG Service - Actual Rate :" + rate + "\n");

					String ExpectedRate = getData("ShipmentCreation", "Sheet1", i, 12);
					msg.append("FRG Service - Expected Rate :" + ExpectedRate + "\n");
					setData("ShipmentCreation", "Sheet1", i, 16, rate);
					if (!rate.equals(ExpectedRate)) {
						setData("ShipmentCreation", "Sheet1", i, 17, "FAIL");
						msg.append("Result==" + "FAIL" + "\n");

					}

					else {
						setData("ShipmentCreation", "Sheet1", i, 17, "PASS");
						msg.append("Result==" + "PASS " + "\n");

					}

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

					Result = "FAIL";

				} catch (Exception ACRestriction) {
					System.out.println("Account is Active");
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
					setData("ShipmentCreation", "Sheet1", i, 15, VoucherNum);

					Thread.sleep(2000);

					// --copy data to cheetah file
					// --Initialize cheetah file
					if (i == 4) {
						// set trackingNo
						setData("CheetahProcess", "Sheet1", i, 2, VoucherNum);
						System.out.println("Shipment Tracking No==" + VoucherNum);
						setData("CheetahProcess", "Sheet1", i, 3, VoucherNum);
						System.out.println("set the Tracking No in PackageNo");
					} else if (i == 5) {
						// set trackingNo
						setData("CheetahProcess", "Sheet1", i, 2, VoucherNum);
						System.out.println("Shipment Tracking No==" + VoucherNum);
						setData("CheetahProcess", "Sheet1", i, 3, VoucherNum);
						System.out.println("set the Tracking No in PackageNo");
					} else if (i == 6) {
						// set trackingNo
						setData("CheetahProcess", "Sheet1", i, 2, VoucherNum);
						System.out.println("Shipment Tracking No==" + VoucherNum);
						setData("CheetahProcess", "Sheet1", i, 3, VoucherNum);
						System.out.println("set the Tracking No in PackageNo");
					} else if (i == 12) {
						// set trackingNo
						setData("CheetahProcess", "Sheet1", i, 2, VoucherNum);
						System.out.println("Shipment Tracking No==" + VoucherNum);
						setData("CheetahProcess", "Sheet1", i, 3, VoucherNum);
						System.out.println("set the Tracking No in PackageNo");
					} else if (i == 13) {
						// set trackingNo
						setData("CheetahProcess", "Sheet1", i, 2, VoucherNum);
						System.out.println("Shipment Tracking No==" + VoucherNum);
						setData("CheetahProcess", "Sheet1", i, 3, VoucherNum);
						System.out.println("set the Tracking No in PackageNo");
					} else if (i == 14) {
						// set trackingNo
						setData("CheetahProcess", "Sheet1", i, 2, VoucherNum);
						System.out.println("Shipment Tracking No==" + VoucherNum);
						setData("CheetahProcess", "Sheet1", i, 3, VoucherNum);
						System.out.println("set the Tracking No in PackageNo");
					} else if (i == 15) {
						// set trackingNo
						setData("CheetahProcess", "Sheet1", i, 2, VoucherNum);
						System.out.println("Shipment Tracking No==" + VoucherNum);
						setData("CheetahProcess", "Sheet1", i, 3, VoucherNum);
						System.out.println("set the Tracking No in PackageNo");
					} else if (i == 16) {
						// set trackingNo
						setData("CheetahProcess", "Sheet1", i, 2, VoucherNum);
						System.out.println("Shipment Tracking No==" + VoucherNum);
						setData("CheetahProcess", "Sheet1", i, 3, VoucherNum);
						System.out.println("set the Tracking No in PackageNo");
					} else if (i > 21) {
						System.out.println("No need to add tracking after row 20");
						Result = "PASS";

					} else {
						// set trackingNo
						setData("CheetahProcess", "Sheet1", i, 2, VoucherNum);
						System.out.println("Shipment Tracking No==" + VoucherNum);
						System.out.println("set the Tracking No");

					}

				}

			}
			msg.append("Shipment Creation Process Completed.... PASS" + "\n");

		} catch (Exception error) {
			msg.append("Shipment Creation Process Completed.... FAIL" + "\n");
			Result = "FAIL";

		}

		// If alert pop-up exist, than accept.
		String Env = storage.getProperty("Env");
		String subject = "Selenium Automation Script: " + Env + " FedEx Shipment Creation";
		try {
			// asharma@samyak.com,pgandhi@samyak.com,sdas@samyak.com,byagnik@samyak.com,pdoshi@samyak.com,kbrahmbhatt@samyak.com
			// ravina.prajapati@samyak.com,asharma@samyak.com,parth.doshi@samyak.com
			Email.sendMail("ravina.prajapati@samyak.com,asharma@samyak.com,parth.doshi@samyak.com", subject,
					msg.toString(), "");
		} catch (Exception ex) {
			Logger.getLogger(ShipmentCreation.class.getName()).log(Level.SEVERE, null, ex);
		}
		/*
		 * driver.findElement(By.linkText("Rate")).click(); // Go to Rate screen
		 * driver.findElement(By.id("Wuc_headerlogout1_lnkbtnLogout")).click(); // click
		 * on Logout driver.quit(); // close browser
		 */

		// --Run Cheetah Method
		if (Result.equalsIgnoreCase("PASS")) {
			CheetahOrderProcessing Cheeath = new CheetahOrderProcessing();
			Cheeath.cheetahOrderPro();
			// --Moved to FedEx URL and login
			login();

		} else {
			System.out.println(Result + "=FAIL");
		}

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

}

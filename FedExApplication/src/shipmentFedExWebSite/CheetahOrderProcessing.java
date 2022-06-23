package shipmentFedExWebSite;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import basePackage.BaseInit;
import basePackage.Email;

public class CheetahOrderProcessing extends BaseInit {
	static StringBuilder msg = new StringBuilder();

	@Test
	public void cheetahOrderPro() throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 5);
		Actions act = new Actions(driver);
		msg.append("Cheetah Order Processing Start - " + "\n\n\n");

		// r.keyPress(KeyEvent.VK_F11);

		String Env = storage.getProperty("Env");
		String BaseURL = null;
		if (Env.equalsIgnoreCase("Pre-Prod")) {
			BaseURL = storage.getProperty("PREPRODURLCheetahOrder");

		} else if (Env.equalsIgnoreCase("STG")) {
			BaseURL = storage.getProperty("STGURLCheetahOrder");

		} else if (Env.equalsIgnoreCase("DEV")) {
			BaseURL = storage.getProperty("DEVURLCheetahOrder");

		}
		driver.get(BaseURL);
		Thread.sleep(4000);

		System.out.println("Cheetah Process Start----" + "\n");
		// Read data from Excel

		// 22
		for (int i = 21; i < 22; i++) {

			if (i == 1) // Normal Order Processing
			{
				String SHipTrackNo = getData("CheetahProcess", "Sheet1", i, 2);
				msg.append("Shipment Tracking No==" + SHipTrackNo + "\n");

				String ShipmentTracking = getData("CheetahProcess", "Sheet1", i, 2);
				driver.findElement(By.id("MainContent_txtShipTrackNum")).clear();
				driver.findElement(By.id("MainContent_txtShipTrackNum")).sendKeys(ShipmentTracking);

				Thread.sleep(1000);
				confirmEd(i);

				Thread.sleep(4000);
				pickedUp(i);

				Thread.sleep(4000);
				pustatusUpdate(i);

				Thread.sleep(4000);
				deliverEd(i);

				Thread.sleep(4000);
				dlstatusUpdate(i);
				System.out.println("CASE1: Order Processing DONE !!");
			}

			else if (i == 2) // Rejected job from Cheetah
			{
				String SHipTrackNo = getData("CheetahProcess", "Sheet1", i, 2);
				msg.append("Shipment Tracking No==" + SHipTrackNo + "\n");
				System.out.println("CASE2: [Rejected] Order Processing Initiated !!");
				String ShipmentTracking = getData("CheetahProcess", "Sheet1", i, 2);
				driver.findElement(By.id("MainContent_txtShipTrackNum")).clear();
				driver.findElement(By.id("MainContent_txtShipTrackNum")).sendKeys(ShipmentTracking);

				Thread.sleep(1000);
				rejectEd(i);
				System.out.println("CASE2: Rejected DONE !!");
			}

			else if (i == 3) // Package Detail Change
			{
				String SHipTrackNo = getData("CheetahProcess", "Sheet1", i, 2);
				msg.append("Shipment Tracking No==" + SHipTrackNo + "\n");
				String ShipmentTracking = getData("CheetahProcess", "Sheet1", i, 2);
				driver.findElement(By.id("MainContent_txtShipTrackNum")).clear();
				driver.findElement(By.id("MainContent_txtShipTrackNum")).sendKeys(ShipmentTracking);

				String PackageTrackNum = getData("CheetahProcess", "Sheet1", i, 3);
				driver.findElement(By.id("MainContent_txtPkgTrackNum")).clear();
				driver.findElement(By.id("MainContent_txtPkgTrackNum")).sendKeys(PackageTrackNum);

				Thread.sleep(1000);

				packageDetailChange(i);
				pickedUp(i);
				pustatusUpdate(i);
				deliverEd(i);
				dlstatusUpdate(i);
				System.out.println("CASE3: [Package Detail Change] Order Processing Completed !!");
			}

			else if (i == 4) // Add Package
			{
				String SHipTrackNo = getData("CheetahProcess", "Sheet1", i, 2);
				msg.append("Shipment Tracking No==" + SHipTrackNo + "\n");
				String ShipmentTracking = getData("CheetahProcess", "Sheet1", i, 2);
				driver.findElement(By.id("MainContent_txtShipTrackNum")).clear();
				driver.findElement(By.id("MainContent_txtShipTrackNum")).sendKeys(ShipmentTracking);

				Thread.sleep(1000);

				addPackage(i);
				pickedUp(i);
				pustatusUpdate(i);
				deliverEd(i);
				dlstatusUpdate(i);
				System.out.println("CASE4: [Add Package] Order Processing Completed !!");
			}

			else if (i == 5) // Pickup Exception
			{
				String SHipTrackNo = getData("CheetahProcess", "Sheet1", i, 2);
				msg.append("Shipment Tracking No==" + SHipTrackNo + "\n");
				msg.append("CASE5: PickupException :- " + "\n");
				int m = 5;
				String puexceptioncode = getData("CheetahProcess", "Sheet1", i, 5);

				if (puexceptioncode.equals("PAT")) {
					String ShipmentTracking2 = getData("CheetahProcess", "Sheet1", m, 2);
					driver.findElement(By.id("MainContent_txtShipTrackNum")).clear();
					driver.findElement(By.id("MainContent_txtShipTrackNum")).sendKeys(ShipmentTracking2);

					Thread.sleep(1000);
					confirmEd(m);

					String ShipmentTracking = getData("CheetahProcess", "Sheet1", m, 2);
					driver.findElement(By.id("MainContent_txtShipTrackNum")).clear();
					driver.findElement(By.id("MainContent_txtShipTrackNum")).sendKeys(ShipmentTracking);

					Thread.sleep(1000);

					driver.findElement(By.id("MainContent_txtShipStatus")).clear();
					driver.findElement(By.id("MainContent_txtShipStatus")).sendKeys("PickupException");
					driver.findElement(By.id("MainContent_txtEventDescription")).clear();
					driver.findElement(By.id("MainContent_txtStatusGridDescription")).clear();
					driver.findElement(By.id("MainContent_txtStatusBarDescription")).clear();

					driver.findElement(By.id("MainContent_txtExcpCode")).clear();
					driver.findElement(By.id("MainContent_txtExcpCode")).sendKeys("PAT");

					new Select(driver.findElement(By.id("MainContent_ddlActionFlag")))
							.selectByVisibleText("Process Only");
					driver.findElement(By.id("MainContent_txtSequenceId")).clear();
					driver.findElement(By.id("MainContent_txtSequenceId")).sendKeys("17");
					String EventJsonData = getData("CheetahProcess", "Sheet1", 5, 10);
					driver.findElement(By.id("MainContent_txtEventJsonData")).clear();
					driver.findElement(By.id("MainContent_txtEventJsonData")).sendKeys(EventJsonData);

					driver.findElement(By.id("MainContent_btnSubmit")).click();
					Thread.sleep(1000);

					// --Wait for status

					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("MainContent_lblRespStatus")));
					WebElement RespStatus = driver.findElement(By.id("MainContent_lblRespStatus"));
					act.moveToElement(RespStatus).build().perform();
					if (RespStatus.isDisplayed()) {
						String ResponseStatus = RespStatus.getText();
						System.out.println("Response Message==" + ResponseStatus + "\n");
						String ShipTrackNo = driver.findElement(By.id("MainContent_lblShipTrackNum")).getText();
						msg.append("ShipmentTrackNo== " + ShipTrackNo + "\n");

						if (ResponseStatus.equalsIgnoreCase("OK")) {
							msg.append("PAT : PASS " + "\n");
							msg.append("Response==" + ResponseStatus + "\n\n\n");
							setData("CheetahProcess", "Sheet1", m, 18, "PASS");

						} else {
							msg.append("PAT : FAIL " + "\n");
							msg.append("Response==" + ResponseStatus + "\n\n\n");
							setData("CheetahProcess", "Sheet1", m, 18, "FAIL");
							getScreenshot(driver, "PAT_Fail_" + ShipTrackNo);

						}

					} else {
						System.out.println("Response Message is not displayed");
						msg.append("Response Message is not displayed");

					}

					pickedUp(m);
					pustatusUpdate(m);
					deliverEd(m);
					dlstatusUpdate(m);

				}
				m++;
				puexceptioncode = getData("CheetahProcess", "Sheet1", m, 5);

				if (puexceptioncode.equals("PPN")) {
					String ShipmentTracking2 = getData("CheetahProcess", "Sheet1", m, 2);
					driver.findElement(By.id("MainContent_txtShipTrackNum")).clear();
					driver.findElement(By.id("MainContent_txtShipTrackNum")).sendKeys(ShipmentTracking2);

					Thread.sleep(1000);
					confirmEd(m);

					String ShipmentTracking = getData("CheetahProcess", "Sheet1", m, 2);
					driver.findElement(By.id("MainContent_txtShipTrackNum")).clear();
					driver.findElement(By.id("MainContent_txtShipTrackNum")).sendKeys(ShipmentTracking);

					Thread.sleep(1000);

					String PackageTrackNum = getData("CheetahProcess", "Sheet1", m, 3);
					driver.findElement(By.id("MainContent_txtPkgTrackNum")).clear();
					driver.findElement(By.id("MainContent_txtPkgTrackNum")).sendKeys(PackageTrackNum);
					Thread.sleep(1000);
					driver.findElement(By.id("MainContent_txtShipStatus")).clear();
					driver.findElement(By.id("MainContent_txtShipStatus")).sendKeys("PickupException");
					driver.findElement(By.id("MainContent_txtEventDescription")).clear();
					driver.findElement(By.id("MainContent_txtStatusGridDescription")).clear();
					driver.findElement(By.id("MainContent_txtStatusBarDescription")).clear();

					driver.findElement(By.id("MainContent_txtExcpCode")).clear();
					driver.findElement(By.id("MainContent_txtExcpCode")).sendKeys("PPN");

					new Select(driver.findElement(By.id("MainContent_ddlActionFlag")))
							.selectByVisibleText("Process Only");
					driver.findElement(By.id("MainContent_txtSequenceId")).clear();
					driver.findElement(By.id("MainContent_txtSequenceId")).sendKeys("17");

					driver.findElement(By.id("MainContent_btnSubmit")).click();
					Thread.sleep(1000);
					// --Wait for status
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("MainContent_lblRespStatus")));
					WebElement RespStatus = driver.findElement(By.id("MainContent_lblRespStatus"));
					act.moveToElement(RespStatus).build().perform();
					if (RespStatus.isDisplayed()) {
						String ResponseStatus = RespStatus.getText();
						System.out.println("Response Message==" + ResponseStatus + "\n");
						String ShipTrackNo = driver.findElement(By.id("MainContent_lblShipTrackNum")).getText();
						msg.append("ShipmentTrackNo== " + ShipTrackNo + "\n");

						if (ResponseStatus.equalsIgnoreCase("OK")) {
							msg.append("PPN : PASS " + "\n");
							msg.append("Response==" + ResponseStatus + "\n\n\n");
							setData("CheetahProcess", "Sheet1", m, 19, "PASS");

						} else {
							msg.append("PPN : FAIL " + "\n");
							msg.append("Response==" + ResponseStatus + "\n\n\n");
							setData("CheetahProcess", "Sheet1", m, 19, "FAIL");
							getScreenshot(driver, "PPN_Fail_" + ShipTrackNo);

						}
					} else {
						System.out.println("Response Message is not displayed");
						msg.append("Response Message is not displayed");

					}

					pickedUp(m);
					pustatusUpdate(m);
					deliverEd(m);
					dlstatusUpdate(m);

				}
				m++;
				puexceptioncode = getData("CheetahProcess", "Sheet1", m, 5);
				if (puexceptioncode.equals("PBC")) {

				}
				m++;
				puexceptioncode = getData("CheetahProcess", "Sheet1", m, 5);
				if (puexceptioncode.equals("PCR")) {

				}
				m++;
				puexceptioncode = getData("CheetahProcess", "Sheet1", m, 5);
				if (puexceptioncode.equals("PNO")) {

				}
				m++;
				puexceptioncode = getData("CheetahProcess", "Sheet1", m, 5);
				if (puexceptioncode.equals("PBA")) {

				}
				m++;
				puexceptioncode = getData("CheetahProcess", "Sheet1", m, 5);
				if (puexceptioncode.equals("PRD")) {

				}

			}

			else if (i == 6) // Delivery Exception
			{
				String SHipTrackNo = getData("CheetahProcess", "Sheet1", i, 2);
				msg.append("Shipment Tracking No==" + SHipTrackNo + "\n");
				msg.append("CASE6: DeliveryException :- " + "\n");

				int n = 12;

				String dlexceptioncode = getData("CheetahProcess", "Sheet1", n, 5);
				if (dlexceptioncode.equals("DBA")) {

					String ShipmentTracking1 = getData("CheetahProcess", "Sheet1", n, 2);
					driver.findElement(By.id("MainContent_txtShipTrackNum")).clear();
					driver.findElement(By.id("MainContent_txtShipTrackNum")).sendKeys(ShipmentTracking1);

					Thread.sleep(1000);
					confirmEd(n);

					pickedUp(n);
					pustatusUpdate(n);
					String ShipmentTracking = getData("CheetahProcess", "Sheet1", n, 2);
					driver.findElement(By.id("MainContent_txtShipTrackNum")).clear();
					driver.findElement(By.id("MainContent_txtShipTrackNum")).sendKeys(ShipmentTracking);

					Thread.sleep(1000);

					driver.findElement(By.id("MainContent_txtShipStatus")).clear();
					driver.findElement(By.id("MainContent_txtShipStatus")).sendKeys("Delivery Exception");
					driver.findElement(By.id("MainContent_txtEventDescription")).clear();
					driver.findElement(By.id("MainContent_txtStatusGridDescription")).clear();
					driver.findElement(By.id("MainContent_txtStatusBarDescription")).clear();

					driver.findElement(By.id("MainContent_txtExcpCode")).clear();
					driver.findElement(By.id("MainContent_txtExcpCode")).sendKeys("DBA");

					new Select(driver.findElement(By.id("MainContent_ddlActionFlag")))
							.selectByVisibleText("Process Only");
					driver.findElement(By.id("MainContent_txtSequenceId")).clear();
					driver.findElement(By.id("MainContent_txtSequenceId")).sendKeys("17");

					driver.findElement(By.id("MainContent_btnSubmit")).click();
					Thread.sleep(1000);

					// --Wait for status
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("MainContent_lblRespStatus")));
					WebElement RespStatus = driver.findElement(By.id("MainContent_lblRespStatus"));
					act.moveToElement(RespStatus).build().perform();
					if (RespStatus.isDisplayed()) {
						String ResponseStatus = RespStatus.getText();
						System.out.println("Response Message==" + ResponseStatus + "\n");
						String ShipTrackNo = driver.findElement(By.id("MainContent_lblShipTrackNum")).getText();
						msg.append("ShipmentTrackNo== " + ShipTrackNo + "\n");

						if (ResponseStatus.equalsIgnoreCase("OK")) {
							msg.append("DBA : PASS " + "\n");
							msg.append("Response==" + ResponseStatus + "\n\n\n");
							setData("CheetahProcess", "Sheet1", n, 20, "PASS");

						} else {
							msg.append("DBA : FAIL " + "\n");
							msg.append("Response==" + ResponseStatus + "\n\n\n");
							setData("CheetahProcess", "Sheet1", n, 20, "FAIL");
							getScreenshot(driver, "DBA_Fail_" + ShipTrackNo);

						}
					} else {
						System.out.println("Response Message is not displayed");
						msg.append("Response Message is not displayed");

					}
					deliverEd(n);
					dlstatusUpdate(n);
				}

				n++;
				dlexceptioncode = getData("CheetahProcess", "Sheet1", n, 5);

				if (dlexceptioncode.equals("DCR")) {

					String ShipmentTracking1 = getData("CheetahProcess", "Sheet1", n, 2);
					driver.findElement(By.id("MainContent_txtShipTrackNum")).clear();
					driver.findElement(By.id("MainContent_txtShipTrackNum")).sendKeys(ShipmentTracking1);

					Thread.sleep(1000);
					confirmEd(n);

					pickedUp(n);
					pustatusUpdate(n);
					String ShipmentTracking = getData("CheetahProcess", "Sheet1", n, 2);
					driver.findElement(By.id("MainContent_txtShipTrackNum")).clear();
					driver.findElement(By.id("MainContent_txtShipTrackNum")).sendKeys(ShipmentTracking);

					Thread.sleep(1000);

					driver.findElement(By.id("MainContent_txtShipStatus")).clear();
					driver.findElement(By.id("MainContent_txtShipStatus")).sendKeys("Delivery Exception");
					driver.findElement(By.id("MainContent_txtEventDescription")).clear();
					driver.findElement(By.id("MainContent_txtStatusGridDescription")).clear();
					driver.findElement(By.id("MainContent_txtStatusBarDescription")).clear();

					driver.findElement(By.id("MainContent_txtExcpCode")).clear();
					driver.findElement(By.id("MainContent_txtExcpCode")).sendKeys("DCR");

					new Select(driver.findElement(By.id("MainContent_ddlActionFlag")))
							.selectByVisibleText("Process Only");
					driver.findElement(By.id("MainContent_txtSequenceId")).clear();
					driver.findElement(By.id("MainContent_txtSequenceId")).sendKeys("17");

					driver.findElement(By.id("MainContent_btnSubmit")).click();
					Thread.sleep(1000);
					// --Wait for status
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("MainContent_lblRespStatus")));
					WebElement RespStatus = driver.findElement(By.id("MainContent_lblRespStatus"));
					act.moveToElement(RespStatus).build().perform();
					if (RespStatus.isDisplayed()) {
						String ResponseStatus = RespStatus.getText();
						System.out.println("Response Message==" + ResponseStatus + "\n");
						String ShipTrackNo = driver.findElement(By.id("MainContent_lblShipTrackNum")).getText();
						msg.append("ShipmentTrackNo== " + ShipTrackNo + "\n");

						if (ResponseStatus.equalsIgnoreCase("OK")) {
							msg.append("DCR : PASS " + "\n");
							msg.append("Response==" + ResponseStatus + "\n\n\n");
							setData("CheetahProcess", "Sheet1", n, 21, "PASS");

						} else {
							msg.append("DCR : FAIL " + "\n");
							msg.append("Response==" + ResponseStatus + "\n\n\n");
							setData("CheetahProcess", "Sheet1", n, 21, "FAIL");
							getScreenshot(driver, "DCR_Fail_" + ShipTrackNo);

						}
					} else {
						System.out.println("Response Message is not displayed");
						msg.append("Response Message is not displayed");

					}

					deliverEd(n);
					dlstatusUpdate(n);
				}

				n++;
				dlexceptioncode = getData("CheetahProcess", "Sheet1", n, 5);

				if (dlexceptioncode.equals("DNO")) {

					String ShipmentTracking1 = getData("CheetahProcess", "Sheet1", n, 2);
					driver.findElement(By.id("MainContent_txtShipTrackNum")).clear();
					driver.findElement(By.id("MainContent_txtShipTrackNum")).sendKeys(ShipmentTracking1);

					Thread.sleep(1000);
					confirmEd(n);

					pickedUp(n);
					pustatusUpdate(n);
					String ShipmentTracking = getData("CheetahProcess", "Sheet1", n, 2);
					driver.findElement(By.id("MainContent_txtShipTrackNum")).clear();
					driver.findElement(By.id("MainContent_txtShipTrackNum")).sendKeys(ShipmentTracking);

					Thread.sleep(1000);

					driver.findElement(By.id("MainContent_txtShipStatus")).clear();
					driver.findElement(By.id("MainContent_txtShipStatus")).sendKeys("Delivery Exception");
					driver.findElement(By.id("MainContent_txtEventDescription")).clear();
					driver.findElement(By.id("MainContent_txtStatusGridDescription")).clear();
					driver.findElement(By.id("MainContent_txtStatusBarDescription")).clear();

					driver.findElement(By.id("MainContent_txtExcpCode")).clear();
					driver.findElement(By.id("MainContent_txtExcpCode")).sendKeys("DNO");

					new Select(driver.findElement(By.id("MainContent_ddlActionFlag")))
							.selectByVisibleText("Process Only");
					driver.findElement(By.id("MainContent_txtSequenceId")).clear();
					driver.findElement(By.id("MainContent_txtSequenceId")).sendKeys("17");

					driver.findElement(By.id("MainContent_btnSubmit")).click();
					Thread.sleep(1000);
					// --Wait for status
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("MainContent_lblRespStatus")));
					WebElement RespStatus = driver.findElement(By.id("MainContent_lblRespStatus"));
					act.moveToElement(RespStatus).build().perform();
					if (RespStatus.isDisplayed()) {
						String ResponseStatus = RespStatus.getText();
						System.out.println("Response Message==" + ResponseStatus + "\n");
						String ShipTrackNo = driver.findElement(By.id("MainContent_lblShipTrackNum")).getText();
						msg.append("ShipmentTrackNo== " + ShipTrackNo + "\n");

						if (ResponseStatus.equalsIgnoreCase("OK")) {
							msg.append("DNO : PASS " + "\n");
							msg.append("Response==" + ResponseStatus + "\n\n\n");
							setData("CheetahProcess", "Sheet1", n, 22, "PASS");

						} else {
							msg.append("DNO : FAIL " + "\n");
							msg.append("Response==" + ResponseStatus + "\n\n\n");
							setData("CheetahProcess", "Sheet1", n, 22, "FAIL");
							getScreenshot(driver, "DNO_Fail_" + ShipTrackNo);

						}
					} else {
						System.out.println("Response Message is not displayed");
						msg.append("Response Message is not displayed");

					}

					deliverEd(n);
					dlstatusUpdate(n);
				}

				n++;
				dlexceptioncode = getData("CheetahProcess", "Sheet1", n, 5);

				if (dlexceptioncode.equals("DRD")) {

					String ShipmentTracking1 = getData("CheetahProcess", "Sheet1", n, 2);
					driver.findElement(By.id("MainContent_txtShipTrackNum")).clear();
					driver.findElement(By.id("MainContent_txtShipTrackNum")).sendKeys(ShipmentTracking1);

					Thread.sleep(1000);
					confirmEd(n);

					pickedUp(n);
					pustatusUpdate(n);
					String ShipmentTracking = getData("CheetahProcess", "Sheet1", n, 2);
					driver.findElement(By.id("MainContent_txtShipTrackNum")).clear();
					driver.findElement(By.id("MainContent_txtShipTrackNum")).sendKeys(ShipmentTracking);

					Thread.sleep(1000);

					driver.findElement(By.id("MainContent_txtShipStatus")).clear();
					driver.findElement(By.id("MainContent_txtShipStatus")).sendKeys("Delivery Exception");
					driver.findElement(By.id("MainContent_txtEventDescription")).clear();
					driver.findElement(By.id("MainContent_txtStatusGridDescription")).clear();
					driver.findElement(By.id("MainContent_txtStatusBarDescription")).clear();

					driver.findElement(By.id("MainContent_txtExcpCode")).clear();
					driver.findElement(By.id("MainContent_txtExcpCode")).sendKeys("DRD");

					new Select(driver.findElement(By.id("MainContent_ddlActionFlag")))
							.selectByVisibleText("Process Only");
					driver.findElement(By.id("MainContent_txtSequenceId")).clear();
					driver.findElement(By.id("MainContent_txtSequenceId")).sendKeys("17");

					driver.findElement(By.id("MainContent_btnSubmit")).click();
					Thread.sleep(1000);
					// --Wait for status
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("MainContent_lblRespStatus")));
					WebElement RespStatus = driver.findElement(By.id("MainContent_lblRespStatus"));
					act.moveToElement(RespStatus).build().perform();
					if (RespStatus.isDisplayed()) {
						String ResponseStatus = RespStatus.getText();
						System.out.println("Response Message==" + ResponseStatus + "\n");
						String ShipTrackNo = driver.findElement(By.id("MainContent_lblShipTrackNum")).getText();
						msg.append("ShipmentTrackNo== " + ShipTrackNo + "\n");

						if (ResponseStatus.equalsIgnoreCase("OK")) {
							msg.append("DRD : PASS " + "\n");
							msg.append("Response==" + ResponseStatus + "\n\n\n");
							setData("CheetahProcess", "Sheet1", n, 23, "PASS");

						} else {
							msg.append("DRD : FAIL " + "\n");
							msg.append("Response==" + ResponseStatus + "\n\n\n");
							setData("CheetahProcess", "Sheet1", n, 23, "FAIL");
							getScreenshot(driver, "DRD_Fail_" + ShipTrackNo);

						}
					} else {
						System.out.println("Response Message is not displayed");
						msg.append("Response Message is not displayed");

					}

					deliverEd(n);
					dlstatusUpdate(n);
				}
				n++;
				dlexceptioncode = getData("CheetahProcess", "Sheet1", n, 5);

				if (dlexceptioncode.equals("SDRTS2")) // StatusRevision
				{

					String ShipmentTracking1 = getData("CheetahProcess", "Sheet1", n, 2);
					driver.findElement(By.id("MainContent_txtShipTrackNum")).clear();
					driver.findElement(By.id("MainContent_txtShipTrackNum")).sendKeys(ShipmentTracking1);

					Thread.sleep(1000);
					confirmEd(n);

					pickedUp(n);
					pustatusUpdate(n);
					String ShipmentTracking = getData("CheetahProcess", "Sheet1", n, 2);
					driver.findElement(By.id("MainContent_txtShipTrackNum")).clear();
					driver.findElement(By.id("MainContent_txtShipTrackNum")).sendKeys(ShipmentTracking);

					Thread.sleep(1000);

					driver.findElement(By.id("MainContent_txtShipStatus")).clear();
					driver.findElement(By.id("MainContent_txtShipStatus")).sendKeys("Delivery Exception");
					driver.findElement(By.id("MainContent_txtEventDescription")).clear();
					driver.findElement(By.id("MainContent_txtStatusGridDescription")).clear();
					driver.findElement(By.id("MainContent_txtStatusBarDescription")).clear();

					driver.findElement(By.id("MainContent_txtExcpCode")).clear();
					driver.findElement(By.id("MainContent_txtExcpCode")).sendKeys("DNO");

					new Select(driver.findElement(By.id("MainContent_ddlActionFlag")))
							.selectByVisibleText("Process Only");
					driver.findElement(By.id("MainContent_txtSequenceId")).clear();
					driver.findElement(By.id("MainContent_txtSequenceId")).sendKeys("17");

					driver.findElement(By.id("MainContent_btnSubmit")).click();
					Thread.sleep(1000);

					driver.findElement(By.id("MainContent_txtShipStatus")).clear();
					driver.findElement(By.id("MainContent_txtShipStatus")).sendKeys("StatusRevision");
					driver.findElement(By.id("MainContent_txtEventDescription")).clear();
					driver.findElement(By.id("MainContent_txtStatusGridDescription")).clear();
					driver.findElement(By.id("MainContent_txtStatusBarDescription")).clear();

					driver.findElement(By.id("MainContent_txtExcpCode")).clear();
					driver.findElement(By.id("MainContent_txtExcpCode")).sendKeys("SDRTS2");

					new Select(driver.findElement(By.id("MainContent_ddlActionFlag")))
							.selectByVisibleText("Process Only");
					driver.findElement(By.id("MainContent_txtSequenceId")).clear();
					driver.findElement(By.id("MainContent_txtSequenceId")).sendKeys("18");

					driver.findElement(By.id("MainContent_btnSubmit")).click();
					Thread.sleep(1000);

					// --Wait for status
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("MainContent_lblRespStatus")));
					WebElement RespStatus = driver.findElement(By.id("MainContent_lblRespStatus"));
					act.moveToElement(RespStatus).build().perform();
					if (RespStatus.isDisplayed()) {
						String ResponseStatus = RespStatus.getText();
						System.out.println("Response Message==" + ResponseStatus + "\n");
						String ShipTrackNo = driver.findElement(By.id("MainContent_lblShipTrackNum")).getText();
						msg.append("ShipmentTrackNo== " + ShipTrackNo + "\n");

						if (ResponseStatus.equalsIgnoreCase("OK")) {
							msg.append("SDRTS2 : PASS " + "\n");
							msg.append("Response==" + ResponseStatus + "\n\n\n");
							setData("CheetahProcess", "Sheet1", n, 24, "PASS");

						} else {
							msg.append("SDRTS2 : FAIL " + "\n");
							msg.append("Response==" + ResponseStatus + "\n\n\n");
							setData("CheetahProcess", "Sheet1", n, 24, "FAIL");
							getScreenshot(driver, "SDRTS2_Fail_" + ShipTrackNo);

						}
					} else {
						System.out.println("Response Message is not displayed");
						msg.append("Response Message is not displayed");

					}

					deliverEd(n);
					dlstatusUpdate(n);
				}
			}

			else if (i == 7) // WAIT TIME
			{
				String SHipTrackNo = getData("CheetahProcess", "Sheet1", i, 2);
				msg.append("Shipment Tracking No==" + SHipTrackNo + "\n");
				msg.append("CASE7: WAIT TIME :- " + "\n");

				int s = 18;

				String pwait = getData("CheetahProcess", "Sheet1", s, 6);
				if (pwait.equals("PICKUP_WAIT_TIME")) {

					String ShipmentTracking1 = getData("CheetahProcess", "Sheet1", s, 2);
					driver.findElement(By.id("MainContent_txtShipTrackNum")).clear();
					driver.findElement(By.id("MainContent_txtShipTrackNum")).sendKeys(ShipmentTracking1);

					Thread.sleep(1000);
					confirmEd(s);
					waitTimePu(s);
					pickedUp(s);
					pustatusUpdate(s);
					String ShipmentTracking = getData("CheetahProcess", "Sheet1", s, 2);
					driver.findElement(By.id("MainContent_txtShipTrackNum")).clear();
					driver.findElement(By.id("MainContent_txtShipTrackNum")).sendKeys(ShipmentTracking);

					Thread.sleep(1000);

					deliverEd(s);
					dlstatusUpdate(s);

				}

				s++;

				String dwait = getData("CheetahProcess", "Sheet1", s, 6);
				if (dwait.equals("DELIVERY_WAIT_TIME")) {

					String ShipmentTracking1 = getData("CheetahProcess", "Sheet1", s, 2);
					driver.findElement(By.id("MainContent_txtShipTrackNum")).clear();
					driver.findElement(By.id("MainContent_txtShipTrackNum")).sendKeys(ShipmentTracking1);

					Thread.sleep(1000);
					confirmEd(s);
					pickedUp(s);
					pustatusUpdate(s);
					String ShipmentTracking = getData("CheetahProcess", "Sheet1", s, 2);
					driver.findElement(By.id("MainContent_txtShipTrackNum")).clear();
					driver.findElement(By.id("MainContent_txtShipTrackNum")).sendKeys(ShipmentTracking);

					Thread.sleep(1000);
					waitTimedl(s);
					deliverEd(s);
					dlstatusUpdate(s);

				}

				s++;

				String rwait = getData("CheetahProcess", "Sheet1", s, 6);
				if (rwait.equals("RETURN_WAIT_TIME")) {

					String ShipmentTracking1 = getData("CheetahProcess", "Sheet1", s, 2);
					driver.findElement(By.id("MainContent_txtShipTrackNum")).clear();
					driver.findElement(By.id("MainContent_txtShipTrackNum")).sendKeys(ShipmentTracking1);

					Thread.sleep(1000);
					confirmEd(s);
					pickedUp(s);
					pustatusUpdate(s);
					String ShipmentTracking = getData("CheetahProcess", "Sheet1", s, 2);
					driver.findElement(By.id("MainContent_txtShipTrackNum")).clear();
					driver.findElement(By.id("MainContent_txtShipTrackNum")).sendKeys(ShipmentTracking);

					Thread.sleep(1000);
					waitTimertn(s);
					deliverEd(s);
					dlstatusUpdate(s);

				}

				s++;

			}

			else if (i == 8) // AddressCorrection PBAOK
			{

			}

			else if (i == 9) // AddressCorrection DBAOK
			{

			}

		}
		System.out.println("Cheetah Process END----");

		msg.append("Regards, - " + "\n");
		msg.append("Selenium Automation" + "\n\n\n");
		// Send Email
		Env = storage.getProperty("Env");
		String subject = "Selenium Automation Script: " + Env + " Cheetah Order Processing";

		// asharma@samyak.com,pgandhi@samyak.com,sdas@samyak.com,byagnik@samyak.com,pdoshi@samyak.com
		try {
			Email.sendMail("ravina.prajapati@samyak.com,asharma@samyak.com,parth.doshi@samyak.com", subject,
					msg.toString(), "");
		} catch (Exception ex) {
			Logger.getLogger(ShipmentCreation.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	public static void confirmEd(int row) throws Exception // CONFIRMED
	{
		WebDriverWait wait = new WebDriverWait(driver, 5);
		Actions act = new Actions(driver);
		Thread.sleep(1000);
		driver.findElement(By.id("MainContent_txtPkgTrackNum")).clear();
		driver.findElement(By.id("MainContent_txtEventDescription")).clear();
		driver.findElement(By.id("MainContent_txtStatusGridDescription")).clear();
		driver.findElement(By.id("MainContent_txtStatusBarDescription")).clear();
		driver.findElement(By.id("MainContent_txtExcpCode")).clear();
		driver.findElement(By.id("MainContent_txtEventJsonData")).clear();

		Thread.sleep(1000);
		new Select(driver.findElement(By.id("MainContent_ddlActionFlag"))).selectByVisibleText("Process Only");

		driver.findElement(By.id("MainContent_txtSequenceId")).clear();
		driver.findElement(By.id("MainContent_txtSequenceId")).sendKeys("1");

		driver.findElement(By.id("MainContent_btnSubmit")).click();
		// --Wait for status
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("MainContent_lblRespStatus")));
		WebElement RespStatus = driver.findElement(By.id("MainContent_lblRespStatus"));
		act.moveToElement(RespStatus).build().perform();
		if (RespStatus.isDisplayed()) {
			String ResponseStatus = RespStatus.getText();
			System.out.println("Response Message==" + ResponseStatus + "\n");
			String ShipTrackNo = driver.findElement(By.id("MainContent_lblShipTrackNum")).getText();
			msg.append("ShipmentTrackNo== " + ShipTrackNo + "\n");
			;
			if (ResponseStatus.equalsIgnoreCase("OK")) {
				msg.append("Confirmed  : PASS " + "\n");
				msg.append("Response==" + ResponseStatus + "\n\n\n");
				setData("CheetahProcess", "Sheet1", row, 11, "PASS");

			} else {
				msg.append("Confirmed   : FAIL " + "\n");
				msg.append("Response==" + ResponseStatus + "\n\n\n");
				setData("CheetahProcess", "Sheet1", row, 11, "FAIL");
				getScreenshot(driver, "Confirmed_Fail_" + ShipTrackNo);

			}
		} else {
			System.out.println("Response Message is not displayed");
			msg.append("Response Message is not displayed");

		}

	}

	public static void pickedUp(int row) throws Exception // PICKEDUP
	{
		WebDriverWait wait = new WebDriverWait(driver, 5);
		Actions act = new Actions(driver);
		Thread.sleep(1000);
		driver.findElement(By.id("MainContent_txtShipStatus")).clear();
		driver.findElement(By.id("MainContent_txtShipStatus")).sendKeys("PICKEDUP");
		driver.findElement(By.id("MainContent_txtPkgTrackNum")).clear();
		driver.findElement(By.id("MainContent_txtEventDescription")).clear();
		driver.findElement(By.id("MainContent_txtStatusBarDescription")).clear();
		driver.findElement(By.id("MainContent_txtExcpCode")).clear();
		driver.findElement(By.id("MainContent_txtEventJsonData")).clear();
		new Select(driver.findElement(By.id("MainContent_ddlActionFlag"))).selectByVisibleText("Process Only");
		driver.findElement(By.id("MainContent_txtSequenceId")).clear();
		driver.findElement(By.id("MainContent_txtSequenceId")).sendKeys("6");
		Thread.sleep(1000);
		driver.findElement(By.id("MainContent_btnSubmit")).click();
		System.out.println("Pickedup Done !!");
		// --Wait for status
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("MainContent_lblRespStatus")));
		WebElement RespStatus = driver.findElement(By.id("MainContent_lblRespStatus"));
		act.moveToElement(RespStatus).build().perform();
		if (RespStatus.isDisplayed()) {
			String ResponseStatus = RespStatus.getText();
			System.out.println("Response Message==" + ResponseStatus + "\n");
			String ShipTrackNo = driver.findElement(By.id("MainContent_lblShipTrackNum")).getText();
			msg.append("ShipmentTrackNo== " + ShipTrackNo + "\n");

			if (ResponseStatus.equalsIgnoreCase("OK")) {
				msg.append("Pickedup  : PASS " + "\n");
				msg.append("Response==" + ResponseStatus + "\n\n\n");
				setData("CheetahProcess", "Sheet1", row, 12, "PASS");

			} else {
				msg.append("Pickedup   : FAIL " + "\n");
				msg.append("Response==" + ResponseStatus + "\n\n\n");
				setData("CheetahProcess", "Sheet1", row, 12, "FAIL");
				getScreenshot(driver, "PickedUp_Fail_" + ShipTrackNo);

			}
		} else {
			System.out.println("Response Message is not displayed");
			msg.append("Response Message is not displayed");

		}
	}

	public static void pustatusUpdate(int row) throws Exception // Status Update
	{
		WebDriverWait wait = new WebDriverWait(driver, 5);
		Actions act = new Actions(driver);
		Thread.sleep(1000);

		driver.findElement(By.id("MainContent_txtShipStatus")).clear();
		driver.findElement(By.id("MainContent_txtShipStatus")).sendKeys("Status Update");
		driver.findElement(By.id("MainContent_txtPkgTrackNum")).clear();
		driver.findElement(By.id("MainContent_txtStatusGridDescription")).clear();
		driver.findElement(By.id("MainContent_txtStatusGridDescription")).sendKeys("In Transit");

		driver.findElement(By.id("MainContent_txtStatusBarDescription")).clear();

		driver.findElement(By.id("MainContent_txtStatusBarDescription")).sendKeys("In Transit");
		driver.findElement(By.id("MainContent_txtEventJsonData")).clear();
		new Select(driver.findElement(By.id("MainContent_ddlActionFlag"))).selectByVisibleText("Process & Display");

		driver.findElement(By.id("MainContent_txtSequenceId")).clear();
		driver.findElement(By.id("MainContent_txtSequenceId")).sendKeys("13");
		Thread.sleep(1000);
		driver.findElement(By.id("MainContent_btnSubmit")).click();
		System.out.println("Pickup Status Update Done !!");
		// --Wait for status
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("MainContent_lblRespStatus")));
		WebElement RespStatus = driver.findElement(By.id("MainContent_lblRespStatus"));
		act.moveToElement(RespStatus).build().perform();
		if (RespStatus.isDisplayed()) {
			String ResponseStatus = RespStatus.getText();
			System.out.println("Response Message==" + ResponseStatus + "\n");
			String ShipTrackNo = driver.findElement(By.id("MainContent_lblShipTrackNum")).getText();
			msg.append("ShipmentTrackNo== " + ShipTrackNo + "\n");

			if (ResponseStatus.equalsIgnoreCase("OK")) {
				msg.append("Pickup Status Update  : PASS " + "\n");
				msg.append("Response==" + ResponseStatus + "\n\n\n");
				setData("CheetahProcess", "Sheet1", row, 13, "PASS");

			} else {
				msg.append("Pickup Status Update   : FAIL " + "\n");
				msg.append("Response==" + ResponseStatus + "\n\n\n");
				setData("CheetahProcess", "Sheet1", row, 13, "FAIL");
				getScreenshot(driver, "PUpStatusUpdate_Fail_" + ShipTrackNo);

			}
		} else {
			System.out.println("Response Message is not displayed");
			msg.append("Response Message is not displayed");

		}
	}

	public static void deliverEd(int row) throws Exception // DELIVERED
	{
		WebDriverWait wait = new WebDriverWait(driver, 5);
		Actions act = new Actions(driver);
		Thread.sleep(1000);
		driver.findElement(By.id("MainContent_txtShipStatus")).clear();
		driver.findElement(By.id("MainContent_txtShipStatus")).sendKeys("DELIVERED");
		driver.findElement(By.id("MainContent_txtPkgTrackNum")).clear();
		driver.findElement(By.id("MainContent_txtEventDescription")).clear();
		driver.findElement(By.id("MainContent_txtStatusGridDescription")).clear();
		driver.findElement(By.id("MainContent_txtStatusBarDescription")).clear();
		driver.findElement(By.id("MainContent_txtExcpCode")).clear();
		driver.findElement(By.id("MainContent_txtEventJsonData")).clear();
		new Select(driver.findElement(By.id("MainContent_ddlActionFlag"))).selectByVisibleText("Process Only");
		driver.findElement(By.id("MainContent_txtSequenceId")).clear();
		driver.findElement(By.id("MainContent_txtSequenceId")).sendKeys("18");
		Thread.sleep(1000);
		driver.findElement(By.id("MainContent_btnSubmit")).click();
		System.out.println("Delivered Done !!");
		// --Wait for status
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("MainContent_lblRespStatus")));
		WebElement RespStatus = driver.findElement(By.id("MainContent_lblRespStatus"));
		act.moveToElement(RespStatus).build().perform();
		if (RespStatus.isDisplayed()) {
			String ResponseStatus = RespStatus.getText();
			System.out.println("Response Message==" + ResponseStatus + "\n");
			String ShipTrackNo = driver.findElement(By.id("MainContent_lblShipTrackNum")).getText();
			msg.append("ShipmentTrackNo== " + ShipTrackNo + "\n");

			if (ResponseStatus.equalsIgnoreCase("OK")) {
				msg.append("Delivered  : PASS " + "\n");
				msg.append("Response==" + ResponseStatus + "\n\n\n");
				setData("CheetahProcess", "Sheet1", row, 14, "PASS");

			} else {
				msg.append("Delivered   : FAIL " + "\n");
				msg.append("Response==" + ResponseStatus + "\n\n\n");
				setData("CheetahProcess", "Sheet1", row, 14, "FAIL");
				getScreenshot(driver, "Delivered_Fail_" + ShipTrackNo);

			}
		} else {
			System.out.println("Response Message is not displayed");
			msg.append("Response Message is not displayed");

		}

	}

	public static void dlstatusUpdate(int row) throws Exception // Status Update
	{
		WebDriverWait wait = new WebDriverWait(driver, 5);
		Actions act = new Actions(driver);
		Thread.sleep(1000);
		driver.findElement(By.id("MainContent_txtShipStatus")).clear();
		driver.findElement(By.id("MainContent_txtShipStatus")).sendKeys("Status Update");
		driver.findElement(By.id("MainContent_txtPkgTrackNum")).clear();
		driver.findElement(By.id("MainContent_txtStatusGridDescription")).clear();
		driver.findElement(By.id("MainContent_txtStatusGridDescription")).sendKeys("Delivered");

		driver.findElement(By.id("MainContent_txtStatusBarDescription")).clear();
		driver.findElement(By.id("MainContent_txtExcpCode")).clear();
		driver.findElement(By.id("MainContent_txtStatusBarDescription")).sendKeys("Delivered");
		driver.findElement(By.id("MainContent_txtEventJsonData")).clear();
		new Select(driver.findElement(By.id("MainContent_ddlActionFlag"))).selectByVisibleText("Process & Display");

		driver.findElement(By.id("MainContent_txtSequenceId")).clear();
		driver.findElement(By.id("MainContent_txtSequenceId")).sendKeys("29");

		driver.findElement(By.id("MainContent_btnSubmit")).click();
		System.out.println("Delivery Status Update Done !!");
		// --Wait for status
		WebElement RespStatus = driver.findElement(By.id("MainContent_lblRespStatus"));
		act.moveToElement(RespStatus).build().perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("MainContent_lblRespStatus")));
		if (RespStatus.isDisplayed()) {
			String ResponseStatus = RespStatus.getText();
			System.out.println("Response Message==" + ResponseStatus + "\n");
			String ShipTrackNo = driver.findElement(By.id("MainContent_lblShipTrackNum")).getText();
			msg.append("ShipmentTrackNo== " + ShipTrackNo + "\n");

			if (ResponseStatus.equalsIgnoreCase("OK")) {
				msg.append("Delivery Status Update  : PASS " + "\n");
				msg.append("Response==" + ResponseStatus + "\n\n\n");
				setData("CheetahProcess", "Sheet1", row, 15, "PASS");

			} else {
				msg.append("Delivery Status Update   : FAIL " + "\n");
				msg.append("Response==" + ResponseStatus + "\n\n\n");
				setData("CheetahProcess", "Sheet1", row, 15, "FAIL");
				getScreenshot(driver, "DelStatusUpdate_Fail_" + ShipTrackNo);

			}
		} else {
			System.out.println("Response Message is not displayed");
			msg.append("Response Message is not displayed");

		}

	}

	public static void rejectEd(int row) throws Exception // REJECTED
	{
		WebDriverWait wait = new WebDriverWait(driver, 5);
		Actions act = new Actions(driver);
		Thread.sleep(1000);
		driver.findElement(By.id("MainContent_txtShipStatus")).clear();
		driver.findElement(By.id("MainContent_txtShipStatus")).sendKeys("Rejected");
		driver.findElement(By.id("MainContent_txtPkgTrackNum")).clear();
		driver.findElement(By.id("MainContent_txtEventDescription")).clear();
		driver.findElement(By.id("MainContent_txtStatusGridDescription")).clear();
		driver.findElement(By.id("MainContent_txtStatusBarDescription")).clear();
		driver.findElement(By.id("MainContent_txtEventJsonData")).clear();
		new Select(driver.findElement(By.id("MainContent_ddlActionFlag"))).selectByVisibleText("Process Only");
		driver.findElement(By.id("MainContent_txtSequenceId")).clear();
		driver.findElement(By.id("MainContent_txtSequenceId")).sendKeys("1");
		driver.findElement(By.id("MainContent_btnSubmit")).click();
		System.out.println("Job is Rejected from Cheetah Done !!");
		// --Wait for status
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("MainContent_lblRespStatus")));
		WebElement RespStatus = driver.findElement(By.id("MainContent_lblRespStatus"));
		act.moveToElement(RespStatus).build().perform();
		if (RespStatus.isDisplayed()) {
			String ResponseStatus = RespStatus.getText();
			System.out.println("Response Message==" + ResponseStatus + "\n");
			String ShipTrackNo = driver.findElement(By.id("MainContent_lblShipTrackNum")).getText();
			msg.append("ShipmentTrackNo== " + ShipTrackNo + "\n");

			if (ResponseStatus.equalsIgnoreCase("OK")) {
				msg.append("Rejected  : PASS " + "\n");
				msg.append("Response==" + ResponseStatus + "\n\n\n");
				setData("CheetahProcess", "Sheet1", row, 16, "PASS");

			} else {
				msg.append("Rejected   : FAIL " + "\n");
				msg.append("Response==" + ResponseStatus + "\n\n\n");
				setData("CheetahProcess", "Sheet1", row, 16, "FAIL");
				getScreenshot(driver, "Rejected_Fail_" + ShipTrackNo);

			}
		} else {
			System.out.println("Response Message is not displayed");
			msg.append("Response Message is not displayed");

		}

	}

	public static void packageDetailChange(int row) throws Exception // PackageDetailChange
	{
		WebDriverWait wait = new WebDriverWait(driver, 5);
		Actions act = new Actions(driver);

		Thread.sleep(1000);
		driver.findElement(By.id("MainContent_txtShipStatus")).clear();
		driver.findElement(By.id("MainContent_txtShipStatus")).sendKeys("PackageDetailChange");
		driver.findElement(By.id("MainContent_txtEventDescription")).clear();
		driver.findElement(By.id("MainContent_txtStatusGridDescription")).clear();
		driver.findElement(By.id("MainContent_txtStatusBarDescription")).clear();
		new Select(driver.findElement(By.id("MainContent_ddlActionFlag"))).selectByVisibleText("Process Only");
		driver.findElement(By.id("MainContent_txtSequenceId")).clear();
		driver.findElement(By.id("MainContent_txtSequenceId")).sendKeys("14");

		String EventJsonData = getData("CheetahProcess", "Sheet1", 3, 10);
		driver.findElement(By.id("MainContent_txtEventJsonData")).clear();
		driver.findElement(By.id("MainContent_txtEventJsonData")).sendKeys(EventJsonData);

		driver.findElement(By.id("MainContent_btnSubmit")).click();
		Thread.sleep(1000);
		System.out.println("Package Detail Change Done !!");
		// --Wait for status
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("MainContent_lblRespStatus")));
		WebElement RespStatus = driver.findElement(By.id("MainContent_lblRespStatus"));
		act.moveToElement(RespStatus).build().perform();
		if (RespStatus.isDisplayed()) {
			String ResponseStatus = RespStatus.getText();
			System.out.println("Response Message==" + ResponseStatus + "\n");
			String ShipTrackNo = driver.findElement(By.id("MainContent_lblShipTrackNum")).getText();
			msg.append("ShipmentTrackNo== " + ShipTrackNo + "\n");

			if (ResponseStatus.equalsIgnoreCase("OK")) {
				msg.append("PackageDetailChange  : PASS " + "\n");
				msg.append("Response==" + ResponseStatus + "\n\n\n");
				setData("CheetahProcess", "Sheet1", row, 17, "PASS");

			} else {
				msg.append("PackageDetailChange   : FAIL " + "\n");
				msg.append("Response==" + ResponseStatus + "\n\n\n");
				setData("CheetahProcess", "Sheet1", row, 17, "FAIL");
				getScreenshot(driver, "PackgDetailChange_Fail_" + ShipTrackNo);

			}
		} else {
			System.out.println("Response Message is not displayed");
			msg.append("Response Message is not displayed");

		}
	}

	public static void addPackage(int row) throws Exception // Add Package
	{
		WebDriverWait wait = new WebDriverWait(driver, 5);
		Actions act = new Actions(driver);

		Thread.sleep(1000);
		driver.findElement(By.id("MainContent_txtShipStatus")).clear();
		driver.findElement(By.id("MainContent_txtShipStatus")).sendKeys("AddPackage");
		driver.findElement(By.id("MainContent_txtEventDescription")).clear();
		driver.findElement(By.id("MainContent_txtStatusGridDescription")).clear();
		driver.findElement(By.id("MainContent_txtStatusBarDescription")).clear();
		new Select(driver.findElement(By.id("MainContent_ddlActionFlag"))).selectByVisibleText("Process Only");
		driver.findElement(By.id("MainContent_txtSequenceId")).clear();
		driver.findElement(By.id("MainContent_txtSequenceId")).sendKeys("15");
		String EventJsonData = getData("CheetahProcess", "Sheet1", 4, 10);
		driver.findElement(By.id("MainContent_txtEventJsonData")).clear();
		driver.findElement(By.id("MainContent_txtEventJsonData")).sendKeys(EventJsonData);

		driver.findElement(By.id("MainContent_btnSubmit")).click();
		Thread.sleep(1000);
		System.out.println("New Package Added Done !!");
		// --Wait for status
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("MainContent_lblRespStatus")));
		WebElement RespStatus = driver.findElement(By.id("MainContent_lblRespStatus"));
		act.moveToElement(RespStatus).build().perform();
		if (RespStatus.isDisplayed()) {
			String ResponseStatus = RespStatus.getText();
			System.out.println("Response Message==" + ResponseStatus + "\n");
			String ShipTrackNo = driver.findElement(By.id("MainContent_lblShipTrackNum")).getText();
			msg.append("ShipmentTrackNo== " + ShipTrackNo + "\n");

			if (ResponseStatus.equalsIgnoreCase("OK")) {
				msg.append("AddPackage  : PASS " + "\n");
				msg.append("Response==" + ResponseStatus + "\n\n\n");
				setData("CheetahProcess", "Sheet1", row, 18, "PASS");

			} else {
				msg.append("AddPackage   : FAIL " + "\n");
				msg.append("Response==" + ResponseStatus + "\n\n\n");
				setData("CheetahProcess", "Sheet1", row, 18, "FAIL");
				getScreenshot(driver, "AddPackage_Fail_" + ShipTrackNo);

			}
		} else {
			System.out.println("Response Message is not displayed");
			msg.append("Response Message is not displayed");

		}
	}

	public static void waitTimePu(int row) throws Exception // PICKUP_WAIT_TIME
	{
		WebDriverWait wait = new WebDriverWait(driver, 5);
		Actions act = new Actions(driver);
		Thread.sleep(1000);

		driver.findElement(By.id("MainContent_txtShipStatus")).clear();
		driver.findElement(By.id("MainContent_txtShipStatus")).sendKeys("PICKUP_WAIT_TIME");
		driver.findElement(By.id("MainContent_txtEventDescription")).clear();
		driver.findElement(By.id("MainContent_txtStatusGridDescription")).clear();
		driver.findElement(By.id("MainContent_txtStatusBarDescription")).clear();
		new Select(driver.findElement(By.id("MainContent_ddlActionFlag"))).selectByVisibleText("Process Only");
		driver.findElement(By.id("MainContent_txtSequenceId")).clear();
		driver.findElement(By.id("MainContent_txtSequenceId")).sendKeys("16");
		String EventJsonData = getData("CheetahProcess", "Sheet1", 18, 10);
		driver.findElement(By.id("MainContent_txtEventJsonData")).clear();
		driver.findElement(By.id("MainContent_txtEventJsonData")).sendKeys(EventJsonData);

		driver.findElement(By.id("MainContent_btnSubmit")).click();
		Thread.sleep(1000);
		System.out.println("PICKUP_WAIT_TIME Entered");
		// --Wait for status
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("MainContent_lblRespStatus")));
		WebElement RespStatus = driver.findElement(By.id("MainContent_lblRespStatus"));
		act.moveToElement(RespStatus).build().perform();
		if (RespStatus.isDisplayed()) {
			String ResponseStatus = RespStatus.getText();
			System.out.println("Response Message==" + ResponseStatus + "\n");
			String ShipTrackNo = driver.findElement(By.id("MainContent_lblShipTrackNum")).getText();
			msg.append("ShipmentTrackNo== " + ShipTrackNo + "\n");

			if (ResponseStatus.equalsIgnoreCase("OK")) {
				msg.append("PICKUP_WAIT_TIME   : PASS " + "\n");
				msg.append("Response==" + ResponseStatus + "\n\n\n");
				setData("CheetahProcess", "Sheet1", row, 27, "PASS");

			} else {
				msg.append("PICKUP_WAIT_TIME   : FAIL " + "\n");
				msg.append("Response==" + ResponseStatus + "\n\n\n");
				setData("CheetahProcess", "Sheet1", row, 27, "FAIL");
				getScreenshot(driver, "PUpWaitTime_Fail_" + ShipTrackNo);

			}
		} else {
			System.out.println("Response Message is not displayed");
			msg.append("Response Message is not displayed");

		}
	}

	public static void waitTimedl(int row) throws Exception // DELIVERY_WAIT_TIME
	{
		WebDriverWait wait = new WebDriverWait(driver, 5);
		Actions act = new Actions(driver);

		driver.findElement(By.id("MainContent_txtShipStatus")).clear();
		driver.findElement(By.id("MainContent_txtShipStatus")).sendKeys("DELIVERY_WAIT_TIME");
		driver.findElement(By.id("MainContent_txtEventDescription")).clear();
		driver.findElement(By.id("MainContent_txtStatusGridDescription")).clear();
		driver.findElement(By.id("MainContent_txtStatusBarDescription")).clear();
		new Select(driver.findElement(By.id("MainContent_ddlActionFlag"))).selectByVisibleText("Process Only");
		driver.findElement(By.id("MainContent_txtSequenceId")).clear();
		driver.findElement(By.id("MainContent_txtSequenceId")).sendKeys("16");
		String EventJsonData = getData("CheetahProcess", "Sheet1", 19, 10);
		driver.findElement(By.id("MainContent_txtEventJsonData")).clear();
		driver.findElement(By.id("MainContent_txtEventJsonData")).sendKeys(EventJsonData);

		driver.findElement(By.id("MainContent_btnSubmit")).click();
		Thread.sleep(1000);
		System.out.println("DELIVERY_WAIT_TIME Entered");
		// --Wait for status
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("MainContent_lblRespStatus")));
		WebElement RespStatus = driver.findElement(By.id("MainContent_lblRespStatus"));
		act.moveToElement(RespStatus).build().perform();
		if (RespStatus.isDisplayed()) {
			String ResponseStatus = RespStatus.getText();
			System.out.println("Response Message==" + ResponseStatus + "\n");
			String ShipTrackNo = driver.findElement(By.id("MainContent_lblShipTrackNum")).getText();
			msg.append("ShipmentTrackNo== " + ShipTrackNo + "\n");

			if (ResponseStatus.equalsIgnoreCase("OK")) {
				msg.append("DELIVERY_WAIT_TIME : PASS " + "\n");
				msg.append("Response==" + ResponseStatus + "\n\n\n");
				setData("CheetahProcess", "Sheet1", row, 28, "PASS");

			} else {
				msg.append("DELIVERY_WAIT_TIME : FAIL " + "\n");
				msg.append("Response==" + ResponseStatus + "\n\n\n");
				setData("CheetahProcess", "Sheet1", row, 28, "FAIL");
				getScreenshot(driver, "DelWaitTime_Fail_" + ShipTrackNo);

			}
		} else {
			System.out.println("Response Message is not displayed");
			msg.append("Response Message is not displayed");

		}
	}

	public static void waitTimertn(int row) throws Exception // RETURN_WAIT_TIME
	{
		WebDriverWait wait = new WebDriverWait(driver, 5);
		Actions act = new Actions(driver);

		Thread.sleep(1000);

		driver.findElement(By.id("MainContent_txtShipStatus")).clear();
		driver.findElement(By.id("MainContent_txtShipStatus")).sendKeys("RETURN_WAIT_TIME");
		driver.findElement(By.id("MainContent_txtEventDescription")).clear();
		driver.findElement(By.id("MainContent_txtStatusGridDescription")).clear();
		driver.findElement(By.id("MainContent_txtStatusBarDescription")).clear();
		new Select(driver.findElement(By.id("MainContent_ddlActionFlag"))).selectByVisibleText("Process Only");
		driver.findElement(By.id("MainContent_txtSequenceId")).clear();
		driver.findElement(By.id("MainContent_txtSequenceId")).sendKeys("16");
		String EventJsonData = getData("CheetahProcess", "Sheet1", 20, 10);
		driver.findElement(By.id("MainContent_txtEventJsonData")).clear();
		driver.findElement(By.id("MainContent_txtEventJsonData")).sendKeys(EventJsonData);

		driver.findElement(By.id("MainContent_btnSubmit")).click();
		Thread.sleep(1000);
		System.out.println("RETURN_WAIT_TIME Entered");
		// --Wait for status
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("MainContent_lblRespStatus")));
		WebElement RespStatus = driver.findElement(By.id("MainContent_lblRespStatus"));
		act.moveToElement(RespStatus).build().perform();
		if (RespStatus.isDisplayed()) {
			String ResponseStatus = RespStatus.getText();
			System.out.println("Response Message==" + ResponseStatus + "\n");
			String ShipTrackNo = driver.findElement(By.id("MainContent_lblShipTrackNum")).getText();
			msg.append("ShipmentTrackNo== " + ShipTrackNo + "\n");

			if (ResponseStatus.equalsIgnoreCase("OK")) {
				msg.append("RETURN_WAIT_TIME   : PASS " + "\n");
				msg.append("Response==" + ResponseStatus + "\n\n\n");
				setData("CheetahProcess", "Sheet1", row, 29, "PASS");

			} else {
				msg.append("RETURN_WAIT_TIME   : FAIL " + "\n");
				msg.append("Response==" + ResponseStatus + "\n\n\n");
				setData("CheetahProcess", "Sheet1", row, 29, "FAIL");
				getScreenshot(driver, "ReturnWaitTime_Fail_" + ShipTrackNo);

			}
		} else {
			System.out.println("Response Message is not displayed");
			msg.append("Response Message is not displayed");

		}
	}

	public static void waitForVisibilityOfElement(By objLocator, long lTime) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, lTime);
			wait.until(ExpectedConditions.visibilityOfElementLocated(objLocator));
		} catch (Exception e) {

		}
	}

	public static void waitForInVisibilityOfElement(By objLocator, long ITime) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(objLocator));
	}

}

package shipmentFedExWebSite;

import java.io.File;
import java.io.FileInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
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

		// driver.get("http://172.16.21.70:9077/publicServiceWebapplication/FlashWsV2.aspx");
		driver.get("http://10.20.104.82:9077/publicServiceWebapplication/FlashWsV2.aspx");

		Thread.sleep(4000);

		System.out.println("Cheetah Process Start----" + "\n");
		// Read data from Excel
		File src = new File(".\\src\\TestFiles\\CheetahProcessing.xlsx");
		FileInputStream fis = new FileInputStream(src);
		Workbook workbook = WorkbookFactory.create(fis);
		Sheet sh1 = workbook.getSheet("Sheet1");
		DataFormatter formatter = new DataFormatter();

		// 8
		for (int i = 7; i < 8; i++) {

			if (i == 1) // Normal Order Processing
			{

				String ShipmentTracking = formatter.formatCellValue(sh1.getRow(i).getCell(2));
				driver.findElement(By.id("MainContent_txtShipTrackNum")).clear();
				driver.findElement(By.id("MainContent_txtShipTrackNum")).sendKeys(ShipmentTracking);

				Thread.sleep(1000);
				confirmEd();

				Thread.sleep(4000);
				pickedUp();

				Thread.sleep(4000);
				pustatusUpdate();

				Thread.sleep(4000);
				deliverEd();

				Thread.sleep(4000);
				dlstatusUpdate();
				System.out.println("CASE1: Order Processing DONE !!");
			}

			else if (i == 2) // Rejected job from Cheetah
			{
				System.out.println("CASE2: [Rejected] Order Processing Initiated !!");
				String ShipmentTracking = formatter.formatCellValue(sh1.getRow(i).getCell(2));
				driver.findElement(By.id("MainContent_txtShipTrackNum")).clear();
				driver.findElement(By.id("MainContent_txtShipTrackNum")).sendKeys(ShipmentTracking);

				Thread.sleep(1000);
				rejectEd();
				System.out.println("CASE2: Rejected DONE !!");
			}

			else if (i == 3) // Package Detail Change
			{

				String ShipmentTracking = formatter.formatCellValue(sh1.getRow(i).getCell(2));
				driver.findElement(By.id("MainContent_txtShipTrackNum")).clear();
				driver.findElement(By.id("MainContent_txtShipTrackNum")).sendKeys(ShipmentTracking);

				String PackageTrackNum = formatter.formatCellValue(sh1.getRow(i).getCell(3));
				driver.findElement(By.id("MainContent_txtPkgTrackNum")).clear();
				driver.findElement(By.id("MainContent_txtPkgTrackNum")).sendKeys(PackageTrackNum);

				Thread.sleep(1000);

				packageDetailChange();
				pickedUp();
				pustatusUpdate();
				deliverEd();
				dlstatusUpdate();
				System.out.println("CASE3: [Package Detail Change] Order Processing Completed !!");
			}

			else if (i == 4) // Add Package
			{

				String ShipmentTracking = formatter.formatCellValue(sh1.getRow(i).getCell(2));
				driver.findElement(By.id("MainContent_txtShipTrackNum")).clear();
				driver.findElement(By.id("MainContent_txtShipTrackNum")).sendKeys(ShipmentTracking);

				Thread.sleep(1000);

				addPackage();
				pickedUp();
				pustatusUpdate();
				deliverEd();
				dlstatusUpdate();
				System.out.println("CASE4: [Add Package] Order Processing Completed !!");
			}

			else if (i == 5) // Pickup Exception
			{
				msg.append("CASE5: PickupException :- " + "\n");
				int l = 5;
				int m = 5;
				String puexceptioncode = formatter.formatCellValue(sh1.getRow(l).getCell(5));

				if (puexceptioncode.equals("PAT")) {
					String ShipmentTracking2 = formatter.formatCellValue(sh1.getRow(m).getCell(2));
					driver.findElement(By.id("MainContent_txtShipTrackNum")).clear();
					driver.findElement(By.id("MainContent_txtShipTrackNum")).sendKeys(ShipmentTracking2);

					Thread.sleep(1000);
					confirmEd();

					String ShipmentTracking = formatter.formatCellValue(sh1.getRow(m).getCell(2));
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
					String EventJsonData = formatter.formatCellValue(sh1.getRow(5).getCell(10));
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
						if (ResponseStatus.equalsIgnoreCase("OK")) {
							msg.append("PAT : PASS " + "\n");
							msg.append("Response==" + ResponseStatus + "\n\n\n");

						} else {
							msg.append("PAT : FAIL " + "\n");
							msg.append("Response==" + ResponseStatus + "\n\n\n");

						}
					} else {
						System.out.println("Response Message is not displayed");
						msg.append("Response Message is not displayed");

					}

					pickedUp();
					pustatusUpdate();
					deliverEd();
					dlstatusUpdate();

				}
				m++;
				l++;
				puexceptioncode = formatter.formatCellValue(sh1.getRow(l).getCell(5));

				if (puexceptioncode.equals("PPN")) {
					String ShipmentTracking2 = formatter.formatCellValue(sh1.getRow(m).getCell(2));
					driver.findElement(By.id("MainContent_txtShipTrackNum")).clear();
					driver.findElement(By.id("MainContent_txtShipTrackNum")).sendKeys(ShipmentTracking2);

					Thread.sleep(1000);
					confirmEd();

					String ShipmentTracking = formatter.formatCellValue(sh1.getRow(m).getCell(2));
					driver.findElement(By.id("MainContent_txtShipTrackNum")).clear();
					driver.findElement(By.id("MainContent_txtShipTrackNum")).sendKeys(ShipmentTracking);

					Thread.sleep(1000);

					String PackageTrackNum = formatter.formatCellValue(sh1.getRow(i).getCell(3));
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
						if (ResponseStatus.equalsIgnoreCase("OK")) {
							msg.append("PPN : PASS " + "\n");
							msg.append("Response==" + ResponseStatus + "\n\n\n");

						} else {
							msg.append("PPN : FAIL " + "\n");
							msg.append("Response==" + ResponseStatus + "\n\n\n");

						}
					} else {
						System.out.println("Response Message is not displayed");
						msg.append("Response Message is not displayed");

					}

					pickedUp();
					pustatusUpdate();
					deliverEd();
					dlstatusUpdate();

				}
				m++;
				l++;
				puexceptioncode = formatter.formatCellValue(sh1.getRow(l).getCell(5));
				if (puexceptioncode.equals("PBC")) {

				}
				m++;
				l++;
				puexceptioncode = formatter.formatCellValue(sh1.getRow(l).getCell(5));
				if (puexceptioncode.equals("PCR")) {

				}
				m++;
				l++;
				puexceptioncode = formatter.formatCellValue(sh1.getRow(l).getCell(5));
				if (puexceptioncode.equals("PNO")) {

				}
				m++;
				l++;
				puexceptioncode = formatter.formatCellValue(sh1.getRow(l).getCell(5));
				if (puexceptioncode.equals("PBA")) {

				}
				m++;
				l++;
				puexceptioncode = formatter.formatCellValue(sh1.getRow(l).getCell(5));
				if (puexceptioncode.equals("PRD")) {

				}

			}

			else if (i == 6) // Delivery Exception
			{
				msg.append("CASE6: DeliveryException :- " + "\n");

				int n = 12;
				int o = 12;

				String dlexceptioncode = formatter.formatCellValue(sh1.getRow(n).getCell(5));
				if (dlexceptioncode.equals("DBA")) {

					String ShipmentTracking1 = formatter.formatCellValue(sh1.getRow(o).getCell(2));
					driver.findElement(By.id("MainContent_txtShipTrackNum")).clear();
					driver.findElement(By.id("MainContent_txtShipTrackNum")).sendKeys(ShipmentTracking1);

					Thread.sleep(1000);
					confirmEd();

					pickedUp();
					pustatusUpdate();
					String ShipmentTracking = formatter.formatCellValue(sh1.getRow(o).getCell(2));
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
						if (ResponseStatus.equalsIgnoreCase("OK")) {
							msg.append("DBA : PASS " + "\n");
							msg.append("Response==" + ResponseStatus + "\n\n\n");

						} else {
							msg.append("DBA : FAIL " + "\n");
							msg.append("Response==" + ResponseStatus + "\n\n\n");

						}
					} else {
						System.out.println("Response Message is not displayed");
						msg.append("Response Message is not displayed");

					}
					deliverEd();
					dlstatusUpdate();
				}

				n++;
				o++;
				dlexceptioncode = formatter.formatCellValue(sh1.getRow(n).getCell(5));

				if (dlexceptioncode.equals("DCR")) {

					String ShipmentTracking1 = formatter.formatCellValue(sh1.getRow(o).getCell(2));
					driver.findElement(By.id("MainContent_txtShipTrackNum")).clear();
					driver.findElement(By.id("MainContent_txtShipTrackNum")).sendKeys(ShipmentTracking1);

					Thread.sleep(1000);
					confirmEd();

					pickedUp();
					pustatusUpdate();
					String ShipmentTracking = formatter.formatCellValue(sh1.getRow(o).getCell(2));
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
						if (ResponseStatus.equalsIgnoreCase("OK")) {
							msg.append("DCR : PASS " + "\n");
							msg.append("Response==" + ResponseStatus + "\n\n\n");

						} else {
							msg.append("DCR : FAIL " + "\n");
							msg.append("Response==" + ResponseStatus + "\n\n\n");

						}
					} else {
						System.out.println("Response Message is not displayed");
						msg.append("Response Message is not displayed");

					}

					deliverEd();
					dlstatusUpdate();
				}

				n++;
				o++;
				dlexceptioncode = formatter.formatCellValue(sh1.getRow(n).getCell(5));

				if (dlexceptioncode.equals("DNO")) {

					String ShipmentTracking1 = formatter.formatCellValue(sh1.getRow(o).getCell(2));
					driver.findElement(By.id("MainContent_txtShipTrackNum")).clear();
					driver.findElement(By.id("MainContent_txtShipTrackNum")).sendKeys(ShipmentTracking1);

					Thread.sleep(1000);
					confirmEd();

					pickedUp();
					pustatusUpdate();
					String ShipmentTracking = formatter.formatCellValue(sh1.getRow(o).getCell(2));
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
						if (ResponseStatus.equalsIgnoreCase("OK")) {
							msg.append("DNO : PASS " + "\n");
							msg.append("Response==" + ResponseStatus + "\n\n\n");

						} else {
							msg.append("DNO : FAIL " + "\n");
							msg.append("Response==" + ResponseStatus + "\n\n\n");

						}
					} else {
						System.out.println("Response Message is not displayed");
						msg.append("Response Message is not displayed");

					}

					deliverEd();
					dlstatusUpdate();
				}

				n++;
				o++;
				dlexceptioncode = formatter.formatCellValue(sh1.getRow(n).getCell(5));

				if (dlexceptioncode.equals("DRD")) {

					String ShipmentTracking1 = formatter.formatCellValue(sh1.getRow(o).getCell(2));
					driver.findElement(By.id("MainContent_txtShipTrackNum")).clear();
					driver.findElement(By.id("MainContent_txtShipTrackNum")).sendKeys(ShipmentTracking1);

					Thread.sleep(1000);
					confirmEd();

					pickedUp();
					pustatusUpdate();
					String ShipmentTracking = formatter.formatCellValue(sh1.getRow(o).getCell(2));
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
						if (ResponseStatus.equalsIgnoreCase("OK")) {
							msg.append("DRD : PASS " + "\n");
							msg.append("Response==" + ResponseStatus + "\n\n\n");

						} else {
							msg.append("DRD : FAIL " + "\n");
							msg.append("Response==" + ResponseStatus + "\n\n\n");

						}
					} else {
						System.out.println("Response Message is not displayed");
						msg.append("Response Message is not displayed");

					}

					deliverEd();
					dlstatusUpdate();
				}
				n++;
				o++;
				dlexceptioncode = formatter.formatCellValue(sh1.getRow(n).getCell(5));

				if (dlexceptioncode.equals("SDRTS2")) // StatusRevision
				{

					String ShipmentTracking1 = formatter.formatCellValue(sh1.getRow(o).getCell(2));
					driver.findElement(By.id("MainContent_txtShipTrackNum")).clear();
					driver.findElement(By.id("MainContent_txtShipTrackNum")).sendKeys(ShipmentTracking1);

					Thread.sleep(1000);
					confirmEd();

					pickedUp();
					pustatusUpdate();
					String ShipmentTracking = formatter.formatCellValue(sh1.getRow(o).getCell(2));
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
						if (ResponseStatus.equalsIgnoreCase("OK")) {
							msg.append("SDRTS2 : PASS " + "\n");
							msg.append("Response==" + ResponseStatus + "\n\n\n");

						} else {
							msg.append("SDRTS2 : FAIL " + "\n");
							msg.append("Response==" + ResponseStatus + "\n\n\n");

						}
					} else {
						System.out.println("Response Message is not displayed");
						msg.append("Response Message is not displayed");

					}

					deliverEd();
					dlstatusUpdate();
				}
			}

			else if (i == 7) // WAIT TIME
			{
				msg.append("CASE7: WAIT TIME :- " + "\n");

				int s = 18;
				int t = 18;

				String pwait = formatter.formatCellValue(sh1.getRow(s).getCell(6));
				if (pwait.equals("PICKUP_WAIT_TIME")) {

					String ShipmentTracking1 = formatter.formatCellValue(sh1.getRow(t).getCell(2));
					driver.findElement(By.id("MainContent_txtShipTrackNum")).clear();
					driver.findElement(By.id("MainContent_txtShipTrackNum")).sendKeys(ShipmentTracking1);

					Thread.sleep(1000);
					confirmEd();
					waitTimePu();
					pickedUp();
					pustatusUpdate();
					String ShipmentTracking = formatter.formatCellValue(sh1.getRow(t).getCell(2));
					driver.findElement(By.id("MainContent_txtShipTrackNum")).clear();
					driver.findElement(By.id("MainContent_txtShipTrackNum")).sendKeys(ShipmentTracking);

					Thread.sleep(1000);

					deliverEd();
					dlstatusUpdate();

				}

				s++;
				t++;

				String dwait = formatter.formatCellValue(sh1.getRow(s).getCell(6));
				if (dwait.equals("DELIVERY_WAIT_TIME")) {

					String ShipmentTracking1 = formatter.formatCellValue(sh1.getRow(t).getCell(2));
					driver.findElement(By.id("MainContent_txtShipTrackNum")).clear();
					driver.findElement(By.id("MainContent_txtShipTrackNum")).sendKeys(ShipmentTracking1);

					Thread.sleep(1000);
					confirmEd();
					pickedUp();
					pustatusUpdate();
					String ShipmentTracking = formatter.formatCellValue(sh1.getRow(t).getCell(2));
					driver.findElement(By.id("MainContent_txtShipTrackNum")).clear();
					driver.findElement(By.id("MainContent_txtShipTrackNum")).sendKeys(ShipmentTracking);

					Thread.sleep(1000);
					waitTimedl();
					deliverEd();
					dlstatusUpdate();

				}

				s++;
				t++;

				String rwait = formatter.formatCellValue(sh1.getRow(s).getCell(6));
				if (rwait.equals("RETURN_WAIT_TIME")) {

					String ShipmentTracking1 = formatter.formatCellValue(sh1.getRow(t).getCell(2));
					driver.findElement(By.id("MainContent_txtShipTrackNum")).clear();
					driver.findElement(By.id("MainContent_txtShipTrackNum")).sendKeys(ShipmentTracking1);

					Thread.sleep(1000);
					confirmEd();
					pickedUp();
					pustatusUpdate();
					String ShipmentTracking = formatter.formatCellValue(sh1.getRow(t).getCell(2));
					driver.findElement(By.id("MainContent_txtShipTrackNum")).clear();
					driver.findElement(By.id("MainContent_txtShipTrackNum")).sendKeys(ShipmentTracking);

					Thread.sleep(1000);
					waitTimertn();
					deliverEd();
					dlstatusUpdate();

				}

				s++;
				t++;

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
		String subject = "Selenium Automation Script: STAGING Cheetah Order Processing";

		// asharma@samyak.com,pgandhi@samyak.com,sdas@samyak.com,byagnik@samyak.com,pdoshi@samyak.com
		try {
			Email.sendMail("ravina.prajapati@samyak.com,asharma@samyak.com,parth.doshi@samyak.com", subject,
					msg.toString(), "");
		} catch (Exception ex) {
			Logger.getLogger(ShipmentCreation.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	public static void confirmEd() throws Exception // CONFIRMED
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
			if (ResponseStatus.equalsIgnoreCase("OK")) {
				msg.append("Confirmed  : PASS " + "\n");
				msg.append("Response==" + ResponseStatus + "\n\n\n");

			} else {
				msg.append("Confirmed   : FAIL " + "\n");
				msg.append("Response==" + ResponseStatus + "\n\n\n");

			}
		} else {
			System.out.println("Response Message is not displayed");
			msg.append("Response Message is not displayed");

		}

	}

	public static void pickedUp() throws Exception // PICKEDUP
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
			if (ResponseStatus.equalsIgnoreCase("OK")) {
				msg.append("Pickedup  : PASS " + "\n");
				msg.append("Response==" + ResponseStatus + "\n\n\n");

			} else {
				msg.append("Pickedup   : FAIL " + "\n");
				msg.append("Response==" + ResponseStatus + "\n\n\n");

			}
		} else {
			System.out.println("Response Message is not displayed");
			msg.append("Response Message is not displayed");

		}
	}

	public static void pustatusUpdate() throws Exception // Status Update
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
			if (ResponseStatus.equalsIgnoreCase("OK")) {
				msg.append("Pickup Status Update  : PASS " + "\n");
				msg.append("Response==" + ResponseStatus + "\n\n\n");

			} else {
				msg.append("Pickup Status Update   : FAIL " + "\n");
				msg.append("Response==" + ResponseStatus + "\n\n\n");

			}
		} else {
			System.out.println("Response Message is not displayed");
			msg.append("Response Message is not displayed");

		}
	}

	public static void deliverEd() throws Exception // DELIVERED
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
			if (ResponseStatus.equalsIgnoreCase("OK")) {
				msg.append("Delivered  : PASS " + "\n");
				msg.append("Response==" + ResponseStatus + "\n\n\n");

			} else {
				msg.append("Delivered   : FAIL " + "\n");
				msg.append("Response==" + ResponseStatus + "\n\n\n");

			}
		} else {
			System.out.println("Response Message is not displayed");
			msg.append("Response Message is not displayed");

		}

	}

	public static void dlstatusUpdate() throws Exception // Status Update
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
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("MainContent_lblRespStatus")));
		WebElement RespStatus = driver.findElement(By.id("MainContent_lblRespStatus"));
		act.moveToElement(RespStatus).build().perform();
		if (RespStatus.isDisplayed()) {
			String ResponseStatus = RespStatus.getText();
			System.out.println("Response Message==" + ResponseStatus + "\n");
			if (ResponseStatus.equalsIgnoreCase("OK")) {
				msg.append("Delivery Status Update  : PASS " + "\n");
				msg.append("Response==" + ResponseStatus + "\n\n\n");

			} else {
				msg.append("Delivery Status Update   : FAIL " + "\n");
				msg.append("Response==" + ResponseStatus + "\n\n\n");

			}
		} else {
			System.out.println("Response Message is not displayed");
			msg.append("Response Message is not displayed");

		}

	}

	public static void rejectEd() throws Exception // REJECTED
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
			if (ResponseStatus.equalsIgnoreCase("OK")) {
				msg.append("Rejected  : PASS " + "\n");
				msg.append("Response==" + ResponseStatus + "\n\n\n");

			} else {
				msg.append("Rejected   : FAIL " + "\n");
				msg.append("Response==" + ResponseStatus + "\n\n\n");

			}
		} else {
			System.out.println("Response Message is not displayed");
			msg.append("Response Message is not displayed");

		}

	}

	public static void packageDetailChange() throws Exception // PackageDetailChange
	{
		WebDriverWait wait = new WebDriverWait(driver, 5);
		Actions act = new Actions(driver);

		File src = new File(".\\src\\TestFiles\\CheetahProcessing.xlsx");
		FileInputStream fis = new FileInputStream(src);
		Workbook workbook = WorkbookFactory.create(fis);
		Sheet sh1 = workbook.getSheet("Sheet1");
		DataFormatter formatter = new DataFormatter();

		Thread.sleep(1000);
		driver.findElement(By.id("MainContent_txtShipStatus")).clear();
		driver.findElement(By.id("MainContent_txtShipStatus")).sendKeys("PackageDetailChange");
		driver.findElement(By.id("MainContent_txtEventDescription")).clear();
		driver.findElement(By.id("MainContent_txtStatusGridDescription")).clear();
		driver.findElement(By.id("MainContent_txtStatusBarDescription")).clear();
		new Select(driver.findElement(By.id("MainContent_ddlActionFlag"))).selectByVisibleText("Process Only");
		driver.findElement(By.id("MainContent_txtSequenceId")).clear();
		driver.findElement(By.id("MainContent_txtSequenceId")).sendKeys("14");

		String EventJsonData = formatter.formatCellValue(sh1.getRow(3).getCell(10));
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
			if (ResponseStatus.equalsIgnoreCase("OK")) {
				msg.append("PackageDetailChange  : PASS " + "\n");
				msg.append("Response==" + ResponseStatus + "\n\n\n");

			} else {
				msg.append("PackageDetailChange   : FAIL " + "\n");
				msg.append("Response==" + ResponseStatus + "\n\n\n");

			}
		} else {
			System.out.println("Response Message is not displayed");
			msg.append("Response Message is not displayed");

		}
	}

	public static void addPackage() throws Exception // Add Package
	{
		WebDriverWait wait = new WebDriverWait(driver, 5);
		Actions act = new Actions(driver);

		File src = new File(".\\src\\TestFiles\\CheetahProcessing.xlsx");
		FileInputStream fis = new FileInputStream(src);
		Workbook workbook = WorkbookFactory.create(fis);
		Sheet sh1 = workbook.getSheet("Sheet1");
		DataFormatter formatter = new DataFormatter();

		Thread.sleep(1000);
		driver.findElement(By.id("MainContent_txtShipStatus")).clear();
		driver.findElement(By.id("MainContent_txtShipStatus")).sendKeys("AddPackage");
		driver.findElement(By.id("MainContent_txtEventDescription")).clear();
		driver.findElement(By.id("MainContent_txtStatusGridDescription")).clear();
		driver.findElement(By.id("MainContent_txtStatusBarDescription")).clear();
		new Select(driver.findElement(By.id("MainContent_ddlActionFlag"))).selectByVisibleText("Process Only");
		driver.findElement(By.id("MainContent_txtSequenceId")).clear();
		driver.findElement(By.id("MainContent_txtSequenceId")).sendKeys("15");
		String EventJsonData = formatter.formatCellValue(sh1.getRow(4).getCell(10));
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
			if (ResponseStatus.equalsIgnoreCase("OK")) {
				msg.append("AddPackage  : PASS " + "\n");
				msg.append("Response==" + ResponseStatus + "\n\n\n");

			} else {
				msg.append("AddPackage   : FAIL " + "\n");
				msg.append("Response==" + ResponseStatus + "\n\n\n");

			}
		} else {
			System.out.println("Response Message is not displayed");
			msg.append("Response Message is not displayed");

		}
	}

	public static void waitTimePu() throws Exception // PICKUP_WAIT_TIME
	{
		WebDriverWait wait = new WebDriverWait(driver, 5);
		Actions act = new Actions(driver);

		File src = new File(".\\src\\TestFiles\\CheetahProcessing.xlsx");
		FileInputStream fis = new FileInputStream(src);
		Workbook workbook = WorkbookFactory.create(fis);
		Sheet sh1 = workbook.getSheet("Sheet1");
		DataFormatter formatter = new DataFormatter();

		Thread.sleep(1000);

		driver.findElement(By.id("MainContent_txtShipStatus")).clear();
		driver.findElement(By.id("MainContent_txtShipStatus")).sendKeys("PICKUP_WAIT_TIME");
		driver.findElement(By.id("MainContent_txtEventDescription")).clear();
		driver.findElement(By.id("MainContent_txtStatusGridDescription")).clear();
		driver.findElement(By.id("MainContent_txtStatusBarDescription")).clear();
		new Select(driver.findElement(By.id("MainContent_ddlActionFlag"))).selectByVisibleText("Process Only");
		driver.findElement(By.id("MainContent_txtSequenceId")).clear();
		driver.findElement(By.id("MainContent_txtSequenceId")).sendKeys("16");
		String EventJsonData = formatter.formatCellValue(sh1.getRow(18).getCell(10));
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
			if (ResponseStatus.equalsIgnoreCase("OK")) {
				msg.append("PICKUP_WAIT_TIME   : PASS " + "\n");
				msg.append("Response==" + ResponseStatus + "\n\n\n");

			} else {
				msg.append("PICKUP_WAIT_TIME   : FAIL " + "\n");
				msg.append("Response==" + ResponseStatus + "\n\n\n");

			}
		} else {
			System.out.println("Response Message is not displayed");
			msg.append("Response Message is not displayed");

		}
	}

	public static void waitTimedl() throws Exception // DELIVERY_WAIT_TIME
	{
		WebDriverWait wait = new WebDriverWait(driver, 5);
		Actions act = new Actions(driver);

		File src = new File(".\\src\\TestFiles\\CheetahProcessing.xlsx");
		FileInputStream fis = new FileInputStream(src);
		Workbook workbook = WorkbookFactory.create(fis);
		Sheet sh1 = workbook.getSheet("Sheet1");
		DataFormatter formatter = new DataFormatter();

		Thread.sleep(1000);

		driver.findElement(By.id("MainContent_txtShipStatus")).clear();
		driver.findElement(By.id("MainContent_txtShipStatus")).sendKeys("DELIVERY_WAIT_TIME");
		driver.findElement(By.id("MainContent_txtEventDescription")).clear();
		driver.findElement(By.id("MainContent_txtStatusGridDescription")).clear();
		driver.findElement(By.id("MainContent_txtStatusBarDescription")).clear();
		new Select(driver.findElement(By.id("MainContent_ddlActionFlag"))).selectByVisibleText("Process Only");
		driver.findElement(By.id("MainContent_txtSequenceId")).clear();
		driver.findElement(By.id("MainContent_txtSequenceId")).sendKeys("16");
		String EventJsonData = formatter.formatCellValue(sh1.getRow(19).getCell(10));
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
			if (ResponseStatus.equalsIgnoreCase("OK")) {
				msg.append("DELIVERY_WAIT_TIME : PASS " + "\n");
				msg.append("Response==" + ResponseStatus + "\n\n\n");

			} else {
				msg.append("DELIVERY_WAIT_TIME : FAIL " + "\n");
				msg.append("Response==" + ResponseStatus + "\n\n\n");

			}
		} else {
			System.out.println("Response Message is not displayed");
			msg.append("Response Message is not displayed");

		}
	}

	public static void waitTimertn() throws Exception // RETURN_WAIT_TIME
	{
		WebDriverWait wait = new WebDriverWait(driver, 5);
		Actions act = new Actions(driver);

		File src = new File(".\\src\\TestFiles\\CheetahProcessing.xlsx");
		FileInputStream fis = new FileInputStream(src);
		Workbook workbook = WorkbookFactory.create(fis);
		Sheet sh1 = workbook.getSheet("Sheet1");
		DataFormatter formatter = new DataFormatter();

		Thread.sleep(1000);

		driver.findElement(By.id("MainContent_txtShipStatus")).clear();
		driver.findElement(By.id("MainContent_txtShipStatus")).sendKeys("RETURN_WAIT_TIME");
		driver.findElement(By.id("MainContent_txtEventDescription")).clear();
		driver.findElement(By.id("MainContent_txtStatusGridDescription")).clear();
		driver.findElement(By.id("MainContent_txtStatusBarDescription")).clear();
		new Select(driver.findElement(By.id("MainContent_ddlActionFlag"))).selectByVisibleText("Process Only");
		driver.findElement(By.id("MainContent_txtSequenceId")).clear();
		driver.findElement(By.id("MainContent_txtSequenceId")).sendKeys("16");
		String EventJsonData = formatter.formatCellValue(sh1.getRow(20).getCell(10));
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
			if (ResponseStatus.equalsIgnoreCase("OK")) {
				msg.append("RETURN_WAIT_TIME   : PASS " + "\n");
				msg.append("Response==" + ResponseStatus + "\n\n\n");

			} else {
				msg.append("RETURN_WAIT_TIME   : FAIL " + "\n");
				msg.append("Response==" + ResponseStatus + "\n\n\n");

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

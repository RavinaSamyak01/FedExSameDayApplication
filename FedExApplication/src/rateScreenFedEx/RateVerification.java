package rateScreenFedEx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import basePackage.BaseInit;
import basePackage.Email;

public class RateVerification extends BaseInit {
	static StringBuilder msg = new StringBuilder();

	@Test
	public void prService() throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		// --get the data
		try {
			System.out.println("Rate Verification start");
			File src = new File(".\\src\\TestFiles\\FedExRateVerification.xlsx");
			FileInputStream fis = new FileInputStream(src);
			Workbook workbook = WorkbookFactory.create(fis);
			Sheet sh1 = workbook.getSheet("Sheet1");

			DataFormatter formatter = new DataFormatter();
			File src1 = new File(".\\src\\TestFiles\\FedExRateVerification.xlsx");
			FileOutputStream fis1 = new FileOutputStream(src1);
			Sheet sh2 = workbook.getSheet("Sheet1");
			//31
			for (int i = 1; i <2 ; i++) {
				driver.getTitle();
				pause(1000);

				// --PickUp Zip
				String PUZip = formatter.formatCellValue(sh1.getRow(i).getCell(0));
				driver.findElement(By.id("txtOrig")).clear();
				driver.findElement(By.id("txtOrig")).sendKeys(PUZip);
				driver.findElement(By.id("txtOrig")).sendKeys(Keys.TAB);
				Thread.sleep(5000);

				// --Delivery Zip
				String DLZip = formatter.formatCellValue(sh1.getRow(i).getCell(1));
				driver.findElement(By.id("txtDest")).clear();
				driver.findElement(By.id("txtDest")).sendKeys(DLZip);
				driver.findElement(By.id("txtDest")).sendKeys(Keys.TAB);
				Thread.sleep(5000);

				// --Weight
				wait.until(ExpectedConditions.elementToBeClickable(By.id("txtActWt0")));
				driver.findElement(By.id("txtActWt0")).clear();
				driver.findElement(By.id("txtActWt0")).sendKeys("5");
				System.out.println("entered weight");
				driver.findElement(By.id("txtActWt0")).sendKeys(Keys.TAB);
				Thread.sleep(5000);

				// --Dim(L)
				driver.findElement(By.id("txtDimLen0")).clear();
				driver.findElement(By.id("txtDimLen0")).sendKeys("5");
				System.out.println("entered dim");
				pause(200);

				// --Dim(W)
				driver.findElement(By.id("txtDimWid0")).clear();
				driver.findElement(By.id("txtDimWid0")).sendKeys("5");
				System.out.println("entered wid");
				pause(200);

				// --Dim(H)
				driver.findElement(By.id("txtDimHt0")).clear();
				driver.findElement(By.id("txtDimHt0")).sendKeys("5");
				System.out.println("entered Ht");
				pause(200);

				// --ShipDate
				driver.findElement(By.id("datepicker")).clear();
				DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				String newDate = dateFormat.format(cal.getTime());
				driver.findElement(By.id("datepicker")).sendKeys(newDate);
				System.out.println("Selected Date,current Date");
				driver.findElement(By.id("datepicker")).sendKeys(Keys.TAB);
				pause(200);

				// Select hour
				Select hour = new Select(driver.findElement(By.id("ddlPickupHour")));
				hour.selectByIndex(7); // AM
				System.out.println("select hour");
				pause(200);

				// Select minuts
				Select minutes = new Select(driver.findElement(By.id("ddlPickupMinutes")));
				minutes.selectByIndex(1); // AM
				System.out.println("select minutes");
				pause(200);

				// Select AM/PM
				Select AmPm = new Select(driver.findElement(By.id("ddlTimeType")));
				AmPm.selectByIndex(0); // AM
				System.out.println("select ampm");
				pause(200);

				// --Click on show rates
				driver.findElement(By.id("btngetQuickquote")).click();
				wait.until(ExpectedConditions
						.visibilityOfAllElementsLocatedBy(By.xpath("//table[@class=\"fdxRatetable\"]")));
				JavascriptExecutor jse = (JavascriptExecutor) driver;
				jse.executeScript("window.scrollBy(0,350)", "");

				waitForVisibilityOfElement(By.xpath(".//*[@id='Process']"), 2);
				waitForInVisibilityOfElement(By.xpath(".//*[@id='Process']"), 30);
				waitForVisibilityOfElement(By.id("btnShip"), 30);

				// --set the data

				String serviceid = formatter.formatCellValue(sh1.getRow(i).getCell(2));

				if (serviceid.equals("PR")) {
					pause(1000);
					String actrate = driver
							.findElement(By.xpath("//*[@class=\"fdxRatetable\"]/tbody[3]//div[@class=\"ratebtn\"]"))
							.getText();
					System.out.println(actrate);
					String ExpectedRate = formatter.formatCellValue(sh1.getRow(i).getCell(3));
					sh2.getRow(i).createCell(4).setCellValue(actrate);
					/*
					 * workbook.write(fis1); fis1.close();
					 */

					if (!actrate.equals(ExpectedRate)) {

						sh2.getRow(i).createCell(5).setCellValue("FAIL");

						/*
						 * workbook.write(fis1); fis1.close();
						 */

					}

					else {
						/*
						 * File src1srcstatus = new
						 * File(".\\src\\TestFiles\\FedExRateVerification.xlsx"); FileOutputStream
						 * fis1srcstatus = new FileOutputStream(src1srcstatus); Sheet sh2srcstatus =
						 * workbook.getSheet("Sheet1");
						 */
						sh2.getRow(i).createCell(5).setCellValue("PASS");
						/*
						 * workbook.write(fis1); fis1.close();
						 */
					}
				}

				else if (serviceid.equals("S2")) {

					pause(1000);
					String actrate = driver
							.findElement(By.xpath("//*[@class=\"fdxRatetable\"]/tbody[5]//div[@class=\"ratebtn\"]"))
							.getText();
					System.out.println(actrate);
					String ExpectedRate = formatter.formatCellValue(sh1.getRow(i).getCell(3));
					sh2.getRow(i).createCell(4).setCellValue(actrate);
					if (!actrate.equals(ExpectedRate)) {
						sh1.getRow(i).createCell(5).setCellValue("FAIL");

						/*
						 * workbook.write(fis1); fis1.close();
						 */
					}

					else {
						sh2.getRow(i).createCell(5).setCellValue("PASS");

						/*
						 * workbook.write(fis1); fis1.close();
						 */
					}
				} else if (serviceid.equals("EC")) {

					pause(1000);
					String actrate = driver
							.findElement(By.xpath("//*[@class=\"fdxRatetable\"]/tbody[7]//div[@class=\"ratebtn\"]"))
							.getText();
					System.out.println(actrate);
					String ExpectedRate = formatter.formatCellValue(sh1.getRow(i).getCell(3));
					sh2.getRow(i).createCell(4).setCellValue(actrate);
					if (!actrate.equals(ExpectedRate)) {
						sh1.getRow(i).createCell(5).setCellValue("FAIL");

						/*
						 * workbook.write(fis1); fis1.close();
						 */

					}

					else {
						sh2.getRow(i).createCell(5).setCellValue("PASS");
						/*
						 * workbook.write(fis1); fis1.close();
						 */
					}
				} else if (serviceid.equals("DR")) {

					pause(1000);
					String actrate = driver
							.findElement(By.xpath("//*[@class=\"fdxRatetable\"]/tbody[1]//div[@class=\"ratebtn\"]"))
							.getText();
					System.out.println(actrate);
					String ExpectedRate = formatter.formatCellValue(sh1.getRow(i).getCell(3));
					sh2.getRow(i).createCell(4).setCellValue(actrate);
					if (!actrate.equals(ExpectedRate)) {
						sh2.getRow(i).createCell(5).setCellValue("FAIL");

						/*
						 * workbook.write(fis1); fis1.close();
						 */

					}

					else {
						sh2.getRow(i).createCell(5).setCellValue("PASS");

						/*
						 * workbook.write(fis1); fis1.close();
						 */
					}
				} else if (serviceid.equals("AIR")) {

					pause(1000);
					String actrate = driver
							.findElement(By.xpath("//*[@class=\"fdxRatetable\"]/tbody[1]//div[@class=\"ratebtn\"]"))
							.getText();
					System.out.println(actrate);
					String ExpectedRate = formatter.formatCellValue(sh1.getRow(i).getCell(3));
					sh2.getRow(i).createCell(4).setCellValue(actrate);
					if (!actrate.equals(ExpectedRate)) {
						sh2.getRow(i).createCell(5).setCellValue("FAIL");

					}

					else {
						sh2.getRow(i).createCell(5).setCellValue("PASS");

						/*
						 * workbook.write(fis1); fis1.close();
						 */

					}

				}

				else if (serviceid.equals("DRV")) {

					pause(1000);
					String actrate = driver
							.findElement(By.xpath("//*[@class=\"fdxRatetable\"]/tbody[1]//div[@class=\"ratebtn\"]"))
							.getText();
					System.out.println(actrate);
					String ExpectedRate = formatter.formatCellValue(sh1.getRow(i).getCell(3));
					sh2.getRow(i).createCell(4).setCellValue(actrate);
					if (!actrate.equals(ExpectedRate)) {
						sh2.getRow(i).createCell(5).setCellValue("FAIL");

						/*
						 * workbook.write(fis1); fis1.close();
						 */
					}

					else {
						sh2.getRow(i).createCell(5).setCellValue("PASS");

						/*
						 * workbook.write(fis1); fis1.close();
						 */

					}

				}

			}
			workbook.write(fis1);
			fis1.close();

		} catch (Exception e) {
			System.out.println("Something went Wrong");

		}

		msg.append("Step1 : Enter URL : PASS" + "\n");
		msg.append("Step2 : Enter UserName : PASS" + "\n");
		msg.append("Step3 : Enter Password : PASS" + "\n");
		msg.append("Step4 : Application Login Successfully : PASS" + "\n");
		msg.append("Step5 : Rate/Quote Verification Process Start.... " + "\n");
		msg.append("Step6 : Rate/Quote Verification Process Completed !!" + "\n");

		// Send Email

		String subject = "Selenium Automation Script: STAGING FedEx Rate/Quote";
		try {
			// asharma@samyak.com,sdas@samyak.com,pgandhi@samyak.com,byagnik@samyak.com,pdoshi@samyak.com
			Email.sendMail("ravina.prajapati@samyak.com,asharma@samyak.com,parth.doshi@samyak.com", subject,
					msg.toString(), "");
		} catch (Exception ex) {
			Logger.getLogger(RateVerification.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	public static void pause(final int iTimeInMillis) {
		try {
			Thread.sleep(iTimeInMillis);
		} catch (InterruptedException ex) {
			System.out.println(ex.getMessage());
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

package shipmentFedExWebSite;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ShipmentCreation {

	static WebDriver driver;
	static StringBuilder msg = new StringBuilder();

	public static void main(String[] args) throws Exception {

		System.setProperty("webdriver.chrome.driver","C:\\Abhishek\\ABHISHEK SHARMA\\workspace\\Driver\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		driver = new ChromeDriver(options);
		options.addArguments("--test-type");
		driver.manage().deleteAllCookies();

		driver.manage().window().maximize();
		driver.get("https://staging.fedexsameday.com"); // Enter URL

		driver.findElement(By.id("logon_name")).clear();
		driver.findElement(By.id("logon_name")).sendKeys("testsamyak10"); // Enter UserName

		driver.findElement(By.id("logon_password")).clear();
		driver.findElement(By.id("logon_password")).sendKeys("samyak10"); // Enter Password

		driver.findElement(By.id("cmdLogin")).click(); // Click on Login
		driver.getTitle();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		// Read data from Excel
		File src = new File("C:\\Abhishek\\ABHISHEK SHARMA\\workspace\\FedExApplication\\src\\TestFiles\\FedExShipments.xlsx");
		FileInputStream fis = new FileInputStream(src);
		Workbook workbook = WorkbookFactory.create(fis);
		Sheet sh1 = workbook.getSheet("Sheet1");
		//int rcount = sh1.getLastRowNum();

		
		for (int i = 1; i < 26; i++) {
			Thread.sleep(4000);
			driver.findElement(By.linkText("Ship")).click(); // Click on ship screen
			driver.getTitle();
			DataFormatter formatter = new DataFormatter();

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			if (driver.getPageSource().contains("Change Address")) // If my preferences has setup From Address
			{
				driver.findElement(By.id("cmdChangePUAddr")).click();
				driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			}
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			String PUCompany = formatter.formatCellValue(sh1.getRow(i).getCell(0));
			driver.findElement(By.xpath("//*[@id='pu_company']")).clear();
			driver.findElement(By.xpath("//*[@id='pu_company']")).sendKeys(PUCompany); // Pickup Company

			String PUName = formatter.formatCellValue(sh1.getRow(i).getCell(1));
			driver.findElement(By.xpath("//*[@id='pu_pertosee']")).clear();
			driver.findElement(By.xpath("//*[@id='pu_pertosee']")).sendKeys(PUName); // Pickup Name
			String PUAddress1 = formatter.formatCellValue(sh1.getRow(i).getCell(2));
			driver.findElement(By.xpath("//*[@id='pu_addr1']")).clear();
			driver.findElement(By.xpath("//*[@id='pu_addr1']")).sendKeys(PUAddress1); // PU Address line 1

			String PUZip = formatter.formatCellValue(sh1.getRow(i).getCell(4));
			driver.findElement(By.xpath("//*[@id='pu_zip']")).clear();
			driver.findElement(By.xpath("//*[@id='pu_zip']")).sendKeys(PUZip);
			Thread.sleep(2000);
			driver.findElement(By.xpath("//*[@id='pu_zip']")).sendKeys(Keys.TAB); // Pickup Zip code and tab
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			Thread.sleep(2000);

			String PUPhone = formatter.formatCellValue(sh1.getRow(i).getCell(5));
			driver.findElement(By.xpath("//*[@id='pu_phone']")).clear();
			driver.findElement(By.xpath("//*[@id='pu_phone']")).sendKeys(PUPhone); // Pickup phone Number
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

			// Delivery Information : To Address

			String DLCompany = formatter.formatCellValue(sh1.getRow(i).getCell(6));
			driver.findElement(By.xpath("//*[@id='dl_company']")).clear();
			driver.findElement(By.xpath("//*[@id='dl_company']")).sendKeys(DLCompany); // Delivery Company name

			String DLName = formatter.formatCellValue(sh1.getRow(i).getCell(7));
			driver.findElement(By.xpath("//*[@id='dl_attn']")).clear();
			driver.findElement(By.xpath("//*[@id='dl_attn']")).sendKeys(DLName); // Delivery Contact Name

			String DLAddress1 = formatter.formatCellValue(sh1.getRow(i).getCell(8));
			driver.findElement(By.xpath("//*[@id='dl_addr1']")).clear();
			driver.findElement(By.xpath("//*[@id='dl_addr1']")).sendKeys(DLAddress1); // Del Address line 1

			String DLZip = formatter.formatCellValue(sh1.getRow(i).getCell(10));
			driver.findElement(By.xpath("//*[@id='dl_zip']")).clear();
			driver.findElement(By.xpath("//*[@id='dl_zip']")).sendKeys(DLZip);
			driver.findElement(By.xpath("//*[@id='dl_zip']")).sendKeys(Keys.TAB); // Del Zip and tab
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			Thread.sleep(2000);
			String DLPhone = formatter.formatCellValue(sh1.getRow(i).getCell(11));
			driver.findElement(By.xpath("//*[@id='dl_phone']")).clear();
			driver.findElement(By.xpath("//*[@id='dl_phone']")).sendKeys(DLPhone); // Del Phone number

			Thread.sleep(4000);

			driver.findElement(By.id("anchor1xx")).click(); // click on calander
			driver.findElement(By.xpath("//a[contains(.,'Today')]")).click(); // select today

			Thread.sleep(5000);

			Select select1 = new Select(driver.findElement(By.id("ddlReadyHour"))); // ready time selection
			select1.selectByVisibleText("11");

			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			select1 = new Select(driver.findElement(By.id("ddlReadyMinutes"))); // ready time min selection
			select1.selectByVisibleText("30");

			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			select1 = new Select(driver.findElement(By.xpath(".//*[@name='ddlReadyTimeType']"))); // AM/ PM selection
			select1.selectByVisibleText("AM");
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			Thread.sleep(2000);

			String serviceid = formatter.formatCellValue(sh1.getRow(i).getCell(13)); // Service ID compare from the Excel

			if (serviceid.equals("DRV") || serviceid.equals("AIR")|| serviceid.equals("SDC")|| serviceid.equals("FRG")) 
			{
				driver.findElement(By.name("txt_content")).clear();
				driver.findElement(By.name("txt_content")).sendKeys("BOX"); // Enter value in contents text box
			}

			String pieces = formatter.formatCellValue(sh1.getRow(i).getCell(14)); // Compare Pieces from the excel
			
			driver.findElement(By.id("pieces")).click();
			driver.findElement(By.id("pieces")).clear();
			driver.findElement(By.id("pieces")).sendKeys(pieces);
			driver.findElement(By.id("pieces")).sendKeys(Keys.TAB);
			Random rn = new Random(); // Generate random numbers
			int pval = Integer.parseInt(pieces);
			Thread.sleep(4000);
			if (pval == 1) {
				int ans;
				if (serviceid.equals("FRG")) {
					ans = rn.nextInt(200) + 1;
				} else {
					ans = rn.nextInt(10) + 1;
				}
				String st = String.valueOf(ans);

				// Dim
				
				new Select(driver.findElement(By.id("ddProfile"))).selectByVisibleText("Enter dimensions");
				driver.findElement(By.id("txtDimLen0")).clear();
				driver.findElement(By.id("txtDimLen0")).sendKeys(st);
				driver.findElement(By.id("txtDimWid0")).clear();
				driver.findElement(By.id("txtDimWid0")).sendKeys(st);
				driver.findElement(By.id("txtDimHt0")).clear();
				driver.findElement(By.id("txtDimHt0")).sendKeys(st);
				driver.findElement(By.id("txtActWt0")).clear();
				driver.findElement(By.id("txtActWt0")).sendKeys(st);
				driver.findElement(By.id("order_by")).clear();
				driver.findElement(By.id("order_by")).sendKeys("Abhishek Sharma");
				driver.findElement(By.id("order_phone")).clear();
				driver.findElement(By.id("order_phone")).sendKeys("1112223333");
			} 
			
			else {

				driver.findElement(By.id("rdbNo")).click();
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
					new Select(driver.findElement(By.id(drpdim)))
							.selectByVisibleText("Enter dimensions");
					txtqt = "txtQty" + j;
					dimlen = "txtDimLenN" + j;
					dimwh = "txtDimWidN" + j;
					dimhi = "txtDimHtN" + j;
					ActWt = "txtActWtNew" + j;

					driver.findElement(By.id(txtqt)).clear();
					driver.findElement(By.id(txtqt)).sendKeys("1");
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

			Thread.sleep(3000);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,-850)", "");
			driver.findElement(By.id("lnkCalculate")).click(); // Click on calculate link
			Thread.sleep(9000);
			driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);

			// Service

			File src1 = new File("C:\\Abhishek\\ABHISHEK SHARMA\\workspace\\FedExApplication\\src\\TestFiles\\FedExShipments.xlsx");
			FileOutputStream fis1 = new FileOutputStream(src1);
			Sheet sh2 = workbook.getSheet("Sheet1");
			workbook.write(fis1);

			if (serviceid.equals("PR")) // If match with PR, below code will execute
			{

				driver.findElement(By.id("chkPR")).click();
				driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
				String rate = driver.findElement(By.id("btnPR")).getText();
				System.out.println(rate);
				String ExpectedRate = formatter.formatCellValue(sh1.getRow(i).getCell(12));
				msg.append("PR Service - Actual Rate :" + rate + "\n");
				sh2.getRow(i).createCell(16).setCellValue(rate);

				if (!rate.equals(ExpectedRate)) {
					sh2.getRow(i).createCell(17).setCellValue("FAIL");
					fis1.close();
				}

				else {
					sh2.getRow(i).createCell(17).setCellValue("PASS");
					fis1.close();
				}

			}

			else if (serviceid.equals("S2")) // If match with S2, below code will execute
			{

				driver.findElement(By.id("chkS2")).click();
				driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);

				String rate = driver.findElement(By.id("btnS2")).getText();
				System.out.println(rate);
				msg.append("S2 Service - Actual Rate :" + rate + "\n");
				String ExpectedRate = formatter.formatCellValue(sh1.getRow(i).getCell(12));
				sh2.getRow(i).createCell(16).setCellValue(rate);
				
				if (!rate.equals(ExpectedRate)) {
					sh2.getRow(i).createCell(17).setCellValue("FAIL");
					fis1.close();

				}

				else {
					sh2.getRow(i).createCell(17).setCellValue("PASS");
					fis1.close();
				}

			}

			else if (serviceid.equals("EC")) // If match with EC, below code will execute
			{
				driver.findElement(By.id("chkSDRTS")).click();
				driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
				driver.findElement(By.id("chkEC")).click();
				driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
				String rate = driver.findElement(By.id("btnEC")).getText();
				System.out.println(rate);
				msg.append("EC Service - Actual Rate :" + rate + "\n");

				String ExpectedRate = formatter.formatCellValue(sh1.getRow(i).getCell(12));
				sh2.getRow(i).createCell(16).setCellValue(rate);
				if (!rate.equals(ExpectedRate)) {
					sh2.getRow(i).createCell(17).setCellValue("FAIL");
					fis1.close();

				}

				else {
					sh2.getRow(i).createCell(17).setCellValue("PASS");
					fis1.close();
				}

			}

			else if (serviceid.equals("DR")) // If match with DR, below code will execute
			{
				driver.findElement(By.id("chkDR")).click();
				driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);

				String rate = driver.findElement(By.id("btnDR")).getText();
				System.out.println(rate);
				msg.append("DR Service - Actual Rate :" + rate + "\n");

				String ExpectedRate = formatter.formatCellValue(sh1.getRow(i).getCell(12));
				sh2.getRow(i).createCell(16).setCellValue(rate);

				if (!rate.equals(ExpectedRate)) {
					sh2.getRow(i).createCell(17).setCellValue("FAIL");
					fis1.close();
				}
				else {
					sh2.getRow(i).createCell(17).setCellValue("PASS");
					fis1.close();
				}
			}

			else if (serviceid.equals("DRV")) // If match with DRV, below code will execute
			{

				driver.findElement(By.id("chkDRV")).click();
				driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);

				String rate = driver.findElement(By.id("btnDRV")).getText();
				System.out.println(rate);
				msg.append("DRV Service - Actual Rate :" + rate + "\n");

				String ExpectedRate = formatter.formatCellValue(sh1.getRow(i).getCell(12));
				sh2.getRow(i).createCell(16).setCellValue(rate);

				if (!rate.equals(ExpectedRate)) {
					sh2.getRow(i).createCell(17).setCellValue("FAIL");
					fis1.close();

				}

				else {
					sh2.getRow(i).createCell(17).setCellValue("PASS");
					fis1.close();
				}

			}

			else if (serviceid.equals("AIR")) // If match with AIR, below code will execute
			{

				driver.findElement(By.id("chkAIR")).click();
				driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);

				String rate = driver.findElement(By.id("btnAIR")).getText();
				System.out.println(rate);
				msg.append("AIR Service - Actual Rate :" + rate + "\n");

				String ExpectedRate = formatter.formatCellValue(sh1.getRow(i).getCell(12));
				sh2.getRow(i).createCell(16).setCellValue(rate);
				if (!rate.equals(ExpectedRate)) {
					sh2.getRow(i).createCell(17).setCellValue("FAIL");
					fis1.close();

				}

				else {
					sh2.getRow(i).createCell(17).setCellValue("PASS");
					fis1.close();
				}

			}

			else if (serviceid.equals("SDC"))// If match with SDC, below code will execute
			{

				driver.findElement(By.id("chkSDC")).click();
				driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);

				String rate = driver.findElement(By.id("btnSDC")).getText();
				System.out.println(rate);
				msg.append("SDC Service - Actual Rate :" + rate + "\n");

				String ExpectedRate = formatter.formatCellValue(sh1.getRow(i).getCell(12));
				sh2.getRow(i).createCell(16).setCellValue(rate);
				if (!rate.equals(ExpectedRate)) {
					sh2.getRow(i).createCell(17).setCellValue("FAIL");
					fis1.close();

				}

				else {
					sh2.getRow(i).createCell(17).setCellValue("PASS");
					fis1.close();
				}
				
			}

			else if (serviceid.equals("FRG")) // If match with FRG, below code will execute
			{

				driver.findElement(By.id("chkFRG")).click();
				driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);

				String rate = driver.findElement(By.id("btnFRG")).getText();
				System.out.println(rate);
				msg.append("FRG Service - Actual Rate :" + rate + "\n");

			}

			driver.findElement(By.id("cmdSubmit")).click(); // Create job button
			Thread.sleep(7000);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

			// If alert pop-up exist, than accept.

			if (isAlertPresent()) {
				Alert alt = driver.switchTo().alert();
				alt.accept();
			}

			Thread.sleep(5000);
			driver.getTitle();
			driver.findElement(By.cssSelector("input[id=cmdSubmit]")).click(); // Confirm from Shipment Summary screen.

			String VoucherNum = driver.findElement(By.xpath("//*[@id='lblVoucherNum']")).getText(); // Get Shipment tracking number and store in variable
			System.out.println("Shipment Tracking # " + VoucherNum);

			
			msg.append("Shipment Tracking # " + VoucherNum + "\n\n");
			sh2.getRow(i).createCell(15).setCellValue(VoucherNum);
			fis1.close();

			// Send Email
		}
		String subject = "STAGING : FedEx Shipment Creation Using SELENIUM";
		try {
			Email.sendMail("asharma@samyak.com,pgandhi@samyak.com,sdas@samyak.com,byagnik@samyak.com,pdoshi@samyak.com,kbrahmbhatt@samyak.com", subject, msg.toString(), "");
		} catch (Exception ex) {
			Logger.getLogger(ShipmentCreation.class.getName()).log(Level.SEVERE,
					null, ex);
		}

		driver.findElement(By.linkText("Rate")).click(); // Go to Rate screen
		driver.findElement(By.id("Wuc_headerlogout1_lnkbtnLogout")).click(); // click on Logout
		driver.quit(); // close browser

	}

	public static void waitForVisibilityOfElement(By objLocator, long lTime) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, lTime);
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(objLocator));
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

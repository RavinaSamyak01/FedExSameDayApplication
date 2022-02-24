package com.fxoAutoDispatch;

	import java.awt.Robot;
	import java.awt.event.KeyEvent;
	import java.io.File;
	import java.io.FileInputStream;
	import org.apache.poi.ss.usermodel.DataFormatter;
	import org.apache.poi.ss.usermodel.Sheet;
	import org.apache.poi.ss.usermodel.Workbook;
	import org.apache.poi.ss.usermodel.WorkbookFactory;
	import org.openqa.selenium.By;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.chrome.ChromeDriver;
	import org.openqa.selenium.chrome.ChromeOptions;
	import org.openqa.selenium.support.ui.ExpectedConditions;
	import org.openqa.selenium.support.ui.Select;
	import org.openqa.selenium.support.ui.WebDriverWait;

	public class CheetahRejection {


		static WebDriver driver;
		public static void main(String[] args) throws Exception
		{

			System.setProperty("webdriver.chrome.driver","C:\\Abhishek\\ABHISHEK SHARMA\\workspace\\Driver\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			driver = new ChromeDriver(options);
			driver.manage().window().maximize();
			
			Robot r = new Robot();
			r.keyPress(KeyEvent.VK_F11);
			
			
			driver.get("http://10.20.104.82:9077/publicServiceWebapplication/FlashWsV2.aspx");
			
			Thread.sleep(4000);

			System.out.println("Cheetah Process Start----" + "\n");
			// Read data from Excel
					File src = new File("C:\\Abhishek\\ABHISHEK SHARMA\\workspace\\FedExApplication\\src\\TestFiles\\FXOAutoDispatch.xlsx");
					FileInputStream fis = new FileInputStream(src);
					Workbook workbook = WorkbookFactory.create(fis);
					Sheet sh1 = workbook.getSheet("Sheet1");
					DataFormatter formatter = new DataFormatter();
					
						for(int i=1;i<5;i++)
						{
							System.out.println("CASE: [Rejected] Order Processing Initiated !!");
							String ShipmentTracking = formatter.formatCellValue(sh1.getRow(i).getCell(15));
							driver.findElement(By.id("MainContent_txtShipTrackNum")).clear();
							driver.findElement(By.id("MainContent_txtShipTrackNum")).sendKeys(ShipmentTracking);
							
							Thread.sleep(1000);
							rejectEd();
							System.out.println("CASE2: Rejected DONE !!");
						}
						driver.close();
			}
			
					

		public static void rejectEd() throws Exception //REJECTED 
		{
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
			
		}
		
		public static void waitForVisibilityOfElement(By objLocator, long lTime)
		{
			try{
				WebDriverWait wait = new WebDriverWait(driver, lTime);
				wait.until(ExpectedConditions.visibilityOfElementLocated(objLocator));
			}
			catch(Exception e){
				
			}
		}
		
		public static void waitForInVisibilityOfElement(By objLocator, long ITime)
		{
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(objLocator));
		}

}

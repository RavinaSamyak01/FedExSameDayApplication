package otherScreens;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ActiveOrders {

	static WebDriver driver,driver1;
	public static void main(String[] args) throws Exception
	{
		System.setProperty("webdriver.chrome.driver","C:\\Abhishek\\ABHISHEK SHARMA\\workspace\\Driver\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		driver = new ChromeDriver(options);
		options.addArguments("--test-type");
		driver.manage().deleteAllCookies();

		driver.manage().window().maximize();
		driver.get("https://staging.fedexsameday.com/"); // Enter URL

		driver.findElement(By.id("logon_name")).clear();
		driver.findElement(By.id("logon_name")).sendKeys("testsamyak10"); // Enter UserName

		driver.findElement(By.id("logon_password")).clear();
		driver.findElement(By.id("logon_password")).sendKeys("samyak10"); // Enter Password
		Thread.sleep(2000);
		driver.findElement(By.id("cmdLogin")).click();
	    driver.getTitle();
		

		 waitForVisibilityOfElement(By.linkText("Track"), 5);
		
		driver.findElement(By.linkText("Track")).click();
		driver.findElement(By.linkText("Active Shipment")).click();
		waitForVisibilityOfElement(By.linkText("Active Shipment"), 5);
		//Thread.sleep(6000);
		// Read data from Excel
				File src = new File("C:\\Abhishek\\ABHISHEK SHARMA\\workspace\\FedExApplication\\src\\TestFiles\\FedExShipments.xlsx");
				FileInputStream fis = new FileInputStream(src);
				Workbook workbook = WorkbookFactory.create(fis);
				Sheet sh1 = workbook.getSheet("Sheet1");
				DataFormatter formatter = new DataFormatter();
				String tracking = formatter.formatCellValue(sh1.getRow(13).getCell(15));
				
			    
				for(int i=13; i<18; i++)
				{
					if(i==13)
					{
					
						// Click on Received [Shipment Tracking]
				        List<WebElement> allColumnsInRow =driver.findElements(By.xpath(".//*[@id='lblContent']/table/tbody/tr/td[text()='"+tracking+"']/../td[1]"));
					        for(WebElement e: allColumnsInRow)
					        {
					        	e.click();
					        	//Thread.sleep(2000);
					        	waitForVisibilityOfElement(By.linkText("Track"), 5);
					        	driver.findElement(By.linkText("Track")).click();		
							  	//Thread.sleep(2000);
							  	waitForVisibilityOfElement(By.linkText("Active Shipment"), 5);
							  	driver.findElement(By.linkText("Active Shipment")).click();
					        }
				        
						}
					Thread.sleep(2000);
					if(i==14)
					{
						// Click on 8.5 x 11 Label
						
						Thread.sleep(2000);
				        List<WebElement> allColumnsInRow =driver.findElements(By.xpath(".//*[@id='lblContent']/table/tbody/tr/td[text()='"+tracking+"']/../td[9]"));
				        for(WebElement e: allColumnsInRow)
				        {
				        	String winHandleBefore = driver.getWindowHandle();	
				        	e.click();
				        	Thread.sleep(5000);
				        	driver.switchTo().window(winHandleBefore);
				        }
					}
					
					if(i==15)
					{
				        // Click on 4 x 6 Label
				        List<WebElement> allColumnsInRow =driver.findElements(By.xpath(".//*[@id='lblContent']/table/tbody/tr/td[text()='"+tracking+"']/../td[9]"));
					        for(WebElement e: allColumnsInRow)
					        {
					        	String winHandleBefore = driver.getWindowHandle();	
					        	WebElement label = e.findElement(By.linkText("4 x 6 Label"));
					        	label.click();
					        	Thread.sleep(8000);
					        	driver.switchTo().window(winHandleBefore);
					        }
					    
						}
		
					if(i==16)
					{
				        // Post Confirm Edit
				        	List<WebElement> allColumnsInRow =driver.findElements(By.xpath(".//*[@id='lblContent']/table/tbody/tr/td[text()='"+tracking+"']/../td[2]"));
					        for(WebElement e: allColumnsInRow)
					        {
					        	e.click();
					        	
					        	driver.findElement(By.id("lnkCalculate")).click(); // Click on calculate link
								Thread.sleep(9000);
								driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
								
								driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
								String rate = driver.findElement(By.id("btnPR")).getText();
								System.out.println(rate);
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
								String VoucherNum1 = driver.findElement(By.xpath("//*[@id='lblVoucherNum']")).getText(); // Get Shipment tracking number and store in variable
								System.out.println("Shipment Tracking # " + VoucherNum1);
					        }
						}
					
					if(i==17)
					{
					
						// Shipment Tracking [Multiple]
						driver.findElement(By.linkText("Track")).click();
						driver.findElement(By.xpath(".//*[@id='fdxHeader_menuControl']/li[3]/ul/li[1]/a")).click();
						String tracking1 = formatter.formatCellValue(sh1.getRow(13).getCell(15));
						String tracking2 = formatter.formatCellValue(sh1.getRow(14).getCell(15));
						driver.findElement(By.xpath(".//*[@id='track_num']")).sendKeys(tracking1+","+tracking2);
						driver.findElement(By.id("cmdTrack")).click();
						Thread.sleep(2000);
						File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				        FileUtils.copyFile(scrFile, new File("C:\\Abhishek\\ABHISHEK SHARMA\\workspace\\FedExApplication\\src\\TestFiles\\MultipleTracking.png"));
						driver.findElement(By.id("dgItin_shipNum_0")).click();				        
				        
						}
					Thread.sleep(2000);
					}
				Thread.sleep(8000);		        
	    driver.close();			    
		driver.quit();
	}
	public static boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException ex) {
			return false;
		}
	}
	public static void waitForVisibilityOfElement(By objLocator, long lTime)
	{
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

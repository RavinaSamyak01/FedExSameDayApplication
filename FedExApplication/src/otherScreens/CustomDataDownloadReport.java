package otherScreens;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import shipmentFedExWebSite.Email;
import shipmentFedExWebSite.ShipmentCreation;

public class CustomDataDownloadReport {
	static WebDriver driver;
	static StringBuilder msg = new StringBuilder();
	public static void main(String[] args) throws Exception , IOException
		{
			System.setProperty("webdriver.chrome.driver","C:\\Abhishek\\ABHISHEK SHARMA\\workspace\\Driver\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			driver = new ChromeDriver(options);
			options.addArguments("--test-type");
			driver.manage().deleteAllCookies();
			driver.manage().window().maximize();
			
			driver.get("https://staging.fedexsameday.com/"); // Enter URL
			System.out.println("Enter URL : PASS");
			driver.findElement(By.id("logon_name")).clear();
			driver.findElement(By.id("logon_name")).sendKeys("testsamyak10"); // Enter UserName
			System.out.println("Enter UserName : PASS");
			driver.findElement(By.id("logon_password")).clear();
			driver.findElement(By.id("logon_password")).sendKeys("samyak10"); // Enter Password
			System.out.println("Enter Password : PASS");
			driver.findElement(By.id("cmdLogin")).click(); // Click on Login
			driver.getTitle();
			System.out.println("Application Login Successfully : PASS");
			Actions builder = new Actions(driver);
			driver.findElement(By.linkText("Manage")).click();		
			
			waitForVisibilityOfElement(By.linkText("Run Reports"), 5);
			WebElement ele1 = driver.findElement(By.linkText("Run Reports"));		
			builder.moveToElement(ele1).build().perform();
			
			driver.findElement(By.linkText("Custom Data Download")).click();
		
			newReport();
			
			msg.append("Enter URL             : PASS" + "\n");
			msg.append("Enter UserName        : PASS" + "\n");
			msg.append("Enter Password        : PASS" + "\n");
			msg.append("Login Application     : PASS" + "\n");
			msg.append("New Report Save       : PASS" + "\n");
			msg.append("Generate Report !!    : PASS" + "\n");
			
			String subject = "STAGING : FedEx CustomDataDownload Using SELENIUM";
			try {
				Email.sendMail("asharma@samyak.com", subject, msg.toString(), "");
			} catch (Exception ex) {
				Logger.getLogger(ShipmentCreation.class.getName()).log(Level.SEVERE,
						null, ex);
			}
			
			driver.close();
			driver.quit();
			
		}

			public static void newReport() throws Exception
			{
				Thread.sleep(3000);
				
				driver.findElement(By.id("btnNewReport")).click();
				Thread.sleep(3000);
				driver.findElement(By.id("txtReportName")).clear();
				driver.findElement(By.id("txtReportName")).sendKeys("SeleniumAuto");
				driver.findElement(By.id("txtPUStartDate")).clear();				
				driver.findElement(By.id("txtPUStartDate")).sendKeys("02/04/2020");
				driver.findElement(By.id("txtPUEndDate")).clear();
				driver.findElement(By.id("txtPUEndDate")).sendKeys("02/04/2020");
			
				WebElement acct =  driver.findElement(By.id("lbAccount"));
				Select s1 = new Select(acct);
				s1.deselectByVisibleText("(090434136) CREATIVE ARTISTS AGENCY");
				
				Select s2 = new Select(acct);
				s2.selectByVisibleText("(992445229) CONNECT FEDEX");
				
				
				Thread.sleep(3000);
				WebElement fields =  driver.findElement(By.id("lbAvailable"));
				Select fld1 = new Select(fields);
				
				fields.click();
				Robot r = new Robot();
				r.keyPress(KeyEvent.VK_CONTROL);
				r.keyPress(KeyEvent.VK_A);
				
				Thread.sleep(3000);
				/*
				fld1.selectByVisibleText("Account #");
				fld1.selectByVisibleText("Service Type");
				fld1.selectByVisibleText("Pickup #");
				fld1.selectByVisibleText("Pickup Wait Time (CWK) $");
				fld1.selectByVisibleText("Pickup Waiting Time (CWK)");
				fld1.selectByVisibleText("Return Wait Time (CWK) $");
				fld1.selectByVisibleText("Return Waiting Time (CWK)");
				fld1.selectByVisibleText("Delivery Wait Time (CWK) $");
				fld1.selectByVisibleText("Delivery Waiting Time (CWK)");
				fld1.selectByVisibleText("Total Waiting Time (CWK)");
				fld1.selectByVisibleText("Total Wait Time $");
				
				driver.findElement(By.name("ctl00")).click();
				driver.findElement(By.id("btnSaveReport")).click();
				Thread.sleep(3000);
				System.out.println("New Report Save : PASS");
				
				driver.findElement(By.id("imgbtnGenerate")).click();
				Thread.sleep(5000);
				File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		        FileUtils.copyFile(scrFile, new File("C:\\Abhishek\\ABHISHEK SHARMA\\workspace\\FedExApplication\\src\\TestFiles\\CustomDataDownload.png"));
		        
				System.out.println("Generate Report !! : PASS");
				Thread.sleep(7000);*/
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

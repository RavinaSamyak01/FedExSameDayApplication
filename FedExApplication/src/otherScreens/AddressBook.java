package otherScreens;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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

public class AddressBook {

	static WebDriver driver;
	static StringBuilder msg = new StringBuilder();
	public static void main(String[] args) throws Exception {

		System.setProperty("webdriver.chrome.driver","C:\\Abhishek\\ABHISHEK SHARMA\\workspace\\Driver\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		driver = new ChromeDriver(options);
        options.addArguments("--test-type");
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.get("https://staging.fedexsameday.com");
		System.out.println("Enter URL : PASS");
		driver.findElement(By.id("logon_name")).clear();
		driver.findElement(By.id("logon_name")).sendKeys("testsamyak10");
		System.out.println("Enter UserName : PASS");
		driver.findElement(By.id("logon_password")).clear();
		driver.findElement(By.id("logon_password")).sendKeys("samyak10");
		System.out.println("Enter Password : PASS");
		driver.findElement(By.id("cmdLogin")).click();
		System.out.println("Login Application : PASS");
		driver.getTitle();
		Thread.sleep(3000);
		
		addrCreateAddress();
		addressImport();
		
		msg.append("Enter URL         : PASS" + "\n");
		msg.append("Enter UserName    : PASS" + "\n");
		msg.append("Enter Password    : PASS" + "\n");
		msg.append("Login Application : PASS" + "\n");
		msg.append("Manually Create Address and Save               : PASS" + "\n");
		msg.append("Address Import with Comma (,) Delimeter        : PASS" + "\n");
		msg.append("Address Import with Semicolon (;) Delimeter    : PASS" + "\n");
		msg.append("Address Import with Verticle Bar (|) Delimeter : PASS" + "\n");
		msg.append("Address Import with Tab (	) Delimeter        : PASS" + "\n");
		
		String subject = "STAGING : FedEx Address Book/Import Using SELENIUM";
		try {
			Email.sendMail("asharma@samyak.com,pdoshi@samyak.com", subject, msg.toString(), "");
		} catch (Exception ex) {
			Logger.getLogger(ShipmentCreation.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		
		driver.close();
		driver.quit();	
        
		
		
	}
	
	
	
	
	public static void addrCreateAddress() throws Exception
	{
		Thread.sleep(5000);
		Actions builder = new Actions(driver);
		driver.findElement(By.linkText("Manage")).click();		
		
		WebElement ele1 = driver.findElement(By.linkText("Manage My Account"));		
		builder.moveToElement(ele1).build().perform();
		
		WebElement ele2 = driver.findElement(By.linkText("Manage My Account"));		
		builder.moveToElement(ele2).build().perform();
		
		driver.findElement(By.linkText("Address Book")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//*[@id=\"dvPrintGrid\"]/table/tbody/tr[4]/td[2]/a[37]/img")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("quick_key")).sendKeys("QC_AUTOMATION");
		driver.findElement(By.id("company_name")).sendKeys("AUTOMATION : Nothing Bundt Cakes");
		driver.findElement(By.id("attn")).sendKeys("QC_Abhishek Sharma");
		driver.findElement(By.id("addr_line_1")).sendKeys("304 W Bay Area Blvd.");
		driver.findElement(By.id("zip_code")).sendKeys("77598");
		driver.findElement(By.id("zip_code")).sendKeys(Keys.TAB);
		Thread.sleep(3000);
		driver.findElement(By.id("phone_num")).sendKeys("(310) 123-4482");
		
		Select s1 = new Select (driver.findElement(By.id("PUBOTHourDropDownList")));
		s1.selectByVisibleText("8");
		Select s2 = new Select (driver.findElement(By.id("PUBOTMinuteDropDownList")));
		s2.selectByVisibleText("00");
		Select s3 = new Select (driver.findElement(By.id("PUBOTFormatDropDownList")));
		s3.selectByVisibleText("AM");
		
		Select s4 = new Select (driver.findElement(By.id("PUBCTHourDropDownList")));
		s4.selectByVisibleText("5");
		Select s5 = new Select (driver.findElement(By.id("PUBCTMinuteDropDownList")));
		s5.selectByVisibleText("00");
		Select s6 = new Select (driver.findElement(By.id("PUBCTFormatDropDownList")));
		s6.selectByVisibleText("PM");
		
		driver.findElement(By.id("email_addr")).sendKeys("asharma@samyak.com");
		driver.findElement(By.id("ncsButtons_cmdSubmit")).click();
		System.out.println("Manually Create Address and Save : PASS");
	}

	public  static void addressImport() throws InterruptedException, IOException
	{
	
		for (int i=0; i<4 ; i++)
		{
			if(i==0)
			{
				Thread.sleep(5000);
				driver.findElement(By.linkText("Import")).click();
				WebElement ele = driver.findElement(By.id("ddlDelimeter"));
				Select opt = new Select(ele);
				opt.selectByVisibleText("Comma");	
				driver.findElement(By.id("fileUpload")).sendKeys("C:\\Abhishek\\ABHISHEK SHARMA\\workspace\\FedExApplication\\src\\TestFiles\\Import_Comma.txt");
				WebElement ele4 = driver.findElement(By.id("ddlClearAddr"));
				Select opt1 = new Select(ele4);
				opt1.selectByVisibleText("No, don't clear old addresses");
				
				File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		        FileUtils.copyFile(scrFile, new File("C:\\Abhishek\\ABHISHEK SHARMA\\workspace\\FedExApplication\\src\\TestFiles\\AddressImport01.png"));
	
		        driver.findElement(By.id("cmdUpload")).click();
				Thread.sleep(5000);
				
				File scrFile1 = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		        FileUtils.copyFile(scrFile1, new File("C:\\Abhishek\\ABHISHEK SHARMA\\workspace\\FedExApplication\\src\\TestFiles\\AddressImport02.png"));
		        Thread.sleep(3000);
				driver.findElement(By.id("cmdProcess")).click();
				System.out.println("Address Import with Comma Delimeter : PASS");
			}
			
			if(i==1)
			{
				Thread.sleep(5000);
				driver.findElement(By.linkText("Import")).click();
				WebElement ele = driver.findElement(By.id("ddlDelimeter"));
				Select opt = new Select(ele);
				opt.selectByVisibleText("Semicolon");	
				driver.findElement(By.id("fileUpload")).sendKeys("C:\\Abhishek\\ABHISHEK SHARMA\\workspace\\FedExApplication\\src\\TestFiles\\Import_Semicolon.txt");
				WebElement ele4 = driver.findElement(By.id("ddlClearAddr"));
				Select opt1 = new Select(ele4);
				opt1.selectByVisibleText("No, don't clear old addresses");
				
			
		        driver.findElement(By.id("cmdUpload")).click();
				Thread.sleep(5000);
				driver.findElement(By.id("cmdProcess")).click();
				System.out.println("Address Import with Semicolon Delimeter : PASS");
			}
			
			if(i==2)
			{
				Thread.sleep(5000);
				driver.findElement(By.linkText("Import")).click();
				WebElement ele = driver.findElement(By.id("ddlDelimeter"));
				Select opt = new Select(ele);
				opt.selectByVisibleText("Verticle Bar");	
				driver.findElement(By.id("fileUpload")).sendKeys("C:\\Abhishek\\ABHISHEK SHARMA\\workspace\\FedExApplication\\src\\TestFiles\\Import_Verticle Bar.txt");
				WebElement ele4 = driver.findElement(By.id("ddlClearAddr"));
				Select opt1 = new Select(ele4);
				opt1.selectByVisibleText("No, don't clear old addresses");
				
			
		        driver.findElement(By.id("cmdUpload")).click();
				Thread.sleep(5000);
				driver.findElement(By.id("cmdProcess")).click();
				System.out.println("Address Import with Verticle Bar Delimeter : PASS");
			}
			
			if(i==3)
			{
				Thread.sleep(5000);
				driver.findElement(By.linkText("Import")).click();
				WebElement ele = driver.findElement(By.id("ddlDelimeter"));
				Select opt = new Select(ele);
				opt.selectByVisibleText("Tab");	
				driver.findElement(By.id("fileUpload")).sendKeys("C:\\Abhishek\\ABHISHEK SHARMA\\workspace\\FedExApplication\\src\\TestFiles\\Import_Tab.txt");
				WebElement ele4 = driver.findElement(By.id("ddlClearAddr"));
				Select opt1 = new Select(ele4);
				opt1.selectByVisibleText("No, don't clear old addresses");
			
		        driver.findElement(By.id("cmdUpload")).click();
				Thread.sleep(5000);
				driver.findElement(By.id("cmdProcess")).click();
				System.out.println("Address Import with Tab Delimeter : PASS");
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

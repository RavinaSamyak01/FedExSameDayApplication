package otherScreens;

import java.io.File;
import java.io.IOException;
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

public class AddressImport {

	static WebDriver driver;
	public static void main(String[] args) throws InterruptedException, IOException {

		addressImport();
        
	}

	public  static void addressImport() throws InterruptedException, IOException
	{
		System.setProperty("webdriver.chrome.driver","C:\\Abhishek\\ABHISHEK SHARMA\\workspace\\Driver\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		driver = new ChromeDriver(options);
        options.addArguments("--test-type");
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.get("https://staging.fedexsameday.com");

		driver.findElement(By.id("logon_name")).clear();
		driver.findElement(By.id("logon_name")).sendKeys("testsamyak10");
        
		driver.findElement(By.id("logon_password")).clear();
		driver.findElement(By.id("logon_password")).sendKeys("samyak10");
        
		driver.findElement(By.id("cmdLogin")).click();
        driver.getTitle();
		Thread.sleep(5000);

		Actions builder = new Actions(driver);
		driver.findElement(By.linkText("Manage")).click();		
		
		WebElement ele1 = driver.findElement(By.linkText("Manage My Account"));		
		builder.moveToElement(ele1).build().perform();
		
		WebElement ele2 = driver.findElement(By.linkText("Manage My Account"));		
		builder.moveToElement(ele2).build().perform();
		
		driver.findElement(By.linkText("Address Book")).click();
		Thread.sleep(5000);
		driver.findElement(By.linkText("Import")).click();
	
		WebElement ele = driver.findElement(By.id("ddlDelimeter"));
		Select opt = new Select(ele);
		opt.selectByVisibleText("Comma");

		driver.findElement(By.id("fileUpload")).sendKeys("C:\\Abhishek\\ABHISHEK SHARMA\\workspace\\FedExApplication\\src\\TestFiles\\AddressImport.txt");
		
		WebElement ele4 = driver.findElement(By.id("ddlClearAddr"));
		Select opt1 = new Select(ele4);
		opt1.selectByVisibleText("Yes, clear all old addresses");
		
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("C:\\Abhishek\\ABHISHEK SHARMA\\workspace\\FedExApplication\\src\\TestFiles\\AddressImport.png"));

        driver.findElement(By.id("cmdUpload")).click();
		Thread.sleep(5000);
		
		driver.findElement(By.id("cmdProcess")).click();
		driver.close();
		driver.quit();		
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

package otherScreens;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyPreferences {
	static WebDriver driver;
	public static void main(String[] args) throws Exception , IOException 
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

		driver.findElement(By.id("cmdLogin")).click(); // Click on Login
		driver.getTitle();
		
		Actions builder = new Actions(driver);
		driver.findElement(By.linkText("Manage")).click();		
		
		waitForVisibilityOfElement(By.linkText("Manage My Account"), 5);
		WebElement ele1 = driver.findElement(By.linkText("Manage My Account"));		
		builder.moveToElement(ele1).build().perform();
		
		driver.findElement(By.linkText("My Preferences")).click();
		Thread.sleep(3000);
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,550)", "");
		
		driver.findElement(By.id("Ncsbuttons2_cmdSubmit")).click();
		Thread.sleep(5000);
		
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("C:\\Abhishek\\ABHISHEK SHARMA\\workspace\\FedExApplication\\src\\TestFiles\\MyPreferences.png"));
        System.out.println("My Preferences Test Case Executed successfully !!!");
		driver.findElement(By.id("Wuc_headerlogout1_lnkbtnLogout")).click();
        driver.close();
		driver.quit();
		
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

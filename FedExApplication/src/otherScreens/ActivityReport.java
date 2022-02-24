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

public class ActivityReport {
	static WebDriver driver;
	public static void main(String[] args) throws Exception , IOException {
		
		actReport();
	}

	public static void actReport() throws Exception, IOException
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
		
		waitForVisibilityOfElement(By.linkText("Run Reports"), 5);
		WebElement ele1 = driver.findElement(By.linkText("Run Reports"));		
		builder.moveToElement(ele1).build().perform();
		
		driver.findElement(By.linkText("Activity Report")).click();
		
		waitForVisibilityOfElement(By.id("ddAcctNum"), 5);

		WebElement el = driver.findElement(By.id("ddAcctNum"));
		Select opt = new Select(el);
		opt.selectByVisibleText("CONNECT FEDEX - # 992445229");
		
		driver.findElement(By.id("cmdRpt")).click();
		waitForVisibilityOfElement(By.id("cmdRpt"), 5);
		
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("C:\\Abhishek\\ABHISHEK SHARMA\\workspace\\FedExApplication\\src\\TestFiles\\ActivitySummary.png"));
        System.out.println("Activity Summary Test Case Executed successfully !!!");
		
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

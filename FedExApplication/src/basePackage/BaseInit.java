package basePackage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BaseInit {
	public static WebDriver driver;
	static StringBuilder msg = new StringBuilder();

	@BeforeSuite
	public void startUp() {
		System.setProperty("webdriver.chrome.driver", ".\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		driver = new ChromeDriver(options);
		options.addArguments("--test-type");
		driver.manage().window().maximize();
		driver.get("https://staging.fedexsameday.com/");

		// --login

	}

	public static void login() {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Header_fdx_main_liLogin")));
		driver.findElement(By.id("Header_fdx_main_liLogin")).click();
		driver.findElement(By.id("Header_fdx_main_logon_name")).clear();
		driver.findElement(By.id("Header_fdx_main_logon_name")).sendKeys("testsamyak10");
		// enter password
		driver.findElement(By.id("Header_fdx_main_logon_password")).clear();
		driver.findElement(By.id("Header_fdx_main_logon_password")).sendKeys("samyak10");

		driver.findElement(By.id("Header_fdx_main_cmdMenuLogin")).click();
		driver.getTitle();
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class=\"fdx-o-grid\"]")));
	}

	@AfterSuite
	public void end() {
		driver.close();
		driver.quit();
	}
}

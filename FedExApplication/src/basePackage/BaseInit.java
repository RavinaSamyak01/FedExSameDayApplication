package basePackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseInit {
	public static WebDriver driver;
	protected static StringBuilder msg = new StringBuilder();

	@BeforeSuite
	public void startUp() {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		// options.addArguments("--headless");
		options.addArguments("--incognito");
		options.addArguments("--test-type");
		options.addArguments("--no-proxy-server");
		options.addArguments("--proxy-bypass-list=*");
		options.addArguments("--disable-extensions");
		options.addArguments("--no-sandbox");
		options.addArguments("--start-maximized");
		// options.addArguments("window-size=800x600");
		// options.addArguments("window-size=1366x788");
		capabilities.setPlatform(Platform.ANY);
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		driver = new ChromeDriver(options);
		// Default size
		Dimension currentDimension = driver.manage().window().getSize();
		int height = currentDimension.getHeight();
		int width = currentDimension.getWidth();
		System.out.println("Current height: " + height);
		System.out.println("Current width: " + width);
		System.out.println("window size==" + driver.manage().window().getSize());
		// --Login
		login();

	}

	public void login() {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		driver.get("https://staging.fedexsameday.com/");
		msg.append("Step1 : Enter URL : PASS" + "\n");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Header_fdx_main_liLogin")));
		driver.findElement(By.id("Header_fdx_main_liLogin")).click();
		driver.findElement(By.id("Header_fdx_main_logon_name")).clear();
		driver.findElement(By.id("Header_fdx_main_logon_name")).sendKeys("testsamyak10");
		// enter password
		driver.findElement(By.id("Header_fdx_main_logon_password")).clear();
		driver.findElement(By.id("Header_fdx_main_logon_password")).sendKeys("samyak10");
		msg.append("Step2 : Enter UserName : PASS" + "\n");
		msg.append("Step3 : Enter Password : PASS" + "\n");
		driver.findElement(By.id("Header_fdx_main_cmdMenuLogin")).click();
		System.out.println("Login done");
		msg.append("Step4 : Application Login Successfully : PASS" + "\n");
		driver.getTitle();
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class=\"fdx-o-grid\"]")));
	}

	@AfterSuite
	public void end() {
		driver.close();
		driver.quit();
	}

	public static void setData(String sheetName, int row, int col, String value)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		String FilePath = ".\\src\\TestFiles\\FedExShipments.xlsx";

		File src = new File(FilePath);
		FileInputStream fis = new FileInputStream(src);
		Workbook workbook = WorkbookFactory.create(fis);
		FileOutputStream fos1 = new FileOutputStream(src);
		Sheet sh = workbook.getSheet(sheetName);

		sh.getRow(row).createCell(col).setCellValue(value);
		workbook.write(fos1);
		fos1.close();
	}

	public static String getData(String sheetName, int row, int col)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		String FilePath = ".\\src\\TestFiles\\FedExShipments.xlsx";

		File src = new File(FilePath);

		FileInputStream FIS = new FileInputStream(src);
		Workbook workbook = WorkbookFactory.create(FIS);
		Sheet sh1 = workbook.getSheet(sheetName);

		DataFormatter formatter = new DataFormatter();
		String Cell = formatter.formatCellValue(sh1.getRow(row).getCell(col));

		return Cell;
	}
}

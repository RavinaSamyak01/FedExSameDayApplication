package basePackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import io.github.bonigarcia.wdm.WebDriverManager;
import rateScreenFedEx.RateVerification;

public class BaseInit {
	public static WebDriver driver;
	protected static StringBuilder msg = new StringBuilder();
	public static Properties storage = new Properties();

	@BeforeSuite
	public void startUp() throws IOException, InterruptedException {
		storage = new Properties();
		FileInputStream fi = new FileInputStream(".\\src\\TestFiles\\config.properties");
		storage.load(fi);
		DesiredCapabilities capabilities = new DesiredCapabilities();
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		// options.addArguments("--headless");
		// options.addArguments("--incognito");
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

	public void login() throws InterruptedException, IOException {
		WebDriverWait wait = new WebDriverWait(driver, 50);

		String Env = storage.getProperty("Env");
		System.out.println("Env " + Env);

		if (Env.equalsIgnoreCase("Pre-Prod")) {
			String URL = storage.getProperty("PREPRODURL");
			driver.get(URL);
			msg.append("Step1 : Enter URL : PASS" + "\n");
			try {
				String UserName = storage.getProperty("PREPRODUserName");
				String Password = storage.getProperty("PREPRODPassword");
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Header_fdx_main_liLogin")));
				driver.findElement(By.id("Header_fdx_main_liLogin")).click();
				driver.findElement(By.id("Header_fdx_main_logon_name")).clear();
				driver.findElement(By.id("Header_fdx_main_logon_name")).sendKeys(UserName);
				// enter password
				driver.findElement(By.id("Header_fdx_main_logon_password")).clear();
				driver.findElement(By.id("Header_fdx_main_logon_password")).sendKeys(Password);
				msg.append("Step2 : Enter UserName : PASS" + "\n");
				msg.append("Step3 : Enter Password : PASS" + "\n");
			} catch (Exception loggedin) {
				try {
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fx-global-header")));
					msg.append("URL is working==PASS");
				} catch (Exception loginpage) {
					msg.append("URL is not working==FAIL");
					getScreenshot(driver, "LoginIssue");
					driver.quit();
					Env = storage.getProperty("Env");
					String File = ".\\src\\Screenshots\\LoginIssue.png";
					String subject = "Selenium Automation Script: " + Env + " FedEx Login";

					try {
						// asharma@samyak.com,sdas@samyak.com,pgandhi@samyak.com,byagnik@samyak.com,pdoshi@samyak.com
						// ravina.prajapati@samyak.com,asharma@samyak.com,parth.doshi@samyak.com
						Email.sendMail("ravina.prajapati@samyak.com,asharma@samyak.com,parth.doshi@samyak.com", subject,
								msg.toString(), File);
					} catch (Exception ex) {
						Logger.getLogger(RateVerification.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
			}

		} else if (Env.equalsIgnoreCase("STG"))

		{
			String URL = storage.getProperty("STGURL");
			driver.get(URL);
			msg.append("Step1 : Enter URL : PASS" + "\n");
			try {
				String UserName = storage.getProperty("STGUserName");
				String Password = storage.getProperty("STGPassword");
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Header_fdx_main_liLogin")));
				driver.findElement(By.id("Header_fdx_main_liLogin")).click();
				driver.findElement(By.id("Header_fdx_main_logon_name")).clear();
				driver.findElement(By.id("Header_fdx_main_logon_name")).sendKeys(UserName);
				// enter password
				driver.findElement(By.id("Header_fdx_main_logon_password")).clear();
				driver.findElement(By.id("Header_fdx_main_logon_password")).sendKeys(Password);
				msg.append("Step2 : Enter UserName : PASS" + "\n");
				msg.append("Step3 : Enter Password : PASS" + "\n");
			} catch (Exception loggedin) {
				try {
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fx-global-header")));
					msg.append("URL is working==PASS");
				} catch (Exception loginpage) {
					msg.append("URL is not working==FAIL");
					getScreenshot(driver, "LoginIssue");
					driver.quit();
					Env = storage.getProperty("Env");
					String File = ".\\src\\Screenshots\\LoginIssue.png";
					String subject = "Selenium Automation Script: " + Env + " FedEx Login";

					try {
						// asharma@samyak.com,sdas@samyak.com,pgandhi@samyak.com,byagnik@samyak.com,pdoshi@samyak.com
						// ravina.prajapati@samyak.com,asharma@samyak.com,parth.doshi@samyak.com
						Email.sendMail("ravina.prajapati@samyak.com,asharma@samyak.com,parth.doshi@samyak.com", subject,
								msg.toString(), File);
					} catch (Exception ex) {
						Logger.getLogger(RateVerification.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
			}

		} else if (Env.equalsIgnoreCase("DEV")) {
			String URL = storage.getProperty("DEVURL");
			driver.get(URL);
			msg.append("Step1 : Enter URL : PASS" + "\n");
			try {
				String UserName = storage.getProperty("DEVUserName");
				String Password = storage.getProperty("DEVPassword");
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Header_fdx_main_liLogin")));
				driver.findElement(By.id("Header_fdx_main_liLogin")).click();
				driver.findElement(By.id("Header_fdx_main_logon_name")).clear();
				driver.findElement(By.id("Header_fdx_main_logon_name")).sendKeys(UserName);
				// enter password
				driver.findElement(By.id("Header_fdx_main_logon_password")).clear();
				driver.findElement(By.id("Header_fdx_main_logon_password")).sendKeys(Password);
				msg.append("Step2 : Enter UserName : PASS" + "\n");
				msg.append("Step3 : Enter Password : PASS" + "\n");
			} catch (Exception loggedin) {
				try {
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fx-global-header")));
					msg.append("URL is working==PASS");
				} catch (Exception loginpage) {
					msg.append("URL is not working==FAIL");
					getScreenshot(driver, "LoginIssue");
					driver.quit();
					Env = storage.getProperty("Env");
					String File = ".\\src\\Screenshots\\LoginIssue.png";
					String subject = "Selenium Automation Script: " + Env + " FedEx Login";

					try {
						// asharma@samyak.com,sdas@samyak.com,pgandhi@samyak.com,byagnik@samyak.com,pdoshi@samyak.com
						// ravina.prajapati@samyak.com,asharma@samyak.com,parth.doshi@samyak.com
						Email.sendMail("ravina.prajapati@samyak.com,asharma@samyak.com,parth.doshi@samyak.com", subject,
								msg.toString(), File);
					} catch (Exception ex) {
						Logger.getLogger(RateVerification.class.getName()).log(Level.SEVERE, null, ex);
					}
				}

			}

		}
		try {
			Thread.sleep(2000);
			driver.findElement(By.id("Header_fdx_main_cmdMenuLogin")).click();
			System.out.println("Login done");
			driver.getTitle();
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class=\"fdx-o-grid\"]")));
			msg.append("Step4 : Application Login Successfully : PASS" + "\n");
			Env = storage.getProperty("Env");
			String subject = "Selenium Automation Script: " + Env + " FedEx Login";
			try {
				// asharma@samyak.com,sdas@samyak.com,pgandhi@samyak.com,byagnik@samyak.com,pdoshi@samyak.com
				// ravina.prajapati@samyak.com,asharma@samyak.com,parth.doshi@samyak.com
				Email.sendMail("ravina.prajapati@samyak.com,asharma@samyak.com,parth.doshi@samyak.com", subject,
						msg.toString(), "");
			} catch (Exception ex) {
				Logger.getLogger(RateVerification.class.getName()).log(Level.SEVERE, null, ex);
			}
		} catch (Exception loggedin) {
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fx-global-header")));
				msg.append("User is already loggedin");

			} catch (Exception frg) {
				driver.findElement(By.id("Header_fdx_main_liLogin")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Header_fdx_main_lblloginmmsg")));
				String ValMsg = driver.findElement(By.id("Header_fdx_main_lblloginmmsg")).getText();
				msg.append("Step4 : Application Login Successfully : FAIL, " + ValMsg + "\n");

				// --End the suit
				end();
				// Send Email

				Env = storage.getProperty("Env");
				String subject = "Selenium Automation Script: " + Env + " FedEx Login";
				try {
					// asharma@samyak.com,sdas@samyak.com,pgandhi@samyak.com,byagnik@samyak.com,pdoshi@samyak.com
					// ravina.prajapati@samyak.com,asharma@samyak.com,parth.doshi@samyak.com
					Email.sendMail("ravina.prajapati@samyak.com,asharma@samyak.com,parth.doshi@samyak.com", subject,
							msg.toString(), "");
				} catch (Exception ex) {
					Logger.getLogger(RateVerification.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
	}

	@AfterSuite
	public void end() {
		driver.close();
		driver.quit();
	}

	public static void setData(String FileName, String sheetName, int row, int col, String value)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		String Env = storage.getProperty("Env");
		String FilePath = null;

		// --Rate Verification File
		if (Env.equalsIgnoreCase("Pre-Prod") && FileName.contains("RateVerification")) {
			FilePath = storage.getProperty("PREPRD_RateVerificationFile");
		} else if (Env.equalsIgnoreCase("STG") && FileName.contains("RateVerification")) {
			FilePath = storage.getProperty("STG_RateVerificationFile");
		} else if (Env.equalsIgnoreCase("DEV") && FileName.contains("RateVerification")) {
			FilePath = storage.getProperty("DEV_RateVerificationFile");
		}

		// --Shipment Creation File
		else if (Env.equalsIgnoreCase("Pre-Prod") && FileName.contains("ShipmentCreation")) {
			FilePath = storage.getProperty("PREPRD_ShipmentCreationFile");
		} else if (Env.equalsIgnoreCase("STG") && FileName.contains("ShipmentCreation")) {
			FilePath = storage.getProperty("STG_ShipmentCreationFile");
		} else if (Env.equalsIgnoreCase("DEV") && FileName.contains("ShipmentCreation")) {
			FilePath = storage.getProperty("DEV_ShipmentCreationFile");
		}

		// --Crud Operation File
		else if (Env.equalsIgnoreCase("Pre-Prod") && FileName.contains("CrudOperation")) {
			FilePath = storage.getProperty("PREPRD_CrudOperationFile");
		} else if (Env.equalsIgnoreCase("STG") && FileName.contains("CrudOperation")) {
			FilePath = storage.getProperty("STG_CrudOperationFile");
		} else if (Env.equalsIgnoreCase("DEV") && FileName.contains("CrudOperation")) {
			FilePath = storage.getProperty("DEV_CrudOperationFile");
		}
		// --Cheetah Process File
		else if (Env.equalsIgnoreCase("Pre-Prod") && FileName.contains("CheetahProcess")) {
			FilePath = storage.getProperty("PREPRD_CheetahProcessFile");
		} else if (Env.equalsIgnoreCase("STG") && FileName.contains("CheetahProcess")) {
			FilePath = storage.getProperty("STG_CheetahProcessFile");
		} else if (Env.equalsIgnoreCase("DEV") && FileName.contains("CheetahProcess")) {
			FilePath = storage.getProperty("DEV_CheetahProcessFile");
		}

		File src = new File(FilePath);
		FileInputStream fis = new FileInputStream(src);
		Workbook workbook = WorkbookFactory.create(fis);
		FileOutputStream fos1 = new FileOutputStream(src);
		Sheet sh = workbook.getSheet(sheetName);

		sh.getRow(row).createCell(col).setCellValue(value);
		workbook.write(fos1);
		fos1.close();
	}

	public static String getData(String FileName, String sheetName, int row, int col)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		String Env = storage.getProperty("Env");
		String FilePath = null;

		// --Rate Verification File
		if (Env.equalsIgnoreCase("Pre-Prod") && FileName.contains("RateVerification")) {
			FilePath = storage.getProperty("PREPRD_RateVerificationFile");
			System.out.println("File==" + FilePath);
		} else if (Env.equalsIgnoreCase("STG") && FileName.contains("RateVerification")) {
			FilePath = storage.getProperty("STG_RateVerificationFile");
			System.out.println("File==" + FilePath);
		} else if (Env.equalsIgnoreCase("DEV") && FileName.contains("RateVerification")) {
			FilePath = storage.getProperty("DEV_RateVerificationFile");
		}

		// --Shipment Creation File
		else if (Env.equalsIgnoreCase("Pre-Prod") && FileName.contains("ShipmentCreation")) {
			FilePath = storage.getProperty("PREPRD_ShipmentCreationFile");
			System.out.println("File==" + FilePath);
		} else if (Env.equalsIgnoreCase("STG") && FileName.contains("ShipmentCreation")) {
			FilePath = storage.getProperty("STG_ShipmentCreationFile");
			System.out.println("File==" + FilePath);
		} else if (Env.equalsIgnoreCase("DEV") && FileName.contains("ShipmentCreation")) {
			FilePath = storage.getProperty("DEV_ShipmentCreationFile");
		}

		// --Crud Operation File
		else if (Env.equalsIgnoreCase("Pre-Prod") && FileName.contains("CrudOperation")) {
			FilePath = storage.getProperty("PREPRD_CrudOperationFile");
			System.out.println("File==" + FilePath);
		} else if (Env.equalsIgnoreCase("STG") && FileName.contains("CrudOperation")) {
			FilePath = storage.getProperty("STG_CrudOperationFile");
			System.out.println("File==" + FilePath);

		} else if (Env.equalsIgnoreCase("DEV") && FileName.contains("CrudOperation")) {
			FilePath = storage.getProperty("DEV_CrudOperationFile");
		}
		// --Cheetah Process File
		else if (Env.equalsIgnoreCase("Pre-Prod") && FileName.contains("CheetahProcess")) {
			FilePath = storage.getProperty("PREPRD_CheetahProcessFile");
			System.out.println("File==" + FilePath);

		} else if (Env.equalsIgnoreCase("STG") && FileName.contains("CheetahProcess")) {
			FilePath = storage.getProperty("STG_CheetahProcessFile");
			System.out.println("File==" + FilePath);

		} else if (Env.equalsIgnoreCase("DEV") && FileName.contains("CheetahProcess")) {
			FilePath = storage.getProperty("DEV_CheetahProcessFile");
		}

		File src = new File(FilePath);
		FileInputStream FIS = new FileInputStream(src);
		Workbook workbook = WorkbookFactory.create(FIS);
		Sheet sh1 = workbook.getSheet(sheetName);

		DataFormatter formatter = new DataFormatter();
		String Cell = formatter.formatCellValue(sh1.getRow(row).getCell(col));

		return Cell;
	}

	public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException {

		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		// after execution, you could see a folder "FailedTestsScreenshots" under src
		// folder
		String destination = System.getProperty("user.dir") + "/src/Screenshots/" + screenshotName + ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}
}

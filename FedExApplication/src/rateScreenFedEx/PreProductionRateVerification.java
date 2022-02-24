package rateScreenFedEx;

	import java.io.File;
	import java.io.FileInputStream;
	import java.io.FileOutputStream;
	import org.apache.poi.ss.usermodel.DataFormatter;
	import org.apache.poi.ss.usermodel.Sheet;
	import org.apache.poi.ss.usermodel.Workbook;
	import org.apache.poi.ss.usermodel.WorkbookFactory;
	import org.openqa.selenium.By;
	import org.openqa.selenium.JavascriptExecutor;
	import org.openqa.selenium.Keys;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.chrome.ChromeDriver;
	import org.openqa.selenium.chrome.ChromeOptions;
	import org.openqa.selenium.support.ui.ExpectedConditions;
	import org.openqa.selenium.support.ui.Select;
	import org.openqa.selenium.support.ui.WebDriverWait;
	
	
	public class PreProductionRateVerification
	{
		static WebDriver driver;
		
		
		public static void doLogin() throws Exception {
	        pause(1000);
	        driver.findElement(By.id("logon_name")).clear();
	        driver.findElement(By.id("logon_name")).sendKeys("testsamyak6");
	        //enter password
	        driver.findElement(By.id("logon_password")).clear();
	        driver.findElement(By.id("logon_password")).sendKeys("samyak6");
	        
	        driver.findElement(By.id("cmdLogin")).click();
	        driver.getTitle();
	        prService();
	    }
		
		public static void main(String[] args) throws Exception
			{
				System.setProperty("webdriver.chrome.driver","C:\\Abhishek\\ABHISHEK SHARMA\\workspace\\Driver\\chromedriver.exe");
				ChromeOptions options = new ChromeOptions();
				driver = new ChromeDriver(options);
		        options.addArguments("--test-type");
		        driver.manage().window().maximize();
		        driver.get("http://10.20.205.70:9060/fdx_main.aspx");
		        doLogin();
			}
				public static void prService() throws Exception
				{
					File src = new File("C:\\Abhishek\\ABHISHEK SHARMA\\workspace\\FedExApplication\\src\\TestFiles\\PreProductionRateVerification.xlsx");
					FileInputStream fis = new FileInputStream(src);
					Workbook workbook = WorkbookFactory.create(fis);
					Sheet sh1 = workbook.getSheet("Sheet1");
					//int rcount = sh1.getLastRowNum();
					DataFormatter formatter = new DataFormatter();
					
						for(int i=1; i<31; i++)
						{
							driver.findElement(By.linkText("Rate")).click();
							driver.getTitle();
					        pause(1000);
					        
					        String PUZip = formatter.formatCellValue(sh1.getRow(i).getCell(0));
							driver.findElement(By.id("txtOrig")).clear();
							driver.findElement(By.id("txtOrig")).sendKeys(PUZip);
					        driver.findElement(By.id("txtOrig")).sendKeys(Keys.TAB);
					        waitForVisibilityOfElement(By.xpath(".//*[@id='Process']"), 2);
					        waitForInVisibilityOfElement(By.xpath(".//*[@id='Process']"), 30);

					        
					        String DLZip = formatter.formatCellValue(sh1.getRow(i).getCell(1));
							driver.findElement(By.id("txtDest")).clear();
							driver.findElement(By.id("txtDest")).sendKeys(DLZip);
					        driver.findElement(By.id("txtDest")).sendKeys(Keys.TAB);
					        waitForVisibilityOfElement(By.xpath(".//*[@id='Process']"), 2);
					        waitForInVisibilityOfElement(By.xpath(".//*[@id='Process']"), 30);
					        
					        waitForVisibilityOfElement(By.xpath(".//*[@id='Process']"), 2);
					        waitForInVisibilityOfElement(By.xpath(".//*[@id='Process']"), 30);
					        waitForVisibilityOfElement(By.id("txtPieces"), 30);
					        driver.findElement(By.id("txtPieces")).sendKeys(Keys.BACK_SPACE);
					        driver.findElement(By.id("txtPieces")).sendKeys("1");
					        driver.findElement(By.id("txtPieces")).sendKeys(Keys.TAB);
					        pause(1000);
					        driver.findElement(By.id("txtDimLen0")).clear();
					        driver.findElement(By.id("txtDimLen0")).sendKeys("5");
					        driver.findElement(By.id("txtDimWid0")).clear();
					        driver.findElement(By.id("txtDimWid0")).sendKeys("5");
					        driver.findElement(By.id("txtDimHt0")).clear();
					        driver.findElement(By.id("txtDimHt0")).sendKeys("5");
					        driver.findElement(By.id("txtActWt0")).clear();
					        driver.findElement(By.id("txtActWt0")).sendKeys("5");
					        driver.findElement(By.id("anchor1xx")).click();
							
					        driver.findElement(By.xpath("//a[contains(.,'Today')]")).click();
							
							Select select = new Select(driver.findElement(By.id("ddlPickupHour")));
					        select = new Select(driver.findElement(By.id("ddlPickupHour")));
					        select.selectByVisibleText("11");
		
					        select = new Select(driver.findElement(By.id("ddlPickupMinutes")));
					        select.selectByVisibleText("45");
		
					        select = new Select(driver.findElement(By.xpath(".//*[@name='ddlTimeType']")));
					        select.selectByVisibleText("AM");
					        driver.findElement(By.id("btngetQuickquote")).click();
					        JavascriptExecutor jse = (JavascriptExecutor)driver;
							jse.executeScript("window.scrollBy(0,350)", "");					
					        
							waitForVisibilityOfElement(By.xpath(".//*[@id='Process']"), 2);
					        waitForInVisibilityOfElement(By.xpath(".//*[@id='Process']"), 30);
					        waitForVisibilityOfElement(By.id("btnShip"), 30);
							
							String serviceid = formatter.formatCellValue(sh1.getRow(i).getCell(2));
							  File src1 = new File("C:\\Abhishek\\ABHISHEK SHARMA\\workspace\\FedExApplication\\src\\TestFiles\\PreProductionRateVerification.xlsx");
								FileOutputStream fis1 = new FileOutputStream(src1);
								Sheet sh2 = workbook.getSheet("Sheet1");
						
							if(serviceid.equals("PR"))
							{
					       
					        pause(1000);
					        String actrate = driver.findElement(By.id("btnPR")).getText();
					        System.out.println(actrate);
					        String ExpectedRate = formatter.formatCellValue(sh1.getRow(i).getCell(3));
				      		sh2.getRow(i).createCell(4).setCellValue(actrate);
							if(!actrate.equals(ExpectedRate))
							{
												
								sh2.getRow(i).createCell(5).setCellValue("FAIL");
								workbook.write(fis1);
								fis1.close();
							}			
							
							else
							{
								File src1srcstatus = new File("C:\\Abhishek\\ABHISHEK SHARMA\\workspace\\FedExApplication\\src\\TestFiles\\PreProductionRateVerification.xlsx");
								FileOutputStream fis1srcstatus = new FileOutputStream(src1srcstatus);
								Sheet sh2srcstatus = workbook.getSheet("Sheet1");
								sh2srcstatus.getRow(i).createCell(5).setCellValue("PASS");
								workbook.write(fis1srcstatus);
								fis1.close();
							}
							}
							
							else if(serviceid.equals("S2"))
							{
					        
					        pause(1000);
					        String actrate = driver.findElement(By.id("btnS2")).getText();
					        System.out.println(actrate);
					        String ExpectedRate = formatter.formatCellValue(sh1.getRow(i).getCell(3));
							sh2.getRow(i).createCell(4).setCellValue(actrate);
							if(!actrate.equals(ExpectedRate))
							{
								sh2.getRow(i).createCell(5).setCellValue("FAIL");
								workbook.write(fis1);
								fis1.close();
								
							}			
							
							else
							{
								sh2.getRow(i).createCell(5).setCellValue("PASS");
								workbook.write(fis1);
								fis1.close();
							}
							}
							else if(serviceid.equals("EC"))
							{
					        
					        pause(1000);
					        String actrate = driver.findElement(By.id("btnEC")).getText();
					        System.out.println(actrate);
					        String ExpectedRate = formatter.formatCellValue(sh1.getRow(i).getCell(3));
							sh2.getRow(i).createCell(4).setCellValue(actrate);
							if(!actrate.equals(ExpectedRate))
							{
								sh2.getRow(i).createCell(5).setCellValue("FAIL");
								workbook.write(fis1);
								fis1.close();
								
							}			
							
							else
							{
								sh2.getRow(i).createCell(5).setCellValue("PASS");
								workbook.write(fis1);
								fis1.close();
							}
							}
							else if(serviceid.equals("DR"))
							{
					        
					        pause(1000);
					        String actrate = driver.findElement(By.id("btnDR")).getText();
					        System.out.println(actrate);
					        String ExpectedRate = formatter.formatCellValue(sh1.getRow(i).getCell(3));
							sh2.getRow(i).createCell(4).setCellValue(actrate);
							if(!actrate.equals(ExpectedRate))
							{
								sh2.getRow(i).createCell(5).setCellValue("FAIL");
								workbook.write(fis1);
								fis1.close();
								
							}			
							
							else
							{
								sh2.getRow(i).createCell(5).setCellValue("PASS");
								workbook.write(fis1);
								fis1.close();
							}
							}
							else if(serviceid.equals("AIR"))
							{
					        
					        pause(1000);
					        String actrate = driver.findElement(By.xpath("//a[contains(.,'$')]")).getText();
					        System.out.println(actrate);
					        String ExpectedRate = formatter.formatCellValue(sh1.getRow(i).getCell(3));
							sh2.getRow(i).createCell(4).setCellValue(actrate);
							if(!actrate.equals(ExpectedRate))
							{
								sh2.getRow(i).createCell(5).setCellValue("FAIL");
								workbook.write(fis1);
								fis1.close();
								
							}			
							
							else
							{
								sh2.getRow(i).createCell(5).setCellValue("PASS");
								workbook.write(fis1);
								fis1.close();
							}
						
							}
							
							else if(serviceid.equals("DRV"))
							{
					        
					        pause(1000);
					        String actrate = driver.findElement(By.xpath("//a[contains(.,'$')]")).getText();
					        System.out.println(actrate);
					        String ExpectedRate = formatter.formatCellValue(sh1.getRow(i).getCell(3));
							sh2.getRow(i).createCell(4).setCellValue(actrate);
							if(!actrate.equals(ExpectedRate))
							{
								sh2.getRow(i).createCell(5).setCellValue("FAIL");
								workbook.write(fis1);
								fis1.close();
								
							}			
							
							else
							{
								sh2.getRow(i).createCell(5).setCellValue("PASS");
								workbook.write(fis1);
								fis1.close();
							}
						
							}
							
						}
					driver.close();
				}
				
				 public static void pause(final int iTimeInMillis) {
				        try {
				            Thread.sleep(iTimeInMillis);
				        } catch (InterruptedException ex) {
				            System.out.println(ex.getMessage());
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

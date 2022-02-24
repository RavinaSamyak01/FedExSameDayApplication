package com.fdxMasterScreens;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.fdxMasterScreens.ExcelDataProvider;

public class TrackShipment extends BaseClass {

	public static void trckShipment() throws IOException, InterruptedException
	{

		for(int i=1; i<3; i++)
		{
			if (i==1) {
				driver.findElement(By.linkText("Track")).click();		
				driver.findElement( By.linkText("Track by Tracking Number")).click();
				
				ExcelDataProvider excelDataProvider = new ExcelDataProvider();
				String tracking = excelDataProvider.getData("Sheet1", 2, 15);

				driver.findElement(By.id("track_num")).sendKeys(tracking);
				
				driver.findElement(By.id("cmdTrack")).click();
				Thread.sleep(5000);
				
				File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		        FileUtils.copyFile(scrFile, new File("C:\\Abhishek\\ABHISHEK SHARMA\\workspace\\FedExApplication\\src\\TestFiles\\Single_ShipmentTracking.png"));
		        
			}
			if (i==2) 
			{
				driver.findElement(By.linkText("Track")).click();		
				driver.findElement( By.linkText("Track by Tracking Number")).click();

				ExcelDataProvider excelDataProvider = new ExcelDataProvider();
				String t1 = excelDataProvider.getData("Sheet1", 2, 15);
				String t2 = excelDataProvider.getData("Sheet1", 3, 15);

				driver.findElement(By.id("track_num")).sendKeys(t1+","+t2);
				
				driver.findElement(By.id("cmdTrack")).click();
				Thread.sleep(5000);
				
				File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		        FileUtils.copyFile(scrFile, new File("C:\\Abhishek\\ABHISHEK SHARMA\\workspace\\FedExApplication\\src\\TestFiles\\Multiple_ShipmentTracking_1.png"));
		        Thread.sleep(2000);
		        driver.findElement(By.id("dgItin_shipNum_0")).click();
		        Thread.sleep(3000);
		        File scrFile2 = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		        FileUtils.copyFile(scrFile2, new File("C:\\Abhishek\\ABHISHEK SHARMA\\workspace\\FedExApplication\\src\\TestFiles\\Multiple_ShipmentTracking_2.png"));
		        
			}
		}
        System.out.println("Shipment Tracking Test Case Executed successfully !!!");
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

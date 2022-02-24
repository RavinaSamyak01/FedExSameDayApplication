package com.fdxMasterScreens;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyProfile extends BaseClass {

	public static void myProfile() throws IOException, InterruptedException
	{
		Actions builder = new Actions(driver);
		driver.findElement(By.linkText("Manage")).click();		
		
		waitForVisibilityOfElement(By.linkText("Manage My Account"), 5);
		WebElement ele1 = driver.findElement(By.linkText("Manage My Account"));		
		builder.moveToElement(ele1).build().perform();
		
		driver.findElement(By.linkText("My Profile")).click();
		Thread.sleep(3000);
		driver.findElement(By.id("ncsButtons_cmdSubmit")).click();
		Thread.sleep(5000);
		
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("C:\\Abhishek\\ABHISHEK SHARMA\\workspace\\FedExApplication\\src\\TestFiles\\MyProfile.png"));
        System.out.println("My Profile Test Case Executed successfully !!!");
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

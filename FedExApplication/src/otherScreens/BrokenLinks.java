package otherScreens;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;


public class BrokenLinks {

	static WebDriver driver;
	static StringBuilder msg = new StringBuilder();
	public static void main(String[] args) {
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
        

		Select select = new Select(driver.findElement(By.id("startpage")));
        select.selectByIndex(0); // Rate Quote
		
        driver.findElement(By.id("cmdLogin")).click();
        driver.getTitle();
        driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS) ;
        

		List<WebElement> links=driver.findElements(By.tagName("a"));
		
		System.out.println("Total links are "+links.size());
		
		for(int i=0;i<links.size();i++)
		{
			
			WebElement ele= links.get(i);
			
			String url=ele.getAttribute("href");
			
			verifyLinkActive(url);
			
		}
		
		String subject = "STAGING: FedExSameDay Broken Links Using SELENIUM";
		try {
			Email.sendMail("asharma@samyak.com,pgandhi@samyak.com,sdas@samyak.com,pdoshi@samyak.com,byagnik@samyak.com", subject, msg.toString(), "");
		} catch (Exception ex) {
			Logger.getLogger(BrokenLinks.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		
		driver.close();
		
	}
		public static void verifyLinkActive(String linkUrl)
		{
	        try 
	        {
	           URL url = new URL(linkUrl);
	           
	           HttpURLConnection httpURLConnect=(HttpURLConnection)url.openConnection();
	           
	           httpURLConnect.setConnectTimeout(3000);
	           
	           httpURLConnect.connect();
	           
	           if(httpURLConnect.getResponseCode()==200)
	           {
	               System.out.println(linkUrl+" - "+httpURLConnect.getResponseMessage());
	               msg.append(linkUrl+" - "+httpURLConnect.getResponseMessage()+ "\n");
	            }
	          if(httpURLConnect.getResponseCode()==HttpURLConnection.HTTP_NOT_FOUND)  
	           {
	               System.out.println(linkUrl+" - "+httpURLConnect.getResponseMessage() + " - "+ HttpURLConnection.HTTP_NOT_FOUND);
	               msg.append(linkUrl+" - "+httpURLConnect.getResponseMessage() + " - "+ HttpURLConnection.HTTP_NOT_FOUND+ "\n");
	            }
	        } catch (Exception e) {
	           
	        }
	     
		}

}


package seleniumConcept;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class BrokenLinks {
	
	public static void main(String[] args) throws InterruptedException, MalformedURLException, IOException {
		// TODO Auto-generated method stub
		System.setProperty("webdriver.chrome.driver","C:\\Users\\DELL\\Desktop\\QA\\eclipse-workspace\\drivers\\chromedriver.exe");
		WebDriver driver = new ChromeDriver(); //launch chrome
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30,TimeUnit.SECONDS);
		
		//driver.get("https://www.imdb.com/");
		driver.get("https://makemysushi.com/404?");
		
		//1.get the list of all links and images
		List<WebElement> links= driver.findElements(By.tagName("a"));
		links.addAll(driver.findElements(By.tagName("img")));
		
		System.out.println("Size of full links and images ---> " +links.size());
		
		List<WebElement> activeLinks = new ArrayList<WebElement>();
		
		//2. Iterate links : exclude all the links and images which doesn't have any href attribute
		
//		for(int i=0;i<links.size();i++) {
//			//Thread.sleep(3000);
//			if(links.get(i).getAttribute("href") != null /*&& (!links.get(i).getAttribute("href").contains("javascript"))*/) {
//				activeLinks.add(links.get(i));
//			}
//		}
//		System.out.println("Size of active links and images ---> "+activeLinks.size());
		
				//3. check the href url, with the httpconnection api
				//200--- ok
				//400--- Bad request
				//404--- not found
				//500--- internal server error
		
//		for(int j=0;j<activeLinks.size();j++) {
//			HttpURLConnection connection = (HttpURLConnection)new URL(activeLinks.get(j).getAttribute("href")).openConnection();
//			connection.connect();
//			String response = connection.getResponseMessage();
//			connection.disconnect();
//			System.out.println(activeLinks.get(j).getAttribute("href") + "--->"+ response);
//		}
		
		// 2nd Method
		
		for(int i=0;i<links.size();i++) {
			WebElement element = links.get(i);
			String url = element.getAttribute("href");
			
			URL link = new URL(url);
			//create a connection using URL class object 'link'.
			HttpURLConnection httpConn = (HttpURLConnection)link.openConnection();
			Thread.sleep(2000);
			//Establish conection
			httpConn.connect();
			int resCode= httpConn.getResponseCode();// return rescode. if resCode is 400 then link broken
			
			if (resCode>=400) {
				System.out.println(url + " - "+" is broken link");
			}
			else
				System.out.println(url + " - "+" is valid link");
			
		}
		
		
		driver.quit();

	}

}

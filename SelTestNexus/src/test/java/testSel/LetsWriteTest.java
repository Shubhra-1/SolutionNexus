package testSel;

import java.time.Duration;
import java.util.List;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LetsWriteTest {

	WebDriver driver;
	 WebDriverWait wait;

	   String URL ="https://www.saucedemo.com/";

	    @Before
	    public void login() {

	    	WebDriverManager.chromedriver().setup();
	        driver = new ChromeDriver();
	        driver.get(URL);

	    }

	     @Test
	    /*
	    Login with problem_user, password : secret_sauce
	    Add items to cart and if any items fails to get added in cart print the name
	    as an example we have 6 items in inventory,
	    then only 2 items fails to get added in cart then print the name of those 2 items
	    * */
	    public void addItemsToCartVerify() throws InterruptedException {
	    	 driver.findElement(By.id("user-name")).sendKeys("problem_user");
	         driver.findElement(By.id("password")).sendKeys("secret_sauce");
	         driver.findElement(By.id("login-button")).click();

	         // Wait for login to complete
	         wait = new WebDriverWait(driver, Duration.ofSeconds(60));
	         wait.until(ExpectedConditions.urlToBe("https://www.saucedemo.com/inventory.html"));
	         Thread.sleep(1000);
	         

	         // Add items to cart
	         
	         List<WebElement> addToCartButtons = driver.findElements(By.xpath("//*[starts-with(@id, 'add-to-cart')]"));
	         for (int i = 0; i < addToCartButtons.size(); i++) {
	             addToCartButtons.get(i).click();

	         }
	         System.out.println("Number of items available for adding:" + addToCartButtons.size());
	         
	         Thread.sleep(1000);

	         // Check if any items failed to get added to the cart
	         
	       
	         
	         List<WebElement> itemNotAdded = driver.findElements(By.xpath("//*[starts-with(@id, 'add-to-cart')]"));
	         
	         
	         if (!itemNotAdded.isEmpty()) {
	        	 //List<WebElement> errorMessage = driver.findElement(By.className("inventory_item_name"));
	        	 System.out.println("The following items failed to get added to the cart:");
		            for ( WebElement item : itemNotAdded) {
		            	String itemName = item.findElement(By.xpath("../../..//div[@class='inventory_item_label']/a/div[@class='inventory_item_name']"))
		                        .getText();
		                System.out.println(itemName);
		               
		             }
	         }
	            
	             Thread.sleep(1000);
	        
	     }

	  
	 
	    @Test
	     /* Login with --> standard_user,  password : secret_sauce
	        Verify that user successfully logged in
	        sort the list - choose any option in DropDown.
	        verify the list is sorted correctly
	    * */
	    public void verifyIsLoginSuccessful() throws InterruptedException {
	    	// Login
	        driver.findElement(By.id("user-name")).sendKeys("standard_user");
	        driver.findElement(By.id("password")).sendKeys("secret_sauce");
	        driver.findElement(By.id("login-button")).click();

	        // Wait for login to complete
	        wait = new WebDriverWait(driver, Duration.ofSeconds(60));
	        wait.until(ExpectedConditions.urlToBe("https://www.saucedemo.com/inventory.html"));
	        Thread.sleep(1000);

	        // Verify login is successful (you can use appropriate assertions here)
	        String expectedTitle = "Swag Labs";
	        String actualTitle = driver.getTitle();
	        Assert.assertEquals(expectedTitle,actualTitle );
	        
	        Thread.sleep(1000);

	        // Sort the list (you can implement this part based on your requirements)
	        driver.findElement(By.className("select_container")).click();
	        Thread.sleep(1000);
	        driver.findElement(By.xpath("//option[@value='lohi']")).click();
	        Thread.sleep(1000);
	        

	        // Verify the list is sorted correctly (you can implement this part based on your requirements)
	        String expectedpage = "Price (low to high)";
	        String actualpage = driver.findElement(By.className("active_option")).getText();
	        Assert.assertEquals(expectedpage, actualpage);

	   }
	    @After
	    public void tearDown() {
	        driver.quit();
	    }
}

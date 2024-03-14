package com;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class amazon {
	private WebDriver driver;

	@SuppressWarnings("deprecation")
	@BeforeClass
	public void setUp() {

		// Configure Chrome options
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");

		// Initialize ChromeDriver
		driver = new ChromeDriver(options);

		// Implicit wait
		//	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test(priority = 1)
	public void testAddItemToCart() throws InterruptedException {
		// Navigate to Amazon homepage
		driver.navigate().to("https://www.amazon.in/");
		driver.navigate().refresh();
		// Search for a product and select it
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys("boss chair");
		driver.findElement(By.id("nav-search-submit-button")).click();
		driver.findElement(By.cssSelector("span.a-size-base-plus.a-color-base.a-text-normal")).click();
		Thread.sleep(5000);
		driver.getWindowHandles().forEach(tab -> driver.switchTo().window(tab));
		Thread.sleep(5000);
		// Add item to cart
		driver.findElement(By.xpath("//div[@id='ppd']//form[@id='addToCart']//span[@id='submit.add-to-cart']")).click();

		// Verify item is added to cart
		WebElement cartCountElement = driver.findElement(By.id("nav-cart-count"));
		int cartCount = Integer.parseInt(cartCountElement.getText());
		Assert.assertEquals(cartCount, 1, "Item is not added to cart successfully");
	}

	@Test(priority = 2)
	public void testUpdateShippingInformation() throws InterruptedException {
		// Navigate to Amazon homepage
		driver.navigate().to("https://www.amazon.in/");
		driver.navigate().refresh();
		// Search for a product and select it
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys("boss chair");
		driver.findElement(By.id("nav-search-submit-button")).click();
		driver.findElement(By.cssSelector("span.a-size-base-plus.a-color-base.a-text-normal")).click();
		Thread.sleep(5000);
		driver.getWindowHandles().forEach(tab -> driver.switchTo().window(tab));
		Thread.sleep(5000);
		// Add item to cart
		driver.findElement(By.xpath("//div[@id='ppd']//form[@id='addToCart']//span[@id='submit.add-to-cart']")).click();
		// Proceed to checkout
		driver.findElement(By.id("nav-cart")).click();
		driver.findElement(By.cssSelector("input[name='proceedToRetailCheckout']")).click();
		Thread.sleep(2000);

		// Enter email/phone number
		WebElement emailField = driver.findElement(By.id("ap_email"));
		emailField.sendKeys("testingautoamaz@gmail.com");
		driver.findElement(By.xpath("//input[@id='continue']")).click();

		// Enter password
		WebElement passwordField = driver.findElement(By.id("ap_password"));
		passwordField.sendKeys("Sahoork54@");

		// Click on the sign-in button
		WebElement signInButton = driver.findElement(By.id("signInSubmit"));
		signInButton.click();
		Thread.sleep(2000);
		//Add shipping address
		driver.findElement(By.xpath("//a[@id='add-new-address-popover-link']")).click();
		Thread.sleep(2000);
		//Add personal information
		driver.findElement(By.xpath("//input[@id='address-ui-widgets-enterAddressFullName']")).sendKeys("Test4");
		driver.findElement(By.xpath("//input[@id='address-ui-widgets-enterAddressPhoneNumber']"))
		.sendKeys("9190876039");
		driver.findElement(By.xpath("//input[@id='address-ui-widgets-enterAddressLine1']")).sendKeys("flat no 5,H N 2726,");
		driver.findElement(By.xpath("//input[@id='address-ui-widgets-enterAddressLine2']")).sendKeys("sector-2, Faridabad");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@id='address-ui-widgets-use-as-my-default']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//span[@id='address-ui-widgets-form-submit-button']")).click();
		Thread.sleep(3000);

		// Verify shipping address is updated
		WebElement updatedAddress = driver.findElement(By.xpath("//div[@class='a-column a-span8']//h1"));
		Assert.assertTrue(updatedAddress.getText().contains("Checkout"),
				"Shipping address is not updated successfully");
	}

	@Test(priority = 3)
	public void testRemoveItemFromCart() throws InterruptedException {
		// Navigate to Amazon homepage
		driver.navigate().to("https://www.amazon.in/");
		Thread.sleep(2000);
		// Search for a product and select it
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys("boss chair");
		driver.findElement(By.id("nav-search-submit-button")).click();
		driver.findElement(By.cssSelector("span.a-size-base-plus.a-color-base.a-text-normal")).click();
		Thread.sleep(5000);
		driver.getWindowHandles().forEach(tab->driver.switchTo().window(tab));
		Thread.sleep(5000);
		//Add item to amazon cart
		driver.findElement(By.xpath("//div[@id='ppd']//form[@id='addToCart']//span[@id='submit.add-to-cart']")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//span[@id='nav-cart-count']")).click();
		Thread.sleep(2000);
		// Remove item from cart
		List<WebElement> deleteButton = driver.findElements(By.cssSelector("input[data-action='delete']"));
		for(int i=0; i<deleteButton.size(); i++) {
			Thread.sleep(1000);
			driver.findElement(By.cssSelector("input[data-action='delete']")).click();
		}
		Thread.sleep(3000);


		// Verify item is removed from cart
		WebElement emptyCartMessage = driver.findElement(By.xpath("//div//h1[@class='a-spacing-mini a-spacing-top-base']"));
		Assert.assertTrue(emptyCartMessage.getText().contains("Cart is empty."),
				"Item is not removed from cart successfully");
	}

	@AfterClass
	public void tearDown() {
		// Close the browser
		 driver.quit();
	}
}
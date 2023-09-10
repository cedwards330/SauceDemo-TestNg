package SauceDemo;

import java.time.Duration;
import java.util.List;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

public class sauceDemoTest {
	
private static final String LOGIN_PAGE_URL = "https://www.saucedemo.com/";
	
	private static final By USERNAME_ID = By.id("user-name");
	private static final By PASSWORD_ID = By.id("password");
	private static final By LOGIN_XPATH = By.xpath("//input[@data-test = 'login-button']");
	
	private static final String USERNAME = "standard_user";
	private static final String PASSWORD = "secret_sauce";
	
	
	private static final String INVENTORY_PAGE_URL = "https://www.saucedemo.com/inventory.html";
	
	private static final By ITEM_CLASS = By.className("inventory_item");
	private static final By ITEM_NAME_XPATH = By.xpath("(//div[@class = 'inventory_item_name'])[1]");
	private static final By ITEM_PRICE_XPATH = By.xpath("(//div[@class = 'inventory_item_price'])[1]");

	private static final By ADD_TO_CART_XPATH = By.xpath("//button[@data-test = 'add-to-cart-sauce-labs-backpack']");
	private static final By ADD_TO_CART2_XPATH = By.xpath("//button[@data-test = 'add-to-cart-sauce-labs-bike-light']");
	private static final By ADD_TO_CART3_XPATH = By.xpath("//button[@data-test = 'add-to-cart-sauce-labs-bolt-t-shirt']");

	private static final By ITEM_COUNT = By.className("'shopping_cart_badge");
	//add-to-cart-sauce-labs-bolt-t-shirt
	private static final By CART_ICON_CLASS = By.className("shopping_cart_badge");	
	
	private static final String CART_PAGE_URL = "https://www.saucedemo.com/cart.html";
	
	private static final By CART_ITEM_NAME_XPATH = By.xpath("//div[@class = 'inventory_item_name']");
	private static final By CART_ITEM_PRICE_XPATH = By.xpath("//div[@class = 'inventory_item_price']");
	private static final By CART_CHECKOUT_ID = By.id("checkout");
	private static final By REMOVE_FROM_CART1_XPATH = By.xpath("//button[@data-test = 'remove-sauce-labs-backpack']");
	private static final By REMOVE_FROM_CART2_XPATH = By.xpath("//button[@data-test = 'remove-sauce-labs-bike-light']");
	private static final By REMOVE_FROM_CART3_XPATH = By.xpath("//button[@data-test = 'remove-sauce-labs-bolt-t-shirt']");
	private static final By CONTINUE_SHOPPING_ID = By.id("continue-shopping");
	
	private static final String YOUR_INFORMATION_PAGE_URL = "https://www.saucedemo.com/checkout-step-one.html";
	
	private static final By FIRST_NAME_ID = By.id("first-name"); 
	private static final By LAST_NAME_ID = By.id("last-name"); 
	private static final By ZIP_CODE_ID = By.id("postal-code"); 
	private static final By CONTINUE_ID = By.id("continue"); 
	
	private static final String OVERVIEW_PAGE_URL = "https://www.saucedemo.com/checkout-step-two.html";
	
	private static final By OVERVIEW_ITEM_NAME_XPATH = By.xpath("//div[@class = 'inventory_item_name']");
	private static final By OVERVIEW_ITEM_PRICE_XPATH = By.xpath("//div[@class = 'inventory_item_price']");
	private static final By FINISH_ID = By.id("finish");
	
	private static final String CONFIRMATION_PAGE_URL = "https://www.saucedemo.com/checkout-complete.html";
	
	private static final By HEADER_CLASS = By.className("complete-header");
	
	//Test Data
	private static final String TYPE_FIRST_NAME = "John";
	private static final String TYPE_LAST_NAME = "Kemp";
	private static final String TYPE_ZIP = "A1B 2C3";
	
	private ChromeDriver driver;
	private WebDriverWait wait;
	
	@BeforeMethod
	public void setUp() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
	
	@Ignore
	public void user_Can_Place_Order() throws InterruptedException {
		openHomePage();
		Assert.assertEquals(getUrl(), LOGIN_PAGE_URL);
		
		typeUsername(USERNAME);
		typePassword(PASSWORD);
		clickLoginButton();
		
		Assert.assertEquals(getUrl(), INVENTORY_PAGE_URL);
		
		int itemCount = getItemsCount();
		Assert.assertTrue(itemCount > 0);
		
		String itemName = getItemName();
		Assert.assertFalse(itemName.isEmpty());
		
		double itemPrice = getItemPrice();
		Assert.assertTrue(itemPrice > 0);
		
		addToCartButton();
		
		int cartCount = getCartIconCount();
		Assert.assertEquals(cartCount, 1);
		
		Assert.assertEquals(getUrl(), CART_PAGE_URL);
		
		String cartItemName = getCartItemName();
		Assert.assertEquals(itemName, cartItemName);
		
		double cartItemPrice = getCartItemPrice();
		Assert.assertTrue(cartItemPrice == itemPrice);
		
		clickCheckoutButton();

		Assert.assertEquals(getUrl(), YOUR_INFORMATION_PAGE_URL);
		
		typeFirstName(TYPE_FIRST_NAME);
		tyepLastName(TYPE_LAST_NAME);
		typeZipCode(TYPE_ZIP);
		clickContinueButton();

		Assert.assertEquals(getUrl(), OVERVIEW_PAGE_URL);
		
		String overViewItemName =getOverviewItemName();
		Assert.assertEquals(itemName, overViewItemName);
		
		Double overviewItemPrice = getOverviewItemPrice();
		Assert.assertTrue(overviewItemPrice == itemPrice);
		
		clickFinishButton();
		
		Assert.assertEquals(getUrl(), CONFIRMATION_PAGE_URL);
		
		String headerText = getHeaderClass();
		Assert.assertEquals(headerText, "Thank you for your order!");
	}
	
	@Test
	public void user_Can_Place_3_Orders() throws InterruptedException {
		//home page
		openHomePage();
		Assert.assertEquals(getUrl(), LOGIN_PAGE_URL);
		
		typeUsername(USERNAME);
		typePassword(PASSWORD);
		clickLoginButton();
		
		//Inventory page
		Assert.assertEquals(getUrl(), INVENTORY_PAGE_URL);
		
		int itemCount = getItemsCount();
		Assert.assertTrue(itemCount > 0);
		
		addToCartButton();
		addToCartButton2();
		addToCartButton3();
		
		//int itemCount = getCartIconCount();
		Assert.assertTrue(itemCount > 0);
		
		clickCartIcon();

		//cart page
		Assert.assertEquals(getUrl(), CART_PAGE_URL);
		removeFromCartButton();
		removeFromCartButton2();
		removeFromCartButton3();

		//here, you should have an assertion about no products being displayed any longer

		//no need of this
		clickContinueShoppingButton();
		Assert.assertEquals(getUrl(), INVENTORY_PAGE_URL);
	}
	
	public void openHomePage() {
		driver.get(LOGIN_PAGE_URL);
	}
	public void typeUsername(String usernameBox){
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.elementToBeClickable(USERNAME_ID));
		
		WebElement typeUsernameBox = driver.findElement(USERNAME_ID);
		typeUsernameBox.sendKeys(USERNAME);
	}
	public void typePassword(String password) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.elementToBeClickable(PASSWORD_ID));
		
		WebElement typePassword = driver.findElement(PASSWORD_ID);
		typePassword.sendKeys(PASSWORD);
	}
	public int getItemsCount() {
		List<WebElement> itemList = driver.findElements(ITEM_CLASS);
		int itemCount = itemList.size();
		return itemCount;
	}
	public void clickLoginButton() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.elementToBeClickable(LOGIN_XPATH));
		
		WebElement loginButton = driver.findElement(LOGIN_XPATH);
		loginButton.click();
	}
	
	public String getItemName() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(ITEM_NAME_XPATH));
		WebElement itemNameElement = driver.findElement(ITEM_NAME_XPATH);
		String itemName = itemNameElement.getText();
		return itemName;
	}
	public double getItemPrice () {
		wait.until(ExpectedConditions.visibilityOfElementLocated(ITEM_PRICE_XPATH));
		WebElement itemPriceElement = driver.findElement(ITEM_PRICE_XPATH);
		String itemPriceText = itemPriceElement.getText();
		itemPriceText = itemPriceText.replace("$", "");
		double itemPrice = Double.parseDouble(itemPriceText);
		return itemPrice;
	}	
	
	public String getCartItemName() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(CART_ITEM_NAME_XPATH));
		WebElement cartItemNameElement = driver.findElement(CART_ITEM_NAME_XPATH);
		String cartItemName = cartItemNameElement.getText();
		return cartItemName;
	}
	public double getCartItemPrice() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(CART_ITEM_PRICE_XPATH));
		WebElement cartItemPriceElement = driver.findElement(CART_ITEM_PRICE_XPATH);
		String cartItemPriceText = cartItemPriceElement.getText();
		cartItemPriceText = cartItemPriceText.replace("$", "");
		double cartItemPrice = Double.parseDouble(cartItemPriceText);
		return cartItemPrice;
	}
	public void clickCheckoutButton() {
		wait.until(ExpectedConditions.elementToBeClickable(CART_CHECKOUT_ID));
		WebElement cartCheckoutButton = driver.findElement(CART_CHECKOUT_ID);
		cartCheckoutButton.click();
	}
	
	public void typeFirstName(String firstname) {
		wait.until(ExpectedConditions.elementToBeClickable(FIRST_NAME_ID));

		WebElement typeFirstName = driver.findElement(FIRST_NAME_ID);
		typeFirstName.sendKeys(firstname);
	}
	public void tyepLastName(String lastname) {
		wait.until(ExpectedConditions.elementToBeClickable(LAST_NAME_ID));
		WebElement tyepLastName = driver.findElement(LAST_NAME_ID);
		tyepLastName.sendKeys(lastname);
	}
	public void typeZipCode(String zip) {
		wait.until(ExpectedConditions.elementToBeClickable(ZIP_CODE_ID));
		WebElement typeZipCode = driver.findElement(ZIP_CODE_ID);
		typeZipCode.sendKeys(zip);
	}
	public void clickContinueButton() {
		wait.until(ExpectedConditions.elementToBeClickable(CONTINUE_ID));
		WebElement continueButton = driver.findElement(CONTINUE_ID);
		continueButton.click();
	}
	public String getOverviewItemName() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(OVERVIEW_ITEM_NAME_XPATH));
		WebElement overviewItemNameElement = driver.findElement(OVERVIEW_ITEM_NAME_XPATH);
		String overViewItemName = overviewItemNameElement.getText();
		return overViewItemName;
	}
	public double getOverviewItemPrice() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(OVERVIEW_ITEM_PRICE_XPATH));
		WebElement overviewItemPriceElement = driver.findElement(OVERVIEW_ITEM_PRICE_XPATH);
		String overviewItemPriceText = overviewItemPriceElement.getText();
		overviewItemPriceText = overviewItemPriceText.replace("$", "");
		double overviewItemPrice = Double.parseDouble(overviewItemPriceText);
		return overviewItemPrice;
	}
	public void clickFinishButton() {
		wait.until(ExpectedConditions.elementToBeClickable(FINISH_ID));
		WebElement finishButton = driver.findElement(FINISH_ID);
		finishButton.click();
	}
	public String getHeaderClass() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(HEADER_CLASS));
		WebElement headerLabel = driver.findElement(HEADER_CLASS);
		String headerText = headerLabel.getText();
		return headerText;
	}
	private String getUrl() {
		return driver.getCurrentUrl();
	}

	public void addToCartButton() {
		wait.until(ExpectedConditions.elementToBeClickable(ADD_TO_CART_XPATH));
		WebElement addToCartButton = driver.findElement(ADD_TO_CART_XPATH);
		addToCartButton.click();
	}

	public void addToCartButton2() {
		wait.until(ExpectedConditions.elementToBeClickable(ADD_TO_CART2_XPATH));
		WebElement cartCheckoutButton2 = driver.findElement(ADD_TO_CART2_XPATH);
		cartCheckoutButton2.click();
	}
	
	public void addToCartButton3() {
		wait.until(ExpectedConditions.elementToBeClickable(ADD_TO_CART3_XPATH));
		WebElement cartCheckoutButton3 = driver.findElement(ADD_TO_CART3_XPATH);
		cartCheckoutButton3.click();
	}

	/*
		this method should not include clicking the cartIcon
		
		clicking the cart icon should be in a separate method

		it is better to have 2 methods, one for getting the cart icon count, the other for clicking the cart icon,
		instead of one method doing both things

		i created a new method for this purpose, clickCartIcon()
	*/
	public int getCartIconCount() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(CART_ICON_CLASS));
		WebElement cartIcon = driver.findElement(CART_ICON_CLASS);
		String cartIconText = cartIcon.getText();
		int cartCount = Integer.parseInt(cartIconText);
		//cartIcon.click();
		return cartCount;
	}

	public void clickCartIcon() {
		wait.until(ExpectedConditions.elementToBeClickable(CART_ICON_CLASS));
		WebElement cartIcon = driver.findElement(CART_ICON_CLASS);
		cartIcon.click();
	}

	//this method is very similar to getCartIconCount(), you do not need it, use instead getCartIconCount()
	public int clickCartIcon3() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(CART_ICON_CLASS));
		WebElement cartIcon3 = driver.findElement(CART_ICON_CLASS);
		String cartIconText = cartIcon3.getText();
		int cartCount = Integer.parseInt(cartIconText);
		cartIcon3.click();
		return cartCount;
	}

	public void removeFromCartButton() {
		wait.until(ExpectedConditions.elementToBeClickable(REMOVE_FROM_CART1_XPATH));
		WebElement removeFromCartButton = driver.findElement(REMOVE_FROM_CART1_XPATH);
		removeFromCartButton.click();
	}
	public void removeFromCartButton2() {
		wait.until(ExpectedConditions.elementToBeClickable(REMOVE_FROM_CART2_XPATH));
		WebElement removeFromCartButton2 = driver.findElement(REMOVE_FROM_CART2_XPATH);
		removeFromCartButton2.click();
	}
	public void removeFromCartButton3() {
		wait.until(ExpectedConditions.elementToBeClickable(REMOVE_FROM_CART3_XPATH));
		WebElement removeFromCartButton3 = driver.findElement(REMOVE_FROM_CART3_XPATH);
		removeFromCartButton3.click();
	}
	public void clickContinueShoppingButton() {
		wait.until(ExpectedConditions.elementToBeClickable(CONTINUE_SHOPPING_ID));
		WebElement continueShoppingButton = driver.findElement(CONTINUE_SHOPPING_ID);
		continueShoppingButton.click();
	}



}
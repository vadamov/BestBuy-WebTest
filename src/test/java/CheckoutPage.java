import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class CheckoutPage {
    private WebDriver driver;
    public static final By SEARCH_FIELD = By.xpath("//input[@type='search']");
    public static final By PRODUCT_ITEM = By.xpath("//div[text()='Open Box - Apple iPhone 15 Pro 128GB - Blue Titanium - Unlocked']");
    public static final By PRODUCT_PRICE = By.xpath("//span[@data-automation='product-price']");
    public static final By ADD_TO_CART_BUTTON = By.xpath("//div[@class='addToCartLabel_YZaVX']");
    public static final By CART_BUTTON = By.xpath("//span[text()='Cart']");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }
    public void searchForProductAndAddToCart() {
        WebElement searchField = driver.findElement(SEARCH_FIELD);
        String productName="Iphone 15 pro";  //The product we will be searching for
        searchField.click(); // Click on the "Search" field
        searchField.sendKeys(productName); //Type the products name
        driver.findElement(PRODUCT_ITEM).click(); //click on the product
        WebElement price = driver.findElement(By.xpath("//span[@data-automation='product-price']")); //Get the products price
        driver.findElement(ADD_TO_CART_BUTTON).click(); //add to cart


    }

    public void navigateToCart() {
        driver.findElement(CART_BUTTON).click(); // navigate to the cart page

    }

    public void proceedToCheckout() {


    }

    public void fillShippingInformation(String firstName, String lastName, String email, String address, String city, String postalCode, String country) {


    }

    public void proceedToPayment() {

    }

    public void completePayment(String paymentMethod, String cardNumber, String expirationDate, String cvv) {


    }

    public static class ChekoutTests {
        WebDriver driver;
        CheckoutPage checkoutPage;

        @BeforeMethod
        public void setUp() {
            driver = BrowserActions.getWebDriver();
            driver.get(BrowserActions.getBaseUrl());
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            checkoutPage = new CheckoutPage(driver);
        }

        @Test
        public void testAddngItemToCart(){
            checkoutPage.searchForProductAndAddToCart(); //Add item to the cart
            checkoutPage.navigateToCart(); // Navigate to the cart
            //Verify that the product is added to the cart

        }

        @AfterMethod
        public void tearDown() {
            if (driver != null) {
                driver.quit();
            }
        }

    }
}


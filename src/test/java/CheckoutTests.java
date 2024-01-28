import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class CheckoutTests {
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
    public void testAddngItemToCart() {
        checkoutPage.searchForProductAndAddToCart(); //Add item to the cart
        checkoutPage.navigateToCart(); // Navigate to the cart
        //Verify that the product is added to the cart

    }

    public void testCheckoutProcess() {
        checkoutPage.searchForProductAndAddToCart();
        checkoutPage.navigateToCart();
        checkoutPage.proceedToCheckout();
        checkoutPage.fillShippingInformation("Ivan", "Ivanov", "ivanovivan@email.com", "Tutrakan", "Tutrakan", "993", "Bulgaria");
        checkoutPage.proceedToPayment();
        checkoutPage.completePayment("DebitCard", "1337733113377331", "13/12", "1337");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}

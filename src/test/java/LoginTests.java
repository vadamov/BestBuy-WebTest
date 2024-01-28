import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class LoginTests {
    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        driver = BrowserActions.getWebDriver();
        driver.get(BrowserActions.getBaseUrl());
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        loginPage = new LoginPage(driver);
    }

    @Test
    public void logInWithValidCredentials() {

        loginPage.navigateToLoginPage(); //navigate to the logIn page
        Assert.assertEquals(loginPage.getLoginPageTitle(), "Sign In", "Invalid message for login page"); // Assert that the page title matches the expected title
        loginPage.signIn("vladislavadamov@email.com", "MH9928Evma*"); //enter valid credentials
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[@data-automation='account-greeting']"))); // Assert that the page title matches the expected title

    }

    @Test
    public void testLoginWithValidEmailInvalidPassword() {

        loginPage.navigateToLoginPage(); //navigate to the logIn page
        WebElement pageTitle = driver.findElement(By.xpath("//h1"));
        Assert.assertEquals(pageTitle.getText(), "Sign In", "Invalid message for login page"); // Assert that the page title matches the expected title
        loginPage.signIn("vladislavadamov@email.com", "MH9928Evma"); //enter valid email and invalid password

        WebElement errorMessageForMismatch = driver.findElement(By.xpath("//p[text()='Sorry, something went wrong. Please try again.']"));
        //  Assert that the error message matches the expected
        Assert.assertEquals(errorMessageForMismatch.getText(), "Sorry, the e-mail address and password you entered don’t match. Please try again.", "Incorrect error message for mismatch between the email and password");
    }

    @Test
    public void testLoginWithInvalidEmailValidPassword() {

        loginPage.navigateToLoginPage(); //navigate to the logIn page
        WebElement pageTitle = driver.findElement(By.xpath("//h1"));
        Assert.assertEquals(pageTitle.getText(), "Sign In", "Invalid message for login page"); // Assert that the page title matches the expected title
        loginPage.signIn("vladislavadamov@emailcom", "MH9928Evma*"); //enter invalid email and valid password
        WebElement invalidEmailMessage = driver.findElement(By.xpath("//div[text()='Please enter a valid email address.']"));
        // Assert that the error message matches the expected
        Assert.assertEquals(invalidEmailMessage.getText(), "Please enter a valid email address.", "Incorrect message for invalid email");

    }

    @Test
    public void testSQLInjection() {
        loginPage.navigateToLoginPage(); //navigate to the login page
        // Assert that the page title matches the expected title
        Assert.assertEquals(loginPage.getLoginPageTitle(), "Sign In", "Invalid message for login page");

        loginPage.signInWithSQLInjection("'; DROP TABLE Users; --", "12345678*");
        // Assert that the login fails due to invalid email
        WebElement invalidEmailMessage = driver.findElement(By.xpath("//div[text()='Please enter a valid email address.']"));
        Assert.assertEquals(invalidEmailMessage.getText(), "Please enter a valid email address.", "Incorrect message for invalid email");
    }

    @Test
    public void testAccountLockoutAfterMultipleFailedAttempts() {

        loginPage.navigateToLoginPage();
        // Enter incorrect login credentials 16 times
        for (int i = 0; i <= 15; i++) {
            loginPage.signIn("invalidUser@abv.bg", "invalidPassword");
            loginPage.clearLoginFields(); //clear email and password field after every loop
            new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[text()='Sorry, something went wrong. Please try again.']")));
        }
        // Verify that the account is locked
        WebElement errorMessage = driver.findElement(By.xpath("//p[text()='Sorry, something went wrong. Please try again.']"));
        Assert.assertEquals(errorMessage.getText(), "Sorry, something went wrong. Please try again.");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
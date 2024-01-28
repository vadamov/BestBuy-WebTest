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

public class RegistrationTests {

    private WebDriver driver;
    private RegistrationPage registrationPage;

    @BeforeMethod
    public void setUp() {
        driver = BrowserActions.getWebDriver();
        driver.get(BrowserActions.getBaseUrl());
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        registrationPage = new RegistrationPage(driver);
    }

    @Test
    public void testValidSignUp() {
        registrationPage.navigateToRegistrationPage(); //navigate to the registration page
        Assert.assertEquals(driver.getTitle(), "Best Buy", "Wrong title on the home page"); // Assert that the page title matches the expected title

        //fill the required fields with valid data
        registrationPage.signUp("Vladislav", "Adamov", "vladislavadamovv@email.com", "MH9928Evma*"); // Your password must be at least 8 characters long, contain a letter, contain a number, contain a symbol.
        // Wait for the greeting message to become visible
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[@data-automation='account-greeting']")));
    }

    @Test
    public void testInvalidSignUpEmptyFields() {

        registrationPage.navigateToRegistrationPage(); // navigate to the registration page
        Assert.assertEquals(driver.getTitle(), "Best Buy", "Wrong title on the home page"); // Assert that the page title matches the expected title
        registrationPage.signUp(" ", "  ", "  ", ""); //fill the required fields with only spaces
        // Assert that error messages are shown for empty fields
        WebElement emptyUsernameText = driver.findElement(By.xpath("//div[text()='Please enter a valid first name.']"));
        Assert.assertEquals(emptyUsernameText.getText(), "Please enter a valid first name.", "Incorrect error message for empty first name field");

        WebElement emptyPassText = driver.findElement(By.xpath("//div[text()='Please enter a valid last name.']"));
        Assert.assertEquals(emptyPassText.getText(), "Please enter a valid last name.", "Incorrect error message for empty last name field");

        WebElement emptyEmailField = driver.findElement(By.xpath("//div[text()='This is not a valid email address.']"));
        Assert.assertEquals(emptyEmailField.getText(), "This is not a valid email address.", "Incorrect error message for empty email address field");

        WebElement emptyPassField = driver.findElement(By.xpath("//div[text()='Please review the password requirements.']"));
        Assert.assertEquals(emptyPassField.getText(), "Please review the password requirements.", "Incorrect error message for empty password field");
    }

    @Test
    public void testRegistrationWithExistingEmail() {

        registrationPage.navigateToRegistrationPage(); //navigate to the registration page
        WebElement createAccountText = driver.findElement(By.xpath("//h1")); // Assert that the page title matches the expected title
        Assert.assertEquals(createAccountText.getText(), "Create an Account");

        // Fill the required fields with already existing email
        registrationPage.signUp("Vladislav", "Adamov", "vladislavadamovv@email.com", "e8922mh*12");
        WebElement existingEmailText = driver.findElement(By.xpath("//div[@class='mWZ7T']"));
        Assert.assertEquals(existingEmailText.getText(), "An account with this email address already exists. Please use it to sign in or enter a new email to continue.", "Incorrect error message for already existing email");
    }

    @Test
    public void testInvalidRegistration_InvalidEmail() {

        registrationPage.navigateToRegistrationPage(); //navigate to the registration page
        WebElement createAccountText = driver.findElement(By.xpath("//h1")); // Assert that the page title matches the expected title
        Assert.assertEquals(createAccountText.getText(), "Create an Account");

        // Fill the required fields with invalid email
        registrationPage.signUp("Vladislav", "Adamov", "vladislavadamov@yahoo,com", "e8922mh*12");

        WebElement invalidEmail = driver.findElement(By.xpath("//div[text()='This is not a valid email address.']"));
        // Assert that the correct error message is shown for an invalid email address
        Assert.assertEquals(invalidEmail.getText(), "This is not a valid email address.", "Incorrect error message for empty email address field");

    }


    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

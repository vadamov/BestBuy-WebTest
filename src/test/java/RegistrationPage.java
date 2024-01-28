import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class RegistrationPage {
    private WebDriver driver;

    private By signInLink = By.xpath("//a[@data-automation='sign-in-link']");
    private By createAccountButton = By.xpath("//span[text()='Create an account']");
    private By firstNameField = By.id("firstName");
    private By lastNameField = By.id("lastName");
    private By emailField = By.id("email");
    private By passwordField = By.id("password");
    private By newsletterCheckbox = By.id("newsletter");
    private By submitButton = By.xpath("//button");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateToRegistrationPage() { //Click on "Account" link to navigate to registration page
        driver.findElement(signInLink).click();

    }

    public void signUp(String firstName, String lastName, String email, String password) {
        driver.findElement(createAccountButton).click();
        driver.findElement(firstNameField).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);

        WebElement newsletterCheckBox = driver.findElement(newsletterCheckbox);
        if (newsletterCheckBox.isSelected()) {
            newsletterCheckBox.click();
        }

        driver.findElement(submitButton).click();
    }

    public String getRegistrationPageTitle() { //Getting the registration page title
        return driver.findElement(By.xpath("//h1")).getText();
    }

    public String getSuccessfulRegistrationPageTitle() {
        return driver.findElement(By.xpath("//h2[@data-automation='account-greeting']")).getText();
    }

    private void waitForPageLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }
}
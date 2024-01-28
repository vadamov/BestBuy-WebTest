import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private WebDriver driver;

    private By accountLink = By.xpath("//span[text()='Account']");
    private By usernameField = By.id("username");
    private By passwordField = By.id("password");
    private By loginButton = By.xpath("//button");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }
    public void navigateToLoginPage(){ //Click on the "Account" link to navigate to the login page
        driver.findElement(By.xpath("//span[text()='Account']")).click();
    }
    public void signIn(String email, String password) { // Enters email and password on the login fields
        driver.findElement(usernameField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();
    }

    public String getLoginPageTitle() { // gets the title of the page
        return driver.findElement(By.xpath("//h1")).getText();
    }
    public void signInWithSQLInjection(String email, String password) {
        String sqlInjectionPayload = "' OR '1'='1' -- ";
        driver.findElement(usernameField).sendKeys(email + sqlInjectionPayload);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();
    }
    public void clearLoginFields() { //clears password and email field
        driver.findElement(usernameField).clear();
        driver.findElement(passwordField).clear();
//        driver.findElement(By.id("username")).clear();
//        driver.findElement(By.id("password")).clear();
    }
}

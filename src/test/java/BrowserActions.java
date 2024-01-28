import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BrowserActions {
    private static final String BASE_URL = "https://www.bestbuy.ca/en-ca";

    public static WebDriver getWebDriver() {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }
}
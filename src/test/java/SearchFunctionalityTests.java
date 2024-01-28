import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class SearchFunctionalityTests {
    private WebDriver driver;
    private SearchField searchField;

    @BeforeMethod
    public void setUp() {
        driver = BrowserActions.getWebDriver();
        driver.get(BrowserActions.getBaseUrl());
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        searchField = new SearchField(driver);
    }


    @Test
    public void searchForValidItem() {

        searchField.navigateToSearchField(); // Click on the "Search" field
        Assert.assertEquals(driver.getTitle(), "Best Buy: Shop Online For Deals & Save | Best Buy Canada", "Wrong title on the home page");// Assert that the page title matches the expected title

        String productNameToSearch = "Laptop"; // Name of the product which will be searched
        searchField.searchForItem(productNameToSearch); //Enter the product name in the search field
        searchField.submitSearch();                     //search for the product
        WebElement result = driver.findElement(By.xpath("//span[@property='item']"));
        String resultText = result.getText();
        //Assert that the product name is present on the results page
        Assert.assertTrue(resultText.contains(productNameToSearch), "The result text does not contain the expected word: " + productNameToSearch);

    }

    @Test
    public void searchForInvalidItem() {
        searchField.navigateToSearchField(); //click on the "Search" field
        Assert.assertEquals(driver.getTitle(), "Best Buy: Shop Online For Deals & Save | Best Buy Canada", "Wrong title on the home page");// Assert that the page title matches the expected title

        String productNameToSearch = "hsadhashdsahdhsadhashdashdhashdashdas"; // Enter random string in order to provoke invalid search
        searchField.searchForItem(productNameToSearch); //Enter the invalid product name in the search field
        searchField.submitSearch();                     //Press the Ssearch" button
        WebElement searchResultText = driver.findElement(By.xpath("//h1"));
        System.out.println("Actual Text: " + searchResultText.getText());
        //Assert that the product name is present on the results page
        Assert.assertTrue(searchResultText.getText().toLowerCase().contains("results for: " + productNameToSearch.toLowerCase()), "Incorrect title for search results");
    }


    @Test
    public void testPriceSortingFunctionality() {
        searchField.navigateToSearchField();//click on the "Search" field
        Assert.assertEquals(driver.getTitle(), "Best Buy: Shop Online For Deals & Save | Best Buy Canada", "Wrong title on the home page"); //Assert that the page title matches the expected title

        String productNameToSearch = "Iphone 15 pro"; // Name of the product which will be searched
        searchField.searchForItem(productNameToSearch); //Enter the product name in the search field
        searchField.submitSearch();                     //search for the product
        WebElement searchResultText = driver.findElement(By.xpath("//h1"));
        Assert.assertTrue(driver.getTitle().contains("Results for: " + productNameToSearch), "Incorrect title for search results"); //Assert that the product name is present on the results page
        searchField.selectPriceLowToHigh(); // sort the products by price
        Assert.assertTrue(searchField.areProductsSortedByLowToHigh(), "Products are not sorted by low to high price"); //Assert that the products are sorted by price (low to high)
    }

    @Test
    public void searchForProductWithSpecialCharacters() {
        searchField.navigateToSearchField(); //Click on the "Search" field
        String productNameWithSpecialChars = "GEMS+ XL Fitness Band for Iphone 6 - 4.7\""; // Enter a product name with special characters
        searchField.searchForItem(productNameWithSpecialChars); // Type the product name in the search field
        searchField.submitSearch(); //Press "Search" button

        WebElement searchResultText = driver.findElement(By.xpath("//h1"));    // Verify that the search results are accurate
        Assert.assertTrue(driver.getTitle().contains("Results for: " + productNameWithSpecialChars),
                "Incorrect title for search results");

        // Verify that the product with special characters is present in the search results
        WebElement productWithSpecialChars = driver.findElement(By.xpath("//div[contains(text(),'" + productNameWithSpecialChars + "')]"));
        Assert.assertTrue(productWithSpecialChars.isDisplayed(), "Product with special characters is not displayed in search results");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

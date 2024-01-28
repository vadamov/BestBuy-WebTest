import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchField {
    private By searchBox = By.xpath("//input[@type='search']");
    private By searchButton = By.xpath("//button[@data-automation='x-search-submit']");
    private By sortDropdown = By.id("Sort");
    private By priceElements = By.xpath("//span[@class='price']");
    private WebDriver driver;
    public SearchField(WebDriver driver){
        this.driver=driver;
    }
    public void navigateToSearchField(){ //Click on the search field
        driver.findElement(searchBox).click();
    }
    public void searchForItem(String item){ //Search for item in the search field
        WebElement searchBoxElement = driver.findElement(searchBox);
        searchBoxElement.sendKeys(item);


    }
    public void submitSearch(){ //Press the search button
        driver.findElement(searchButton).click();
    }
    public void selectPriceLowToHigh(){ //sort the results in descending order (BY PRICE)
        WebElement sort = driver.findElement(sortDropdown);
        Select select = new Select(sort);
        select.selectByVisibleText("Price Low-High");
    }
    public boolean areProductsSortedByLowToHigh() {
        // Extract prices of the products and check if they are in ascending order
        List<WebElement> priceElementsList = driver.findElements(priceElements);
        List<Double> prices = new ArrayList<>();

        for (WebElement priceElement : priceElementsList) {
            String priceText = priceElement.getText().replaceAll("[^\\d.]", "");
            prices.add(Double.parseDouble(priceText));
        }

        List<Double> sortedPrices = new ArrayList<>(prices);
        Collections.sort(sortedPrices);
        return prices.equals(sortedPrices);
    }
}


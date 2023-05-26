package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MakeMyTripeFlightsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public MakeMyTripeFlightsPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    private By fromCity = By.id("fromCity");

    private By toCity = By.id("toCity");

    private By selectCityFromList(String cityName) {
        return By.xpath("//ul[@role='listbox']/li/div/div/p[contains(text(),'" + cityName + "')]");
    }

    private By departure = By.id("departure");
    private By departureDate = By.xpath("//div[@class='DayPicker-Month'][2]//div[contains(@aria-label,'01')]/div");
    private By travellers = By.xpath("//label[@for='travellers']");

    private By adults(int numberOfAdults) {
        return By.xpath("(//p[contains(text(),'ADULTS')]/following-sibling::ul/li)[" + numberOfAdults + "]");
    }

    private By children(int numberOfChildren) {
        return By.xpath("(//p[contains(text(),'CHILDREN')]/following-sibling::ul/li)[" + numberOfChildren + "]");
    }

    private By infants(int numberOfInfants) {
        return By.xpath("(//p[contains(text(),'INFANTS')]/following-sibling::ul/li)[" + numberOfInfants + "]");
    }

    private By buttonApply = By.xpath("//button[contains(text(),'APPLY')]");
    private By buttonSearch = By.xpath("//a[contains(text(),'Search')]");
    private By buttonGotIt = By.xpath("//button[contains(text(),'OKAY, GOT IT!')]");
    private By tripType = By.xpath("//label[@for='tripType']/following-sibling::div/div");
    private By fromCityValue = By.xpath("//input[@id='fromCity']");
    private By toCityValue = By.xpath("//input[@id='toCity']");
    private By departureDateValue = By.xpath("//input[@id='departure']");
    private By travellerAndClass = By.xpath("//input[@id='travellerAndClass']");
    private By selectPriceRange = By.xpath("//div[@class='rangeslider__handle']");
    private By maxOneWayPrice = By.xpath("//div[@aria-valuenow]");
    private By priceMaxValue = By.xpath("//div[@aria-valuenow]");

    private By stopsCheckbox(String stopType) {
        return By.xpath("//div/p[contains(text(),'Stops')]/following-sibling::div/div/label/span/following-sibling::div/p[contains(text(),'" + stopType + "')]");
    }

    private By resultList = By.xpath("//div[@id='premEcon']/div/div[contains (@id, 'flight_list_item')]");
    private By listPrice = By.xpath("//div[@id='premEcon']/div/div[contains (@id, 'flight_list_item')]/div//div[@class='priceSection']//p");

    public void selectFromCity(String city) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(fromCity));
        driver.findElement(fromCity).sendKeys(city);
    }

    public void selectCityList(String city) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(selectCityFromList(city)));
        driver.findElement(selectCityFromList(city)).click();

    }

    public void selectToCity(String city) {
        driver.findElement(toCity).sendKeys(city);
    }

    public void selectDeparture() {
        driver.findElement(departure).click();
        //wait.until(ExpectedConditions.visibilityOfElementLocated(departureDate));
    }

    public void selectDepartureDate() {
        driver.findElement(departureDate).click();
    }

    public void selectTravellers() {
        wait.until(ExpectedConditions.elementToBeClickable(travellers));
        driver.findElement(travellers).click();
    }

    public void selectAdults(int count) {
        driver.findElement(adults(count)).click();
    }

    public void selectChildren(int count) {
        driver.findElement(children(count)).click();
    }

    public void selectInfants(int count) {
        driver.findElement(infants(count)).click();
    }

    public void clickApply() {
        driver.findElement(buttonApply).click();
    }

    public void clickSearch() {
        driver.findElement(buttonSearch).click();
        wait.until(ExpectedConditions.elementToBeClickable(buttonGotIt));
        if (driver.findElements(buttonGotIt).size() != 0) {
            driver.findElement(buttonGotIt).click();
        }
    }

    public String getTripType() {
        return driver.findElement(tripType).getText();
    }

    public String getFromCity() {
        return driver.findElement(fromCityValue).getAttribute("value");
    }

    public String getToCity() {
        return driver.findElement(toCityValue).getAttribute("value");
    }

    public String getDeparture() {
        return driver.findElement(departureDateValue).getAttribute("value");
    }

    public String getPassengerClass() {
        return driver.findElement(travellerAndClass).getAttribute("value");
    }

    public int getMaxOneWayPrice() {
        return Integer.parseInt(driver.findElement(maxOneWayPrice).getAttribute("ariaValueNow"));
    }

    public void setOneWayPrice(int expectedPrice) {
        Actions actions = new Actions(driver);
        WebElement priceSlider = driver.findElement(selectPriceRange);
        System.out.println(getMaxOneWayPrice());

        Actions builder = new Actions(driver);
        do {
            builder.moveToElement(priceSlider)
                    .click()
                    .keyDown(Keys.ARROW_LEFT)
                    .build()
                    .perform();
        } while (getMaxOneWayPrice() != expectedPrice);
        wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(resultList)));
    }

    public boolean getListPrice(int value) {
        List<WebElement> searchList = driver.findElements(listPrice);
        boolean temp = false;
        for(int i=0; i < searchList.size();i++)
        {
             int price = Integer.parseInt(searchList.get(i).getText().replace("₹", "").replace(",","").trim());
            if (price <= value) {
                temp= true;
            } else {
                temp= false;
            }
        }
        return temp;
    }

    public void selectStops(String value){
        driver.findElement(stopsCheckbox(value)).click();
        wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(resultList)));
    }

    public int getStopWiseFlightCount(String value){
        return Integer.parseInt(driver.findElement(stopsCheckbox(value)).getText().replace(value,"").replace("(","").replace(")","").trim());
    }
    public int getFlightListCount(){
        return driver.findElements(resultList).size();
    }

}

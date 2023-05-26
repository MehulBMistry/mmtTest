package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    private WebDriver driver;
    private WebDriverWait wait;

    public HomePage(WebDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
    }
    public MakeMyTripeFlightsPage openMMTSite(){
        driver.get("https://www.makemytrip.com/flights/");
        return new MakeMyTripeFlightsPage(driver, wait);
    }

}
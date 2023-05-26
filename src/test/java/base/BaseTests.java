package base;

import com.google.common.io.Files;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.HomePage;
import utils.CookieManager;
import utils.ExcelUtil;
import utils.WindowManager;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class BaseTests {

    //private EventFiringWebDriver driver;
    private WebDriver driver;
    private WebDriverWait wait;
    protected HomePage homePage;

    @BeforeClass
    @Parameters({"BrowserType"})
    public void setUp(String browserType) {
        if (browserType.equalsIgnoreCase("Chrome")) {

            System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origin=*");
            //driver = new EventFiringWebDriver(new ChromeDriver(getChromeOptions()));
            //driver.register(new EventReporter());
            driver = new ChromeDriver(options);

            /*
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            */
        }
        else if(browserType.equalsIgnoreCase("Firefox")){
            // WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @BeforeMethod
    public void goHome() {
        driver.manage().window().maximize();
        homePage = new HomePage(driver, wait);
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    @AfterMethod
    public void recordFailure(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            var camera = (TakesScreenshot) driver;
            File screenshot = camera.getScreenshotAs(OutputType.FILE);
            try {
                Files.move(screenshot, new File("resources/screenshots/" + result.getName() + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public WindowManager getWindowManager() {
        return new WindowManager(driver);
    }

    public ExcelUtil getExcelUtil() {
        return new ExcelUtil();
    }

    private ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-infobars");
        //options.setHeadless(true);
        return options;
    }

    public CookieManager getCookieManager() {
        return new CookieManager(driver);
    }
}

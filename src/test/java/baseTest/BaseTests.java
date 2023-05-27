package baseTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;
import pages.CheckoutCompletionPage;
import pages.HomePage;
import pages.InventoryPage;

import java.time.Duration;

public class BaseTests {
    protected HomePage homePage;
    protected InventoryPage inventoryPage;
    protected CheckoutCompletionPage checkoutCompletionPage;
    public WebDriver driver;

    protected static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();


    @Parameters({"browser"})
    @BeforeMethod
    public void setup(@Optional String browser) {

        if ("chrome".equalsIgnoreCase(browser)) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            threadLocalDriver.set(driver);
        } else if ("firefox".equalsIgnoreCase(browser)) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
            threadLocalDriver.set(driver);
        } else if ("edge".equalsIgnoreCase(browser)) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
            threadLocalDriver.set(driver);
        } else {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            threadLocalDriver.set(driver);
        }
        homePage = new HomePage(getDriver());
        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
        getDriver().get("https://www.saucedemo.com/");
        inventoryPage = homePage.enterUserName(homePage.getUserNameFromCredentials())
                .enterPassword(homePage.getPassword())
                .clickLoginBtn();
        checkoutCompletionPage = new CheckoutCompletionPage(getDriver());

    }




    //get thread-safe driver
    public static WebDriver getDriver(){
        return threadLocalDriver.get();
    }


    @AfterMethod
    public void tearDown(){
        getDriver().quit();
        threadLocalDriver.remove();
    }
}


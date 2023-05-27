package baseTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import pages.CheckoutCompletionPage;
import pages.HomePage;
import pages.InventoryPage;

import java.time.Duration;

public class BaseTests {
    protected HomePage homePage;
    protected InventoryPage inventoryPage;
    protected CheckoutCompletionPage checkoutCompletionPage;
    protected WebDriver driver;

    @Parameters({"browser"})
    @BeforeMethod
    public void setup(@Optional String browser) {

        if ("chrome".equalsIgnoreCase(browser)) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if ("firefox".equalsIgnoreCase(browser)) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if ("edge".equalsIgnoreCase(browser)) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        } else {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }
        homePage = new HomePage(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
        driver.get("https://www.saucedemo.com/");
        inventoryPage = homePage.enterUserName(homePage.getUserNameFromCredentials())
                .enterPassword(homePage.getPassword())
                .clickLoginBtn();
        checkoutCompletionPage = new CheckoutCompletionPage(driver);

    }


    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}


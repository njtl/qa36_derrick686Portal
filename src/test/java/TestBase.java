import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.Browser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;


public class TestBase {
    final static Logger logger = LoggerFactory.getLogger(TestBase.class);


    WebDriver wd;
    String browser;

    @BeforeSuite(alwaysRun=true)
    public void Prepare(){
        logger.info("Running a test: prepare in BeforeSuite, initializing WebDriver, maximizing window and opening login page ");
        String path;

        browser = System.getProperty("browser");
        logger.info("Running test system property browser set to " + browser);

        if (browser.equals(Browser.CHROME.browserName())) {
            path = System.getenv("chromeDriver");
            System.setProperty("webdriver.chrome.driver", path);
            wd = new ChromeDriver();
        } else if (browser.equals(Browser.FIREFOX.browserName())) {
            path = System.getenv("firefoxDriver");
            System.setProperty("webdriver.gecko.driver", path);
            wd = new FirefoxDriver();
        }  else {
            logger.error("No supported browser specified. Supported browsers: chrome, firefox,edge, opera");
        }

        wd.get("https://derrick686.softr.app/login");
        wd.manage().window().maximize();
    }


    public void badAuth() throws InterruptedException {
        //Enter wrong email
        WebElement username = wd.findElement(By.id("sw-form-capture-email-input"));
        username.click();
        username.clear();
        username.sendKeys("wrong email");

        //Enter wrong password
        WebElement password = wd.findElement(By.id("sw-form-password-input"));
        password.click();
        password.clear();
        password.sendKeys("password");

        //Submit the form
        WebElement button = wd.findElement(By.id("sw-sign-in-submit-btn"));
        button.click();
        Thread.sleep(2000);

    }


    public void managerAuth() throws InterruptedException {
        auth("billye@example.com", "123456");
    }

    public void clientAuth() throws InterruptedException {
        auth("lucie@example.com", "123456");
    }



    public void consultantAuth() throws InterruptedException {
        auth("edra@example.com", "123456");
    }

    public void auth(String usernameS, String passwordS) throws InterruptedException {
        //Enter manager's email
        WebElement username = wd.findElement(By.id("sw-form-capture-email-input"));
        username.click();
        username.clear();
        username.sendKeys(usernameS);

        //Enter manager's password
        WebElement password = wd.findElement(By.id("sw-form-password-input"));
        password.click();
        password.clear();
        password.sendKeys(passwordS);

        //Submit the form
        WebElement button = wd.findElement(By.id("sw-sign-in-submit-btn"));
        button.click();

        Thread.sleep(2000);
    }

    public void checkClientsLink(){
        wd.findElement(By.xpath("//*[@id=\"home-header1\"]/div/div[1]/ul/li[2]/a"));
    }

    public Boolean searchInPageSource(String text){
        return wd.getPageSource().contains(text);
    }


    public void logout() {
        wd.findElement(By.id("navbarDropdown")).click();
        //Find element by class name via cssSelector
        //Attention to that spaces in class name are replaced by dots for css selector
        wd.findElement(By.cssSelector(".d-item.d-flex.justify-content-start.align-items-center.navigate")).click();

        //Second option: locate a link by its text
        //wd.findElement(By.partialLinkText("Sign Out")).click();
    }

    public void openClientsPage()
    {
        wd.get("https://derrick686.softr.app/clients");
    }

    public void searchClientsBy(String stringS) throws InterruptedException {
        WebElement input = wd.findElement(By.xpath("//*[@id=\"list2\"]/div[1]/div/div/div/input"));
        input.click();
        input.clear();
        input.sendKeys(stringS);
        input.sendKeys(Keys.ENTER);
        Thread.sleep(2000);
    }

    public void openLoginPage(){
        wd.get("https://derrick686.softr.app/login");
    }
    @AfterSuite(alwaysRun=true)
    public void exit(){
        wd.quit();
        logger.info("Ending test in AftersSuite and quiting browser");
    }
}

import com.google.common.io.Files;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.Browser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.awt.*;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import org.monte.screenrecorder.ScreenRecorder;
import org.monte.media.Format;
import org.monte.media.FormatKeys;
import org.monte.media.math.Rational;
import static org.monte.media.FormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;


import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;



public class TestBase {
    final static Logger logger = LoggerFactory.getLogger(TestBase.class);

    private ScreenRecorder sr;

    WebDriver wd;
    String browser;

    String recordsFolder= "src/test/resources/records";

    @BeforeSuite(alwaysRun=true)
    public void Prepare(){
        deleteAllRecordings();

        logger.info("Running a test: prepare in BeforeSuite, initializing WebDriver, maximizing window and opening login page ");
        String path;

        /*
        browser = System.getProperty("browser"); //-Dbrowser=chrome
        logger.info("Running test system property browser set to " + browser);

        if (browser.equals(Browser.CHROME.browserName())) {
            path = System.getenv("chromeDriver"); // chromeDriver=/Users/ilya/telran/Tools/chromedriver
            System.setProperty("webdriver.chrome.driver", path);
            wd = new ChromeDriver();
        } else if (browser.equals(Browser.FIREFOX.browserName())) {
            path = System.getenv("firefoxDriver"); // firefoxDriver=/Users/ilya/telran/Tools/geckodriver
            System.setProperty("webdriver.gecko.driver", path);
            wd = new FirefoxDriver();
        }  else if (browser.equals(Browser.OPERA.browserName())) {
            path = System.getenv("operaDriver");
            System.setProperty("webdriver.chrome.driver", path);
            //wd = new OperaDriver();
        } else if (browser.equals(Browser.EDGE.browserName())) {
            path = System.getenv("edgeDriver");
            System.setProperty("webdriver.edge.driver", path);
            wd = new EdgeDriver();
        } else {
            logger.error("No supported browser specified. Supported browsers: chrome, firefox,edge, opera");
        }
        */

        System.setProperty("webdriver.chrome.driver", "/Users/ilya/telran/Tools/chromedriver");
        wd = new ChromeDriver();

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

    public String takeScreenshot() {
        File tmp = ((TakesScreenshot)wd).getScreenshotAs(OutputType.FILE);
        File screenshot = new File("src/test/resources/screenshots/screen"+System.currentTimeMillis()+".png");
        try {
            screenshot.createNewFile();
            Files.copy(tmp,screenshot);
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
            return "Failed to create a screenshot";
        }
        return screenshot.getAbsolutePath();
    }


    public void startRecording() {
        File file = new File(recordsFolder);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle captureSize = new Rectangle(0,0, dimension.width, dimension.height);
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        logger.info("Starting screen recording");
        try {
            sr = new Recorder(gc, captureSize,
                    new Format(MediaTypeKey, FormatKeys.MediaType.FILE, MimeTypeKey, MIME_AVI),
                    new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_MJPG, CompressorNameKey, ENCODING_AVI_MJPG,
                            DepthKey, 24, FrameRateKey, Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60),
                    new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)),
                    null, file, "MyVideo");
            sr.start();
        } catch (IOException | AWTException e1){
            logger.error(e1.getMessage());
        }
    }

    public void stopRecoding() {
        logger.info("Stopping screen recording");
        try {
            sr.stop();
        } catch (IOException e1){
            logger.error(e1.getMessage());
        }
    }

    public void deleteAllRecordings(){
        File dir = new File(recordsFolder);
        for (File f: dir.listFiles()){
            try
            {
                f.delete();
            }
            catch (Exception e)
            {
                logger.error("Error while cleaning Records folder " + e.getMessage());
            }
        }
        logger.info("Cleaned Records folder");
    }

    public void openLoginPage(){
        wd.get("https://derrick686.softr.app/login");
    }
    @AfterSuite(alwaysRun=true)
    public void exit(ITestResult result){
        wd.quit();
        logger.info("Ending test in AftersSuite and quiting browser");
    }
}

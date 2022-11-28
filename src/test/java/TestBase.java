import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class TestBase {
    WebDriver wd;

    @BeforeTest
    public void Prepare(){
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

        //Enter manager's email
        WebElement username = wd.findElement(By.id("sw-form-capture-email-input"));
        username.click();
        username.clear();
        username.sendKeys("billye@example.com");

        //Enter manager's password
        WebElement password = wd.findElement(By.id("sw-form-password-input"));
        password.click();
        password.clear();
        password.sendKeys("123456");

        //Submit the form
        WebElement button = wd.findElement(By.id("sw-sign-in-submit-btn"));
        button.click();

        Thread.sleep(2000);

    }

    public void checkClientsLink(){
        WebElement clients = wd.findElement(By.xpath("//*[@id=\"home-header1\"]/div/div[1]/ul/li[2]/a"));
    }


    @AfterTest
    public void exit(){
        wd.quit();
    }
}

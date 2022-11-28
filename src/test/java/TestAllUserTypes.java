import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestAllUserTypes extends TestBase {
    @BeforeMethod
    public void beforeMOpenLoginPage(){
        System.out.println("Running before method: opening login page");

        //Precondition for each page: user is not logged in, login page is opened in browser
        openLoginPage();
    }

    @Test
    public void LoginAsManager() throws InterruptedException {
        System.out.println("Running test LoginAsManager");

        //Authenticate as a manager
        managerAuth();
        //Verify that links to portal sections are presented on the page available to manager
        wd.findElement(By.partialLinkText("PROJECT OVERVIEW"));
        wd.findElement(By.partialLinkText("CLIENTS"));
        wd.findElement(By.partialLinkText("TEAM"));
        wd.findElement(By.partialLinkText("INVOICES"));
    }

    @Test
    public void LoginAsClient() throws InterruptedException {
        System.out.println("Running test LoginAsClient");

        //Authenticate as a client
        clientAuth();

        //Verify that links to portal sections are presented on the page available to client
        wd.findElement(By.partialLinkText("PROJECTS OVERVIEW"));
        wd.findElement(By.partialLinkText("INVOICES"));

        //Verify there is no more links from manager/consultant: **first way**
        Assert.assertFalse(searchInPageSource("CLIENTS"));
        Assert.assertFalse(searchInPageSource("TEAM"));

        //Verify there is no more links from manager/consultant: **second way**
        //Attention to that findElements is used instead findElement to get an empty array (expected)
        Assert.assertTrue(wd.findElements(By.partialLinkText("CLIENTS")).isEmpty());
        Assert.assertTrue(wd.findElements(By.partialLinkText("TEAM")).isEmpty());
    }

    @Test
    public void LoginAsConsultant() throws InterruptedException {
        System.out.println("Running test LoginAsConsultant");

        //Authenticate as a consultant
        consultantAuth();

        //Verify that links to portal sections are presented on the page available to consultant
        wd.findElement(By.partialLinkText("PROJECT OVERVIEW"));
        wd.findElement(By.partialLinkText("CLIENTS"));
        wd.findElement(By.partialLinkText("TEAM"));
        wd.findElement(By.partialLinkText("INVOICES"));
    }

    @AfterMethod
    public void afterMLogout(){
        logout();
        System.out.println("Running after method: logging current user out");
    }
}

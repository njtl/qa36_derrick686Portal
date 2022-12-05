import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;


public class TestAllUserTypes extends TestBase {
    @BeforeMethod
    public void beforeMOpenLoginPage(Method m, Object[] p){
        logger.info("Starting method: " + m.getName()+" with data: "+ Arrays.asList(p));

        //Precondition for each test: user is not logged in, login page is opened in browser
        openLoginPage();
    }

    @Test
    public void LoginAsManager() throws InterruptedException {
        System.out.println("Running test LoginAsManager");
        logger.info("Starting method login");

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

    @Test(dataProvider = "positiveCredsFromCSV", dataProviderClass=DataProviders.class)
    public void goodAuthTestWithDataProviderCSV(String email, String pwd, String elementsTrue, String elementsFalse) throws InterruptedException {
        auth(email, pwd);

        //Check if splitted by ; strings from elementsTrue are presented on the page as links
        String[] presented = elementsTrue.split(";");
        for (String verification : presented) {
            wd.findElement(By.partialLinkText(verification));
        }

        //Check if splitted by ; strings from elementsFalse are not presented on the page as links (excluding space characters from verifications)
        String[] notpresented = elementsFalse.split(";");
        for (String verification : notpresented) {
            if (!Objects.equals(verification, ""))
            {
                Assert.assertEquals(wd.findElements(By.partialLinkText(verification)).size(), 0);
            }
        }
    }

    @Test(dataProvider = "getWrongCredsFromCSV", dataProviderClass = DataProviders.class)
    public void BadAuthTestWithDataProvider(String email, String pwd) throws InterruptedException {
        auth(email, pwd);
        String text = "Invalid email or password";
        Assert.assertEquals(wd.getPageSource().contains(text),Boolean.TRUE);
    }

    @AfterMethod
    public void afterMLogout(){
        logout();
        System.out.println("Running after method: logging current user out");
    }
}

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestSetVerifyLogin extends TestBase {



    @Test
    public void BadAuthTest() throws InterruptedException {
        //try to auth with bad creds
        badAuth();

        //Verify there's an error message displayed
        String text = "Invalid email or password";
        Assert.assertEquals(wd.getPageSource().contains(text),Boolean.TRUE);
    }

    @Test
    public void ManagerAuthTest() throws InterruptedException {
        //auth as a manager
        managerAuth();

        //Check for Clients link on the page
        checkClientsLink();        //Verify there's no error message on the screen
        String text = "Invalid email or password";
        Assert.assertEquals(wd.getPageSource().contains(text),Boolean.FALSE);

    }

    @Test
    public void GoodAuthafterBadAuth() throws InterruptedException {
        //try bad auth
        badAuth();
        //try manager auth
        managerAuth();
        checkClientsLink();
    }

}

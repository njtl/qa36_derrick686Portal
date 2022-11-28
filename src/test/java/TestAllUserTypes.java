import org.testng.Assert;
import org.testng.annotations.Test;

public class TestAllUserTypes extends TestBase {
    @Test
    public void LoginAsManager() throws InterruptedException {
        openLoginPage();
        managerAuth();
        Assert.assertTrue(searchOnPage("PROJECT OVERVIEW"));
        Assert.assertTrue(searchOnPage("CLIENTS"));
        Assert.assertTrue(searchOnPage("TEAM"));
        Assert.assertTrue(searchOnPage("INVOICES"));
        logout();
    }

    @Test
    public void LoginAsClient() throws InterruptedException {
        openLoginPage();
        clientAuth();
        Assert.assertTrue(searchOnPage("PROJECTS OVERVIEW"));
        Assert.assertFalse(searchOnPage("CLIENTS"));
        Assert.assertFalse(searchOnPage("TEAM"));
        Assert.assertTrue(searchOnPage("INVOICES"));
        logout();
    }

    @Test
    public void LoginAsConsultant() throws InterruptedException {
        openLoginPage();
        consultantAuth();
        Assert.assertTrue(searchOnPage("PROJECT OVERVIEW"));
        Assert.assertTrue(searchOnPage("CLIENTS"));
        Assert.assertTrue(searchOnPage("TEAM"));
        Assert.assertTrue(searchOnPage("INVOICES"));
        logout();
    }
}

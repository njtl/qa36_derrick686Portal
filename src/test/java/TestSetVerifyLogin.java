import javafx.beans.binding.ObjectExpression;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class TestSetVerifyLogin extends TestBase {
/*
    @BeforeMethod
    public void runBeforeMethod(Method m, Object[] p){
        logger.info("Starting method: " + m.getName()+" with data: "+ Arrays.asList(p));
    }*/

/*
    @DataProvider
    public Iterator<Object[]> getWrongLoginData(){
        List<Object[]> list = new ArrayList<>();

        list.add(new Object[]{"wrong email","password"});
        list.add(new Object[]{"biley@example.com","12345"});
        list.add(new Object[]{"biley_example.com","123456"});

        return list.iterator();
    }*/




    @Test
    public void BadAuthTest() throws InterruptedException {
        //try to auth with bad creds
        badAuth();

        //Verify there's an error message displayed
        String text = "Invalid email or password";
        Assert.assertEquals(wd.getPageSource().contains(text),Boolean.TRUE);
    }

/*    @Test(dataProvider = "getWrongCredsFromCSV")
    public void BadAuthTestWithDataProvider(String email, String pwd) throws InterruptedException {
        auth(email, pwd);
        String text = "Invalid email or password";
        Assert.assertEquals(wd.getPageSource().contains(text),Boolean.TRUE);
    }*/

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
/*
    @AfterMethod
    public void afterMLogout(Method m, Object[] p){
        logger.info("Quiting method: " + m.getName()+" with data: "+ Arrays.asList(p));
    }*/

}

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class TestClientSearch extends TestBase {

    @BeforeTest
    public void loginAsM() throws InterruptedException {
        logger.info("Starting method BeforeTest and authenicating as a Manager");
        managerAuth();
    }

    @BeforeMethod
    public void prepareClients() {
        logger.info("Starting method BeforeMethod and opening Clients page");
        openClientsPage();
    }

    @Test
    public void searchClientsByCompanyNegative() throws InterruptedException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info("Running "+methodName+" test");

        int randomNum = ThreadLocalRandom.current().nextInt(10000, 50000 + 1);
        logger.info("Searching for a company with a random company name: " + randomNum);
        searchClientsBy(String.valueOf(randomNum));

        Boolean isErrorOnPage = searchInPageSource("No results found, try adjusting your search and filters");
        Assert.assertTrue(isErrorOnPage);
        logger.info("Error message is found on page, passed test "+methodName);
    }

    @Test
    public void oneClientByCompanyName_Worman() throws InterruptedException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info("Running "+methodName+" test");

        String companyName = "Worman";

        logger.info("Searching for clients with a random company name: "+companyName);
        searchClientsBy(companyName);
        int expectedSearchResults = 1;
        List<WebElement> searchResults = wd.findElements(By.xpath("//*[@id=\"list2\"]/div[2]/div/div/div[1]/div"));
        logger.info("Found " + searchResults.size() + " results. Expected: "+ expectedSearchResults);
        Assert.assertEquals(searchResults.size(), expectedSearchResults);
    }

    @Test
    public void oneClientByCompanyName_Montag() throws InterruptedException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info("Running "+methodName+" test");

        String companyName = "Montag";

        logger.info("Searching for clients with a random company name: "+companyName);
        searchClientsBy(companyName);
        int expectedSearchResults = 2;
        List<WebElement> searchResults = wd.findElements(By.xpath("//*[@id=\"list2\"]/div[2]/div/div/div[1]/div"));
        logger.info("Found " + searchResults.size() + " results. Expected: "+ expectedSearchResults);
        Assert.assertEquals(searchResults.size(), expectedSearchResults);
    }

    @Test
    public void oneClientByClientName_billye() throws InterruptedException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info("Running "+methodName+" test");

        String clientName = "billye";

        logger.info("Searching for clients with a random company name: "+clientName);
        searchClientsBy(clientName);
        int expectedSearchResults = 1;
        List<WebElement> searchResults = wd.findElements(By.xpath("//*[@id=\"list2\"]/div[2]/div/div/div[1]/div"));
        logger.info("Found " + searchResults.size() + " results. Expected: "+ expectedSearchResults);
        Assert.assertEquals(searchResults.size(), expectedSearchResults);
    }

    @Test
    public void oneClientByClientName_lucie() throws InterruptedException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info("Running "+methodName+" test");

        String clientName = "lucie";

        logger.info("Searching for clients with a random company name: "+clientName);
        searchClientsBy(clientName);
        int expectedSearchResults = 1;
        List<WebElement> searchResults = wd.findElements(By.xpath("//*[@id=\"list2\"]/div[2]/div/div/div[1]/div"));
        logger.info("Found " + searchResults.size() + " results. Expected: "+ expectedSearchResults);
        Assert.assertEquals(searchResults.size(), expectedSearchResults);
        logger.error("Test passed " + methodName + ". Expected and actual results"+searchResults.size() + " " + expectedSearchResults);

    }


    @AfterTest
    public void clear(){
        logger.info("Starting method AfterTest and logging out");
        logout();
    }

}

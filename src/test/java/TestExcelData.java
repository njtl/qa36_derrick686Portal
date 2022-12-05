import org.testng.Assert;
import org.testng.annotations.Test;

public class TestExcelData extends TestBase {

    @Test(dataProvider = "excelWrongDataRead", dataProviderClass = DataProviders.class)
    public void BadAuthTestWithDataProviderExcel(String email, String pwd) throws InterruptedException {
        auth(email, pwd);
        String text = "Invalid email or password";
        Assert.assertEquals(wd.getPageSource().contains(text),Boolean.TRUE);
    }

    @Test(dataProvider = "csvWrongDataRead", dataProviderClass = DataProviders.class)
    public void BadAuthTestWithDataProviderCsv(String email, String pwd) throws InterruptedException {
        auth(email, pwd);
        String text = "Invalid email or password";
        Assert.assertEquals(wd.getPageSource().contains(text),Boolean.TRUE);
    }

}

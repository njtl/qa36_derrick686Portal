import org.testng.annotations.DataProvider;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class DataProviders {

    @DataProvider
    public static Iterator<Object[]> getWrongCredsFromCSV() throws IOException {
        List<Object[]> list = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/wrong_creds.csv")));

        String line = reader.readLine();
        while (line != null) {
            String[] split = line.split(",");
            list.add(split);
            line = reader.readLine();
        }

        return list.iterator();
    }
}

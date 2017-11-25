package Google;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
//@RunWith(value = Parameterized.class)
public class Task_1 {
    private static WebDriver driver;
    private final static String url = "https://www.google.com.ua";
    private  final String s = "свитер";
   // public Task_1(String s){
     //   this.s =s;
    //}
    @BeforeClass
    public static void setUp(){
        String exePath = "chromedriver";
        System.setProperty("webdriver.chrome.driver", exePath);
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }

    @Test
    public void testSearchField(){
        driver.get(url);
        SearchPage result = new SearchPage(driver);
        result.enterSearchData(s);
        List<String> heads = result.getStringHeads();
    }
    @AfterClass
    public static void close(){
        driver.quit();
    }

  //  @Parameterized.Parameters
    //public static Collection<Object[]> data() {
      //  Object[][] data = new Object[][] {{"lolik"}, {"bolik"}};
        //return Arrays.asList(data);
    //}
}

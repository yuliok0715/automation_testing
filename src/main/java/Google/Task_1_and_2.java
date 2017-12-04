package Google;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(value = Parameterized.class)
public class Task_1_and_2 {
    private static WebDriver driver;
    private final static String url = "https://www.google.com.ua";
    private String search;
    private String key;

    public Task_1_and_2(String key, String search) {
        this.key = key;
        this.search = search;
    }

    @BeforeClass
    public static void setUp() {
        String exePath = "chromedriver";
        System.setProperty("webdriver.chrome.driver", exePath);
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }


    @Test
    public void test_contains(){
        driver.get(url);
        int page_number = 1;
        SearchPage result = new SearchPage(driver);
        result.enterSearchData(search);
        boolean flag = false;
        while (!flag){
            List<String> heads = result.getStringHeads();
            for (int i = 0; i < heads.size(); i++) {
                if (heads.get(i).contains(key)) {
                    flag = true;
                    System.out.println("We have found your key: " + key);
                    System.out.println(String.format("Page number: %d ", page_number));
                    System.out.println(String.format("That was in result number %d", i+1));
                    File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                    try {
                        FileUtils.copyFile(scrFile, new File("task_" + key + "_" + search + ".png"));
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
            result = result.next_page();
            page_number += 1;
        }
    }

    @AfterClass
    public static void close() {
        driver.quit();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][]{{"Garne", "свитер"}, {"Vandastyle", "свитер"}};
        return Arrays.asList(data);
    }
}

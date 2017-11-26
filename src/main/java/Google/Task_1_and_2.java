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
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

@RunWith(value = Parameterized.class)
public class Task_1_and_2 {
    private static WebDriver driver;
    private final static String url = "https://www.google.com.ua";
    private String search;
    private String key;
    private final static String key_not = "colgate";

    public Task_1_and_2(String key, String search) {
        this.key = key;
        this.search = search;
    }

    private static void scale(int a) throws AWTException {
        Robot r = new Robot();
        for (int i = 0; i <= 10; i++) {
            r.keyPress(KeyEvent.VK_CONTROL);
            r.delay(50);
            r.mouseWheel(a);
            r.delay(50);
            r.keyRelease(KeyEvent.VK_CONTROL);
            r.delay(100);
        }
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
    public void test_contains() throws AWTException {
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
    @Test
    public void test_not_contains(){
        driver.get(url);
        int page_number = 1;
        SearchPage result = new SearchPage(driver);
        result.enterSearchData(search);
        boolean flag = false;
        while (!flag){
            List<String> heads = result.getStringHeads();
            for (String head : heads) {
                if (!head.contains(key_not)) {
                    File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                    try {
                        FileUtils.copyFile(scrFile, new File(String.format("task_%s_%d.png", key_not, page_number)));
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
            result = result.next_page();
            if (result == null){
                flag = true;
            }

            page_number += 1;
        }
    }


    @AfterClass
    public static void close() {
        driver.quit();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][]{{"Garne", "свитер"}, {"Musthave", "свитер"}};
        return Arrays.asList(data);
    }
}

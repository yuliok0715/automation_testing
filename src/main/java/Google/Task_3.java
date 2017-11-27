package Google;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
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
import java.util.concurrent.TimeUnit;

public class Task_3 {
    private static WebDriver driver;
    private final static String url = "https://www.google.com.ua";
    private final static String search = "свитер";
    private final static String key_not = "colgate";

    @BeforeClass
    public static void setUp() {
        String exePath = "chromedriver";
        System.setProperty("webdriver.chrome.driver", exePath);
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().window().maximize();
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
                System.out.println("Not found button");
            }

            page_number += 1;
        }
    }


    @AfterClass
    public static void close() {
        driver.quit();
    }
}


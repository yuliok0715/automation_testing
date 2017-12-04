package Rozetka;

import com.google.common.base.Predicate;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

class FictionBook {
    WebDriver driver;
    FictionBook(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(id = "price[min]")
    private WebElement min_price;

    @FindBy(css = "#submitprice")
    private WebElement submit_price_button;

    private List<WebElement> prices;

    List<Integer> prices(){
        new WebDriverWait(driver, 30, 350 )//сказитись можна. як простіше?
                .ignoring(StaleElementReferenceException.class)
                .until(new Predicate<WebDriver>() {
                    public boolean apply(WebDriver driver) {
                        prices = driver.findElements(new By.ByClassName("g-price-uah"));
                        return true;
                    }
                });
        List<Integer> int_prices = new ArrayList<Integer>(prices.size());
        for (WebElement price: prices){
             String price_s = price.getText().replaceAll("[^0-9]*", "");
             int_prices.add(Integer.parseInt(price_s));
        }
        return int_prices;
    }


    FictionBook set_min_price(Integer price){
        if(price == null) return this;
        min_price.sendKeys(price.toString());
        return this;
    }


    void submit_filter_price(){
        submit_price_button.sendKeys(Keys.ENTER);
    }

    Integer get_min_price(){
        String MinPriceValue = min_price.getAttribute("value");
        if(MinPriceValue == null | MinPriceValue.equals("")) return null;
        else {
            return Integer.parseInt(MinPriceValue);
        }
    }
}

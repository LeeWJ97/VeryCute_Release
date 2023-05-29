package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

// page_url = https://www.baidu.com/
public class Search extends LoadableComponent<Search> {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//*[@id='kw']")
    public WebElement searchInput;

    @FindBy(xpath = "//*[@id='su']")
    public WebElement searchBtn;


    public Search(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        PageFactory.initElements(driver, this);
    }

    public void setInputText(String text){
        wait.until(ExpectedConditions.visibilityOf(searchInput)).clear();
        searchInput.sendKeys(text);
    }

    public void sumbitSearch(){
        //wait.until(ExpectedConditions.elementToBeClickable(searchBtn)).click();
        wait.until(ExpectedConditions.elementToBeClickable(searchInput)).submit();
    }

    public String getTitle(final String titleStartsWith) {
        try {
            wait.until((ExpectedCondition<Boolean>) d -> d.getTitle().toLowerCase().startsWith(titleStartsWith));
        }
        finally {
            return driver.getTitle();
        }
    }

    @Override
    protected void load() {
        driver.get("https://baidu.com");
    }

    @Override
    protected void isLoaded() throws Error {
        String url = driver.getCurrentUrl();
        assertTrue("Not on the issue entry page: " + url, url.contains("baidu.com"));
    }

}
package com.verycute.pages.app.Chrome;

import com.verycute.springconfig.annotation.LazyAutowired;
import com.verycute.springconfig.annotation.PageObject;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@PageObject
public class Chrome_MainPage {
    private AppiumDriver driver;
    @LazyAutowired
    @Qualifier("appDriverWait")
    private WebDriverWait wait;

    @FindBy(id = "com.android.chrome:id/signin_fre_dismiss_button")
    public WebElement dismissBtn;

    @FindBy(id = "com.android.chrome:id/negative_button")
    public WebElement negativeBtn;

    @FindBy(id = "com.android.chrome:id/button_primary")
    public WebElement sogouBtn;

    @FindBy(id = "com.android.chrome:id/tab_switcher_toolbar0")
    public WebElement googleLogo;

    @FindBy(id = "com.android.chrome:id/full_promo_content")
    public WebElement ChineseGoogleLogo;

    @FindBy(id = "com.android.chrome:id/terms_accept")
    public WebElement chineseAccept;


    @Autowired
    public Chrome_MainPage(AppiumDriver driver) {
        this.driver =  driver;
        PageFactory.initElements(driver, this);
    }

    public void clickdismissBtn(){
        wait.until(ExpectedConditions.elementToBeClickable(dismissBtn)).click();
    }

    public void clicknegativeBtn(){
        wait.until(ExpectedConditions.elementToBeClickable(negativeBtn)).click();
    }

    public void clickChineseAcceptBtn(){
        wait.until(ExpectedConditions.elementToBeClickable(chineseAccept)).click();
    }

    public void clicksogouBtn(){
        wait.until(ExpectedConditions.elementToBeClickable(sogouBtn)).click();
    }

    public void getGoogleLogo() {
        wait.until(ExpectedConditions.visibilityOf(googleLogo));
    }

    public void getChineseGoogleLogo() {
        wait.until(ExpectedConditions.visibilityOf(ChineseGoogleLogo));
    }


}

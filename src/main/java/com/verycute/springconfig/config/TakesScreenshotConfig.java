package com.verycute.springconfig.config;

import com.verycute.factory.AppDriverFactory;
import com.verycute.factory.DriverFactory;
import com.verycute.springconfig.annotation.LazyConfiguration;
import org.openqa.selenium.TakesScreenshot;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@LazyConfiguration
public class TakesScreenshotConfig {

    @Bean(name = "takesScreenshot")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public TakesScreenshot takesScreenshot() {
        if (AppDriverFactory.threadLocalAppDriver.get() != null){
            return (TakesScreenshot) AppDriverFactory.getDriver();
        }
        if (DriverFactory.threadLocalWebDriver.get() != null){
            return (TakesScreenshot) DriverFactory.getDriver();
        }
        return null;
    }
}



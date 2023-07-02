package com.verycute.springconfig.config;

import com.verycute.springconfig.annotation.LazyConfiguration;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.time.Duration;

@LazyConfiguration
public class AppDriverWaitConfig {
    @Value("${appdriver.default.timeout:180}")
    private int timeout;
    @Bean(name = "appDriverWait")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public WebDriverWait appDriverWait(AppiumDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }
}



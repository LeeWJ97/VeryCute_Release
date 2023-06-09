package com.verycute.springconfig.config;

import com.verycute.factory.DriverFactory;
import com.verycute.springconfig.annotation.LazyConfiguration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;

import java.time.Duration;

@LazyConfiguration
public class WebDriverWaitConfig {
    @Value("${driver.default.timeout:10}")
    private int timeout;
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public WebDriverWait webDriverWait(@Qualifier("webDriver") WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }
}



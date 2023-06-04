package com.verycute.springconfig.config;

import com.verycute.factory.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class WebDriverConfig {

//    @Bean(name = "webDriver")
//    public WebDriver webDriver() {
//        DriverFactory driverFactory = new DriverFactory();
//        return driverFactory.initDriver();
//    }
}



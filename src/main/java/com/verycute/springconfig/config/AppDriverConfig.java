package com.verycute.springconfig.config;


import com.verycute.factory.AppDriverFactory;
import com.verycute.springconfig.annotation.LazyConfiguration;
import io.appium.java_client.AppiumDriver;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import java.net.MalformedURLException;

@LazyConfiguration
public class AppDriverConfig {

    @Bean(name = "appDriver")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public AppiumDriver appDriver() {
        return AppDriverFactory.getDriver();
    }
}



package com.verycute.springconfig;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

@CucumberContextConfiguration   //Please ensure only one class configures the spring context
@SpringBootTest
public class CucumberSpringConfiguration { }
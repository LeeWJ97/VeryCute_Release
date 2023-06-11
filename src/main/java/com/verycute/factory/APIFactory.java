package com.verycute.factory;

import com.alibaba.fastjson.JSONObject;
import com.verycute.utils.FastJsonUtils;
import com.verycute.utils.IOUtils;
import io.restassured.RestAssured;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.restassured.response.Response;
import java.io.IOException;
import java.util.Map;

public class APIFactory {
    public RestAssured ra;
    public static ThreadLocal<RestAssured> threadLocalAPI = new ThreadLocal<>();  //For Parallel execution

    public RestAssured initAPI() {
        ra = new RestAssured();
        threadLocalAPI.set(ra);
        return ra;
    }


    public static synchronized RestAssured getAPI() {
        if (threadLocalAPI.get() != null){
            return threadLocalAPI.get(); //For Parallel execution
        }
        else{
            APIFactory apiFactory = new APIFactory();
            return apiFactory.initAPI();
        }
    }

    public static synchronized void removeAPI() {
        threadLocalAPI.remove(); //For Parallel execution
    }
}

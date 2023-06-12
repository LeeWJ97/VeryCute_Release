package com.verycute.factory;

import com.alibaba.fastjson.JSONObject;
import com.verycute.springconfig.annotation.Factory;
import com.verycute.utils.FastJsonUtils;
import com.verycute.utils.IOUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.RestAssured;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.Map;

@Factory
public class APIFactory {
    public RequestSpecification rs;
    public static String configPath = null;
    public static JSONObject configJsonObject = null;
    public static ThreadLocal<RequestSpecification> threadLocalAPI = new ThreadLocal<>();  //For Parallel execution

    @Value("${api.config.filename:target/test-classes/api-config.json}")
    private void setconfigPath(String configPath) {
        if (APIFactory.configPath == null){
            APIFactory.configPath = configPath;
        }
    }

    public RequestSpecification initAPI() {
        readConfig();
        JSONObject apiArgs = configJsonObject.getJSONObject("apiArgs");

        rs = RestAssured.given(); // static method

        if (apiArgs.getBooleanValue("proxy") == true){
            String proxyInfo = apiArgs.getString("proxy-server");
            String proxyURL = proxyInfo.split(":")[0];
            int proxyPort = Integer.parseInt(proxyInfo.split(":")[1]);
            rs = rs.relaxedHTTPSValidation();
            rs = rs.proxy(proxyURL, proxyPort);
        }

        threadLocalAPI.set(rs);
        return rs;
    }

    private static void readConfig() {
        if (configJsonObject == null){
            try {
                String config = IOUtils.readTextFile(configPath, "utf-8");
                configJsonObject = FastJsonUtils.toJObject(config);
            } catch (IOException e) {
                System.out.println("failed to get config: " + configPath);
                throw new RuntimeException(e);
            }
        }
    }


    public static synchronized RequestSpecification getAPI() {
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

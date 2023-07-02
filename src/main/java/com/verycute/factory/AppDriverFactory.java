package com.verycute.factory;

import com.alibaba.fastjson.JSONObject;
import com.verycute.springconfig.annotation.Factory;
import com.verycute.utils.FastJsonUtils;
import com.verycute.utils.IOUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.options.BaseOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import io.appium.java_client.remote.MobileCapabilityType;
@Factory
public class AppDriverFactory {
    private static final Logger logger = LoggerFactory.getLogger(AppDriverFactory.class);
    public AppiumDriver driver;
    public static String configPath = null;
    public static JSONObject configJsonObject = null;
    public static ThreadLocal<AppiumDriver> threadLocalAppDriver = new ThreadLocal<>();  //For Parallel execution
    public static ThreadLocal<String> threadLocalMachineInfo = new ThreadLocal<>();  //For Parallel execution
    public static List<String> machinePool = Collections.synchronizedList(new ArrayList<>());
    @Value("${appdriver.config.filename:target/test-classes/appdriver-config.json}")
    private void setconfigPath(String configPath) {
        if (AppDriverFactory.configPath == null){
            AppDriverFactory.configPath = configPath;
        }
    }

    @Value("#{'${appdriver.machinepool:http://127.0.0.1:4723}'.split(',')}")
    private void setMachinePool(List<String> machinePool) {
        if (AppDriverFactory.machinePool.size() == 0){
            AppDriverFactory.machinePool = machinePool;
//            machinePool.forEach(str -> {
//                try {
//                    URL url = new URL(str);
//                    AppDriverFactory.machinePool.add(url);
//                } catch (MalformedURLException e) {
//                    logger.error(e.getMessage());
//                }
//            });
        }
    }


    public AppiumDriver initDriver() {
        readConfig();

        URL url = null;
        String deviceName = null;
        boolean getMachineFlag = false;
        while (true){
            synchronized (machinePool) {
                if (machinePool.size() > 0){
                    threadLocalMachineInfo.set(machinePool.remove(0));
                    logger.info("get machine: " + threadLocalMachineInfo.get());
                    getMachineFlag = true;
                }
            }
            if (getMachineFlag){
                String[] machineInfo = threadLocalMachineInfo.get().split("\\|");
                try {
                    url = new URL(machineInfo[0]);
                    deviceName = machineInfo[1];
                } catch (MalformedURLException e) {
                    logger.error("Get machine failed: " + e.getMessage());
                    //throw new RuntimeException(e);
                }
                finally {
                    break;
                }

            }
            else{
                logger.info("wait machine: " + machinePool.size());
            }

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        BaseOptions options = new BaseOptions();
        Map<String,String> name = (Map<String, String>) configJsonObject.get("Name");
        Map<String,String> driverArgs = (Map<String, String>) configJsonObject.get("driverArgs");

        options.setPlatformName(name.get("PlatformName")).setAutomationName(name.get("AutomationName"));
        options.amend(MobileCapabilityType.DEVICE_NAME,deviceName);
        options.amend(MobileCapabilityType.UDID,deviceName);  //bind appium with specific device


        for (Map.Entry<String, String> m:
        driverArgs.entrySet()) {
            options.amend(m.getKey(), m.getValue());
        }

//        BaseOptions options = new BaseOptions()
//                .setPlatformName("Android")
//                .setAutomationName("uiautomator2")
//                .amend(MobileCapabilityType.DEVICE_NAME,deviceName)
//                //.setNoReset(true)
//                .amend("appPackage","com.android.chrome")
//                .amend("appActivity","com.google.android.apps.chrome.Main")
//                //.amend(MobileCapabilityType.APP, "D:\\edge.apk")
//                .amend("uiautomator2ServerLaunchTimeout", "60000")
//                .amend("adbExecTimeout", "60000")
//                ;


        driver = new AppiumDriver(url, options);
        logger.info(url + " " + deviceName);
        threadLocalAppDriver.set(driver);
        return driver;
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

    public static AppiumDriver getDriver(){
        if (threadLocalAppDriver.get() != null){
            return threadLocalAppDriver.get(); //For Parallel execution
        }
        else{
            AppDriverFactory appdriverFactory = new AppDriverFactory();
            return appdriverFactory.initDriver();
        }
    }
    public static void removeDriver() {
        try {
            logger.info("start to set back machine...");
            synchronized (machinePool) {
                AppDriverFactory.machinePool.add(threadLocalMachineInfo.get());
            }
            logger.info("set back machine: " + threadLocalMachineInfo.get());
            threadLocalAppDriver.get().quit();
        }
        catch (Exception e){
            logger.error("set back machine failed: " + e.getMessage());
        }
        finally {
            threadLocalAppDriver.remove();
            threadLocalMachineInfo.remove();
        }

    }

}

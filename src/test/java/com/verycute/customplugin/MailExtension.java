package com.verycute.customplugin;

import com.alibaba.fastjson.JSONObject;
import com.verycute.hooks.Hooks;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.EventListener;
import io.cucumber.plugin.event.*;
import com.verycute.utils.DateTimeUtils;
import com.verycute.utils.EmailUtils;
import com.verycute.utils.FastJsonUtils;
import com.verycute.utils.IOUtils;
import io.cucumber.spring.CucumberContextConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.MessagingException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;


// this plugin will execute after whole cucumber testing

public class MailExtension implements ConcurrentEventListener {
    private static final Logger logger = LoggerFactory.getLogger(MailExtension.class);
    private static final String reportFile = "HTMLReport/TestReport.html";
    private static final String logFile = "logs/TestLog.log";
    private static String runnerName;
    private static String message;
    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestRunFinished.class, this::onTestRunFinished);
    }

    private void onTestRunFinished(TestRunFinished event) {
        try {
            backupResult();
        } catch (IOException e) {
            logger.error("backup Report failed: " + e.getMessage());
        }

        try {
            backupLog();
        } catch (IOException e) {
            logger.error("backup Log failed: " + e.getMessage());
        }


        sendEmail();

    }

    private static void sendEmail() {
        String fromEmail = "";
        String password = "";
        String toEmail = "";
        String mailConfigPath = "";
        try {
            mailConfigPath = "target/test-classes/mail-config-dev.json";
            String mailConfig = IOUtils.readTextFile(mailConfigPath, "utf-8");
            JSONObject mailConfigJsonObject = FastJsonUtils.toJObject(mailConfig);
            fromEmail = mailConfigJsonObject.getString("fromEmail");
            password = mailConfigJsonObject.getString("password");
            toEmail = mailConfigJsonObject.getString("toEmail");
        } catch (IOException e) {
            logger.warn("failed to get mail-config: " + mailConfigPath);
            //throw new RuntimeException(e);
            return;
        }
        logger.info("start to send mail ...");
        String subject = getRunnerName() + ": UI Automation Test Report";
        //message = "Please see attachment.";
        String host = "smtp.qq.com";
        String port = "465";

        try{
            EmailUtils.sendEmail(fromEmail, password, toEmail, subject, message, host, port, new ArrayList<>(Arrays.asList(reportFile, logFile)));
        } catch (MessagingException e) {
            logger.warn("Failed to send mail");
            //throw new RuntimeException(e);
            return;
        }
    }

    private static String getRunnerName(){
        if (runnerName != null && !runnerName.isEmpty()) return runnerName;
        // get the runner name
        try {
            String entryClassName;
            entryClassName = System.getProperty("test");
            if (entryClassName == null){
                entryClassName = System.getProperty("sun.java.command");
                if (entryClassName != null && !entryClassName.isEmpty()) {
                    String[] entryClassNameList = entryClassName.split(" ");
                    entryClassName = entryClassNameList[entryClassNameList.length - 1];

                    entryClassNameList = entryClassName.split("\\.");

                    return entryClassNameList[entryClassNameList.length - 1];
                } else {
                    return "Test";
                }
            }
            else {
                return entryClassName;
            }


        } catch (Exception e) {
            return "Test";
        }
    }

    public static void backupResult() throws IOException {
        String testResultContent  = IOUtils.readTextFile(reportFile, "UTF-8");;
//        while (true){
//            try {
//                testResultContent = IOUtils.readTextFile(reportFile, "UTF-8");
//                break;
//            } catch (IOException e) {
//                logger.info("finding test report...");
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException ex) {
//                    throw new RuntimeException(ex);
//                }
//            }
//        }

        testResultContent = testResultContent.replace("https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css","https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css");
        IOUtils.writeTextFile(reportFile, testResultContent,"UTF-8");
        message = testResultContent;

        String today = DateTimeUtils.formatDateText(DateTimeUtils.timestampToDate(DateTimeUtils.getCurrentTimestamp()));
        String regex = "[\\\\/:*?\"<>|]";
        File sourceFile = new File(reportFile);
        File targetFolder = new File(reportFile.split("/")[0] + "/" + getRunnerName() + "_" + today.replaceAll(regex, "_") );

        try {
            if (!targetFolder.exists()) {
                targetFolder.mkdirs();
            }

            File targetFile = new File(targetFolder, sourceFile.getName());
            Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            logger.info("backup html report successfully: " + targetFile.getPath());
            sourceFile.deleteOnExit();
        } catch (IOException e) {
            logger.error("backup html report failed：" + e.getMessage());
            //throw e;
        }
    }


    public static void backupLog() throws IOException {
        String testResultContent = IOUtils.readTextFile(logFile, "UTF-8");

        String today = DateTimeUtils.formatDateText(DateTimeUtils.timestampToDate(DateTimeUtils.getCurrentTimestamp()));
        String regex = "[\\\\/:*?\"<>|]";
        File sourceFile = new File(logFile);
        File targetFolder = new File(logFile.split("/")[0] + "/" + getRunnerName() + "_" + today.replaceAll(regex, "_") );

        try {
            if (!targetFolder.exists()) {
                targetFolder.mkdirs();
            }

            File targetFile = new File(targetFolder, sourceFile.getName());
            Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            logger.info("backup log successfully: " + targetFile.getPath());

            sourceFile.deleteOnExit();
        } catch (IOException e) {
            logger.error("backup log failed：" + e.getMessage());
            //throw e;
        }
    }



}

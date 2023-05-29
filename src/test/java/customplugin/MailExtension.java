package customplugin;

import com.alibaba.fastjson.JSONObject;
import io.cucumber.plugin.EventListener;
import io.cucumber.plugin.event.*;
import utils.DateTimeUtils;
import utils.EmailUtils;
import utils.FastJsonUtils;
import utils.IOUtils;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

// this plugin will execute after whole cucumber testing
public class MailExtension implements EventListener {
    private static final String attachment = "HTMLReport/TestReport.html";
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
            System.out.println("backResult failed: " + e.getMessage());
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
            System.out.println("failed to get mail-config: " + mailConfigPath);
            //throw new RuntimeException(e);
            return;
        }
        System.out.println("start to send mail ...");
        String subject = getRunnerName() + ": UI Automation Test Report";
        //message = "Please see attachment.";
        String host = "smtp.qq.com";
        String port = "465";

        try{
            EmailUtils.sendEmail(fromEmail, password, toEmail, subject, message, host, port, attachment);
        } catch (MessagingException e) {
            System.out.println("Failed to send mail");
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
        String testResultContent = IOUtils.readTextFile(attachment, "UTF-8");
        testResultContent = testResultContent.replace("https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css","https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css");
        IOUtils.writeTextFile(attachment, testResultContent,"UTF-8");
        message = testResultContent;

        String today = DateTimeUtils.formatDateText(DateTimeUtils.timestampToDate(DateTimeUtils.getCurrentTimestamp()));
        String regex = "[\\\\/:*?\"<>|]";
        File sourceFile = new File(attachment);
        File targetFolder = new File("HTMLReport/"+ getRunnerName() + "_" + today.replaceAll(regex, "_") );

        try {
            if (!targetFolder.exists()) {
                targetFolder.mkdirs();
            }

            File targetFile = new File(targetFolder, sourceFile.getName());
            Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            System.out.println("backup html report successfully!");
        } catch (IOException e) {
            System.out.println("backup html report failed：" + e.getMessage());
            //throw e;
        }
    }

}

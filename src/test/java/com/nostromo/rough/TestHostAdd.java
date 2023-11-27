package com.nostromo.rough;

import com.nostromo.utilities.MonitoringMail;
import com.nostromo.utilities.TestConfig;
import jakarta.mail.MessagingException;

import java.net.InetAddress;
import java.net.UnknownHostException;

//import javax.mail.MessagingException;

public class TestHostAdd {
    public static void main(String[] args) throws UnknownHostException, MessagingException {
        MonitoringMail mail = new MonitoringMail();
        String messageBody = "http://" + InetAddress.getLocalHost().getHostAddress() + ":8080/job/data-driven-framework-idea/HTML_20Report";
        System.out.println(messageBody);
        mail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to, TestConfig.subject, messageBody);
    }
}

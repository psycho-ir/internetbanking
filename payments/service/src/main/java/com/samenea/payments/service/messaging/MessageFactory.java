package com.samenea.payments.service.messaging;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author: Soroosh Sarabadani
 * Date: 3/16/13
 * Time: 2:29 PM
 */

public class MessageFactory {

    private MessageFactory() {
    }

    public static MimeMessagePreparator createHtmlMessage(final String from, final String to, final String subject, final String message) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
                mimeMessage.setSubject(subject);
                mimeMessage.setRecipient(Message.RecipientType.TO,
                        new InternetAddress(to));
                mimeMessage.setFrom(new InternetAddress(from));
//                mimeMessageHelper.setText(message, true);
                mimeMessage.setText(message,"utf-8");
                mimeMessage.addHeader("Content-Type","text/html;");


            }
        };

        return preparator;
    }
}

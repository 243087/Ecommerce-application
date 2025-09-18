package com.rahul.sendemailservice.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rahul.sendemailservice.dtos.SendEmailDto;
import com.rahul.sendemailservice.utils.EmailUtil;
import org.springframework.kafka.annotation.KafkaListener;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

@Component
public class SendEmailEventConsumer {
    private ObjectMapper objectMapper;

    public SendEmailEventConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
/*
below @KafkaListener : We write on top of that method which we want to execute automatically whenever
topic name ='sendEmail' comes in Message Queue(kafka)
 */
    @KafkaListener(topics = "sendEmail",groupId = "emailService")
    public void handleSendEmailEvent(String message) throws JsonProcessingException {
        SendEmailDto emailDto = objectMapper.readValue(message, SendEmailDto.class);


            String to = emailDto.getEmail();
            String subject = emailDto.getSubject();
            String body = emailDto.getBody();

            // code for send an email, we will get from

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP(Simple mail transfer protocal) Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("rahulkumar243k@gmail.com", "ajqnzouzfxdxciof");
            }
        };
        Session session = Session.getInstance(props, auth);

        EmailUtil.sendEmail(session, to, subject, body);
    }

}

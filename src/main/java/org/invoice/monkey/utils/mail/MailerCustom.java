package org.invoice.monkey.utils.mail;

import org.invoice.monkey.App;
import org.invoice.monkey.model.Configurations.Configuration;
import org.invoice.monkey.model.Configurations.EmailDetails;

import javax.mail.Transport;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class MailerCustom implements Mailer{

    private EmailDetails emailDetails;

    private String sender;
    private String receiver;

    private String subject;
    private String message;
    private String attachment;


    public MailerCustom()
    {
        emailDetails  = App.getConfiguration().getEmailDetails();
        sender = emailDetails.getEmail();
    }


    private MimeMessage createEmail(Session session) throws MessagingException
    {
        MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress(sender));
        email.addRecipient(javax.mail.Message.RecipientType.TO,
                new InternetAddress(receiver));
        email.setSubject(subject);

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(message, "text/plain");



        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        if(emailDetails.getLink()) {
            MimeBodyPart linkToRepository = new MimeBodyPart();
            linkToRepository.setContent("<br><br><br>This invoice was generated using <a href=\"https://github.com/varunborar/Invoice-Monkey\"> " +
                            "Invoice Monkey.</a>",
                    "text/html");
            multipart.addBodyPart(linkToRepository);
        }

        MimeBodyPart invoice = new MimeBodyPart();
        File file = new File(attachment);
        DataSource source = new FileDataSource(file);

        invoice.setDataHandler(new DataHandler(source));
        invoice.setFileName(file.getName());

        multipart.addBodyPart(invoice);
        email.setContent(multipart);

        return email;
    }

    public void setEmailContent(String receiver, String subject, String message, String attachment)
    {
        this.receiver = receiver;
        this.subject = subject;
        this.message = message;
        this.attachment = attachment;
    }

    public void sendMessage()
    {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", emailDetails.getHost());

        Session session;

        if(emailDetails.getAuthentication())
        {
            properties.put("mail.smtp.socketFactory.port", emailDetails.getPort());
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            properties.put("mail.smtp,port", emailDetails.getPort());
            properties.put("mail.smtp.auth", "true");
            session  = Session.getDefaultInstance(properties,
                    new javax.mail.Authenticator(){
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(sender, emailDetails.getPassword());
                        }
                    });
        }
        else{
            session = Session.getDefaultInstance(properties);
        }

        try{
            MimeMessage message = createEmail(session);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}

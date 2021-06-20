package org.invoice.monkey.utils.mail;

import com.google.api.client.util.Base64;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import org.invoice.monkey.App;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Properties;

public class MailerGmail implements Mailer{

    private String sender;
    private String receiver;

    private String subject;
    private String message;
    private String attachment;

    private final Gmail service;

    public MailerGmail() throws GeneralSecurityException, IOException {
        service = MailerFactory.getService();
        sender = service.users().getProfile("me").getUserId();
    }

    private MimeMessage createEmail() throws MessagingException
    {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props);

        MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress(sender));
        email.addRecipient(javax.mail.Message.RecipientType.TO,
                new InternetAddress(receiver));
        email.setSubject(subject);

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(message, "text/plain");


        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        if(App.getConfiguration().getEmailDetails().getLink()) {
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

    private Message createMessage(MimeMessage emailContent) throws MessagingException, IOException
    {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        emailContent.writeTo(buffer);
        byte[] bytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }

    public void sendMessage() throws MessagingException, IOException {
        MimeMessage emailContent = createEmail();
        Message message = createMessage(emailContent);
        service.users().messages().send("me", message).execute();
    }


    public void setEmailContent(String receiver, String subject, String message, String attachment)
    {
        this.receiver = receiver;
        this.subject = subject;
        this.message = message;
        this.attachment = attachment;
    }

}

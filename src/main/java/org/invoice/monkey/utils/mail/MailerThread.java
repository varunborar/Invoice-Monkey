package org.invoice.monkey.utils.mail;

import javax.mail.MessagingException;
import java.io.IOException;

public class MailerThread implements Runnable{

    Mailer mailer;

    public MailerThread(Mailer mailer)
    {
        this.mailer = mailer;
    }

    public void run()
    {
        try {
            mailer.sendMessage();
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }
}

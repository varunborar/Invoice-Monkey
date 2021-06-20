package org.invoice.monkey.utils.mail;

import javax.mail.MessagingException;
import java.io.IOException;

public interface Mailer {

    void setEmailContent(String receiver, String subject, String message, String attachment);

    void sendMessage() throws MessagingException, IOException;
}

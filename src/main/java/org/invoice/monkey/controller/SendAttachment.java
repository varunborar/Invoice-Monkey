package org.invoice.monkey.controller;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.invoice.monkey.App;
import org.invoice.monkey.model.Configurations.EmailDetails;
import org.invoice.monkey.utils.mail.Mailer;
import org.invoice.monkey.utils.mail.MailerCustom;
import org.invoice.monkey.utils.mail.MailerGmail;
import org.invoice.monkey.utils.mail.MailerThread;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class SendAttachment {


    @FXML
    private JFXTextField to;
    @FXML
    private JFXTextField subject;
    @FXML
    private JFXTextArea message;
    @FXML
    private JFXCheckBox link;

    private EmailDetails emailDetails;

    public static String attachment;
    public static String customerName;

    public void initialize()
    {
        emailDetails = App.getConfiguration().getEmailDetails();
        subject.setText(emailDetails.getSubject());
        message.setText(emailDetails.getMessage().replaceFirst("Customer", customerName));
        link.selectedProperty().set(emailDetails.getLink());
    }

    public void send(ActionEvent event)
    {
        Mailer mailer;
        try {
            if (emailDetails.isCustomSet())
                mailer = new MailerCustom();
            else
                mailer = new MailerGmail();

            mailer.setEmailContent(to.getText(), subject.getText(), message.getText(), attachment);
            MailerThread mail = new MailerThread(mailer);
            mail.run();
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }

        cancel(event);
    }

    public void cancel(ActionEvent event)
    {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }
}

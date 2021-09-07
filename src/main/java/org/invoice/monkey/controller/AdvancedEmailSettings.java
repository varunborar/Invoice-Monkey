package org.invoice.monkey.controller;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import org.invoice.monkey.App;
import org.invoice.monkey.model.Configurations.EmailDetails;

public class AdvancedEmailSettings {

    @FXML
    private JFXTextField subject;
    @FXML
    private JFXTextArea message;
    @FXML
    private JFXCheckBox link;
    @FXML
    private JFXButton saveMessage;
    @FXML
    private JFXButton cancelMessage;


    @FXML
    private ToggleGroup mailType;
    @FXML
    private JFXRadioButton gmail;
    @FXML
    private JFXRadioButton custom;
    @FXML
    private JFXButton saveAdvanced;
    @FXML
    private JFXButton cancelAdvanced;


    @FXML
    private JFXTextField host;
    @FXML
    private JFXTextField port;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXCheckBox ssl;
    @FXML
    private JFXCheckBox authentication;


    @FXML
    public void initialize()
    {
        saveMessage.setDisable(true);
        saveAdvanced.setDisable(true);

        mailType.selectedToggleProperty().addListener(((observableValue, oldValue, newValue)-> {
            if(gmail.isSelected())
            {
                host.setText("");
                host.setDisable(true);
                port.setText("");
                port.setDisable(true);
                ssl.selectedProperty().set(false);
                ssl.setDisable(true);
                authentication.selectedProperty().set(false);
                authentication.setDisable(true);

                email.setText("");
                email.setDisable(true);
                password.setText("");
                password.setDisable(true);
            }else if(custom.isSelected())
            {
                host.setDisable(false);
                port.setDisable(false);
                ssl.setDisable(false);
                authentication.setDisable(false);
            }
            saveAdvanced.setDisable(false);
        }));

        authentication.selectedProperty().addListener(((observable, oldValue, newValue )->{
            if(authentication.isSelected())
            {
                email.setDisable(false);
                password.setDisable(false);
            }
            else{
                email.setDisable(true);
                password.setDisable(true);
            }
        }));

        EmailDetails details = App.getConfiguration().getEmailDetails();


        subject.setText(details.getSubject());
        message.setText(details.getMessage());
        link.selectedProperty().set(details.getLink());

        if(!details.isCustomSet())
        {
            gmail.selectedProperty().set(true);
            host.setText("");
            host.setDisable(true);
            port.setText("");
            port.setDisable(true);
            ssl.selectedProperty().set(false);
            ssl.setDisable(true);
            authentication.selectedProperty().set(false);
            authentication.setDisable(true);

            email.setText("");
            email.setDisable(true);
            password.setText("");
            password.setDisable(true);
        }
        else{

            custom.selectedProperty().set(true);
            host.setText(details.getHost());
            port.setText(details.getPort());
            ssl.selectedProperty().set(details.getSSL());
            authentication.selectedProperty().set(details.getAuthentication());

            if(authentication.isSelected())
            {
                email.setText(details.getEmail());
                password.setText(details.getPassword());
            }
        }
    }


    public void saveMessage()
    {
        App.getConfiguration().getEmailDetails().setSubject(subject.getText());
        App.getConfiguration().getEmailDetails().setMessage(message.getText());
        App.getConfiguration().getEmailDetails().setLink(link.isSelected());
        App.getConfiguration().refresh();
        cancelMessage.setText("Close");
    }

    public void saveAdvanced(ActionEvent event)
    {
        if(custom.isSelected())
        {
            App.getConfiguration().getEmailDetails().setCustom(true);
            App.getConfiguration().getEmailDetails().setHost(host.getText());
            App.getConfiguration().getEmailDetails().setPort(port.getText());
            App.getConfiguration().getEmailDetails().setSSL(ssl.isSelected());
            App.getConfiguration().getEmailDetails().setAuthentication(authentication.isSelected(),
                    email.getText(),
                    password.getText());
            App.getConfiguration().refresh();
        }else{
            App.getConfiguration().getEmailDetails().setCustom(false);
            App.getConfiguration().refresh();
        }

        cancelAdvanced.setText("Close");
    }

    public void cancel(ActionEvent event)
    {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    public void enableSave()
    {
        saveMessage.setDisable(false);
    }
}

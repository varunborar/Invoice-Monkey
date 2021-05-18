package org.invoice.monkey.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.invoice.monkey.App;
import org.invoice.monkey.model.Configurations.Configuration;
import org.invoice.monkey.utils.File;
import org.invoice.monkey.utils.Validator;

public class EditCompanyDetails {


    Configuration configuration;

    @FXML
    private TextField orgName;
    @FXML
    private TextField orgNumber;
    @FXML
    private TextField orgEmail;
    @FXML
    private TextField orgSignatory;

    @FXML
    private TextField building;
    @FXML
    private TextField city;
    @FXML
    private TextField state;
    @FXML
    private TextField postalCode;

    @FXML
    private TextField defaultLocation;
    @FXML
    private TextField logoLocation;

    @FXML
    private Button save;

    @FXML
    public void initialize()
    {
        configuration = new Configuration();

        orgName.setText(configuration.getOrgDetails().getOrgName());
        orgEmail.setText(configuration.getOrgDetails().getOrgEmail());
        orgNumber.setText(configuration.getOrgDetails().getOrgNumber());
        orgSignatory.setText(configuration.getOrgDetails().getOrgSignatory());

        building.setText(configuration.getAddress().getOrgAddress());
        city.setText(configuration.getAddress().getOrgCity());
        state.setText(configuration.getAddress().getOrgState());
        postalCode.setText(configuration.getAddress().getOrgPostalCode());

        defaultLocation.setText(configuration.getAppConfigurations().getDefaultLocation());
    }

    public void browseFolder(ActionEvent ae)
    {
        DirectoryChooser folderLoc = new DirectoryChooser();
        folderLoc.setTitle("Choose Folder");
        java.io.File folder = folderLoc.showDialog(App.getstage());
        if(!(folder == null))
            defaultLocation.setText(folder.getAbsolutePath());
    }

    public void browseLogo(ActionEvent ae)
    {

        Node node = (Node) ae.getSource();
        Stage s = (Stage) node.getScene().getWindow();

        FileChooser logoLoc = new FileChooser();
        logoLoc.setTitle("Choose Logo");
        logoLoc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files","*.png"));

        java.io.File logo = logoLoc.showOpenDialog(s);
        if (!(logo == null))
            logoLocation.setText(logo.getAbsolutePath());

    }

    public void saveChanges(ActionEvent ae)
    {
        configuration.getOrgDetails().setOrgEmail(orgEmail.getText());
        configuration.getOrgDetails().setOrgName(orgName.getText());
        configuration.getOrgDetails().setOrgNumber(orgNumber.getText());
        configuration.getOrgDetails().setOrgSignatory(orgSignatory.getText());

        configuration.getAddress().setOrgAddress(building.getText());
        configuration.getAddress().setOrgCity(city.getText());
        configuration.getAddress().setOrgState(state.getText());
        configuration.getAddress().setOrgPostalCode(postalCode.getText());

        configuration.getAppConfigurations().setDefaultLocation(defaultLocation.getText());

        //Copying new logo to org.data
        if(!logoLocation.getText().equals("")) {
            File.deleteFile("org.data\\logo.png");
            File.copyFile(logoLocation.getText(), "org.data\\logo.png");
        }

        // Writing changes to appConfig
        configuration.refresh();
        cancel(ae);
    }

    public void cancel(ActionEvent event)
    {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    public void isNumeric(KeyEvent ke)
    {
        TextField tf = (TextField) ke.getSource();
        if(Validator.isNumeric(tf.getText()))
        {
            save.setDisable(false);
            tf.getStyleClass().removeAll("input-error");
        }
        else
        {
            save.setDisable((true));
            tf.getStyleClass().add("input-error");
        }
    }

    public void validateEmail(KeyEvent ke)
    {
        TextField tf = (TextField) ke.getSource();
        if(Validator.isEmailValid(tf.getText()))
        {
            save.setDisable(false);
            tf.getStyleClass().removeAll("input-error");
        }
        else
        {
            save.setDisable((true));
            tf.getStyleClass().add("input-error");
        }
    }

    public void validateNumber(KeyEvent ke)
    {
        TextField tf = (TextField) ke.getSource();
        if(Validator.isPhoneNumberValid(tf.getText()))
        {
            save.setDisable(false);
            tf.getStyleClass().removeAll("input-error");
        }
        else
        {
            save.setDisable((true));
            tf.getStyleClass().add("input-error");
        }
    }
}

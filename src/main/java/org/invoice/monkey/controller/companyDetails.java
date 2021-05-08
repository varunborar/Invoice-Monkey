package org.invoice.monkey.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.invoice.monkey.App;

import java.io.*;

import org.invoice.monkey.utils.Validator;

import org.invoice.monkey.mainApp.MainApplication;
import org.json.simple.JSONObject;

public class companyDetails {
    // Company Details Form
    @FXML
    private TextField orgName;
    @FXML
    private TextField orgEmail;
    @FXML
    private TextField orgNumber;
    @FXML
    private TextField orgSignatory;
    @FXML
    private Button nextButton;

    // Address Details Form
    @FXML
    private TextField orgAdd;
    @FXML
    private TextField orgCity;
    @FXML
    private TextField orgState;
    @FXML
    private TextField orgPostalCode;
    @FXML
    private Button nextButtonAdd;

    // App Configurations
    @FXML
    private TextField folderPath;
    @FXML
    private TextField logoPath;
    @FXML
    private Button FinishButton;



    private static JSONObject orgDetails;
    private static JSONObject appConfig;
    private static JSONObject orgDetail;
    private static JSONObject address;
    private static JSONObject database;
    private static JSONObject email;


    static{
        orgDetails = new JSONObject();
        appConfig = new JSONObject();
        orgDetail = new JSONObject();
        address = new JSONObject();
        database = new JSONObject();
        email = new JSONObject();
    }


    public void validate()
    {
        if (!orgName.getText().equals("")  && !orgNumber.getText().equals("") && !orgEmail.getText().equals("") && !orgSignatory.getText().equals(""))
        {
            nextButton.setDisable(false);
        }
    }

    @SuppressWarnings("unchecked")
    public void nextButtonClicked() {
        try {
            // Retrieving Data
            String orgName = this.orgName.getText();
            String orgEmail = this.orgEmail.getText();
            String orgNumber = this.orgNumber.getText();
            String orgSignatory = this.orgSignatory.getText();

            // Validating Email and Number Fields
            // Pattern numPattern = Pattern.compile("[0-9]+");
            // Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$");

            boolean validData = true;

            if (!Validator.isEmailValid(orgEmail)) {
                validData = false;
                this.orgEmail.getStyleClass().add("text-input-error");
            }
            if (!Validator.isPhoneNumberValid(orgNumber)) {
                validData = false;
                this.orgNumber.getStyleClass().add("text-input-error");
            }

            if (validData) {
                this.orgEmail.getStyleClass().clear();
                this.orgEmail.getStyleClass().add("text-input");
                this.orgNumber.getStyleClass().clear();
                this.orgNumber.getStyleClass().add("text-input");


                orgDetail.put("Org-Name", orgName);
                orgDetail.put("Org-Email", orgEmail);
                orgDetail.put("Org-Number", orgNumber);
                orgDetail.put("Org-Signatory", orgSignatory);


                App.changeScene("addressForm.fxml", "Getting Started");

            }
        } catch (Exception e) {
            System.out.println(e.getClass().getName() + ": " + e.getCause());
        }
    }

    public void validateAddressForm()
    {
        if (!orgAdd.getText().equals("")  && !orgCity.getText().equals("") && !orgState.getText().equals("") && !orgPostalCode.getText().equals(""))
        {
            nextButtonAdd.setDisable(false);
        }
    }

    @SuppressWarnings("unchecked")
    public void nextButtonAddClicked()
    {
        try{
            String orgAdd = this.orgAdd.getText();
            String orgCity= this.orgCity.getText();
            String orgState = this.orgState.getText();
            String orgPostalCode = this.orgPostalCode.getText();


            address.put("Building", orgAdd);
            address.put("City", orgCity);
            address.put("State", orgState);
            address.put("Postal-Code", orgPostalCode);

            App.changeScene("appConfig.fxml", "Getting Started");

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void selectDefaultLocation()
    {
        DirectoryChooser folderLoc = new DirectoryChooser();
        folderLoc.setTitle("Choose Folder");
        File folder = folderLoc.showDialog(App.getstage());

        folderPath.setText(folder.getAbsolutePath());
    }

    public void selectLogoLoc()
    {
        FileChooser logoLoc = new FileChooser();
        logoLoc.setTitle("Choose Logo");
        logoLoc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files","*.png"));
        File logo = logoLoc.showOpenDialog(App.getstage());

        if(logo != null) {
            logoPath.setText(logo.getAbsolutePath());
            FinishButton.setDisable(false);
        }
    }

    @SuppressWarnings("unchecked")
    public void finishButtonClicked()
    {
        // App configuration
        appConfig.put("default-folder", folderPath.getText());
        appConfig.put("Logo", "org.data/logo.png");

        // Database details
        database.put("is-custom-database-set", false);
        database.put("local-database-path", "org.data/InvoiceMonkey.db");
        database.put("custom-database", false);

        // Email setup configurations
        email.put("is-email-service-ready", false);
        email.put("email", "none");


        orgDetails.put("Org-Details", orgDetail);
        orgDetails.put("Address", address);
        orgDetails.put("App-Configurations", appConfig);
        orgDetails.put("Database", database);
        orgDetails.put("Email", email);


//        System.out.println(orgDetails.toJSONString());

        try{
            File folder = new File("org.data");
            boolean result = folder.mkdir();
            FileWriter file = new FileWriter("org.data/appConfig.json");
            file.write(orgDetails.toJSONString());
            file.flush();

            //Copying logo to org.data
            try (InputStream logoOriginal = new FileInputStream(new File(logoPath.getText())); OutputStream logoCopy = new FileOutputStream(new File("org.data/logo.png"))) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = logoOriginal.read(buffer)) > 0) {
                    logoCopy.write(buffer, 0, length);
                }
            }

            // Initializing configuration for the application
            App.getstage().close();
            MainApplication app = new MainApplication();

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}

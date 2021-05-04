package org.invoice.monkey.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.invoice.monkey.App;

import java.util.Objects;


public class companyDetails {
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

    public void validate()
    {
        if (!orgName.getText().equals("")  && !orgNumber.getText().equals("") && !orgEmail.getText().equals("") && !orgSignatory.getText().equals(""))
        {
            nextButton.setDisable(false);
        }
    }

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

            if (!orgEmail.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")) {
                validData = false;
                this.orgEmail.getStyleClass().add("text-input-error");
            }
            if (!orgNumber.matches("[0-9]+")) {
                validData = false;
                this.orgNumber.getStyleClass().add("text-input-error");
            }

            if (validData) {
                this.orgEmail.getStyleClass().clear();
                this.orgEmail.getStyleClass().add("text-input");
                this.orgNumber.getStyleClass().clear();
                this.orgNumber.getStyleClass().add("text-input");

                Parent companyDetails = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("src\\main\\resources\\org\\invoice\\monkey\\addressForm.fxml")));
                App.changeScene(companyDetails, "Getting Started");

            }
        } catch (Exception e) {
            System.out.println(e.getClass().getName() + ": " + e.getCause());
        }
    }
}

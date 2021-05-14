package org.invoice.monkey.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.invoice.monkey.model.Configurations.Configuration;

public class SetupDatabase {


    @FXML
    private ToggleGroup Database;
    @FXML
    private RadioButton localDatabase;
    @FXML
    private RadioButton customDatabase;
    @FXML
    private Label connectString;

    @FXML
    private TextField host;
    @FXML
    private TextField port;
    @FXML
    private TextField databaseName;
    @FXML
    private TextField userName;
    @FXML
    private TextField password;

    @FXML
    private Button connect;
    @FXML
    private Button save;
    @FXML
    private Button cancel;


    private Configuration configuration;

    @FXML
    public void initialize()
    {
        configuration = new Configuration();
        if(configuration.getDatabaseDetails().isCustomDatabaseSet())
        {
            Database.selectToggle(customDatabase);
            databaseName.setText(configuration.getDatabaseDetails().getDatabaseName());
            host.setText(configuration.getDatabaseDetails().getHost());
            port.setText(configuration.getDatabaseDetails().getPort());
            userName.setText(configuration.getDatabaseDetails().getUserName());
            password.setText(configuration.getDatabaseDetails().getPassword());

            connectString.setText(configuration.getDatabaseDetails().getFormattedURL());
        }
        else
        {
            Database.selectToggle(localDatabase);
            connect.setVisible(false);
        }
        save.setDisable(true);
    }

    public void chooseLocalDatabase()
    {
        databaseName.setText("");
        databaseName.setDisable(true);
        host.setText("");
        host.setDisable(true);
        userName.setText("");
        userName.setDisable(true);
        password.setText("");
        password.setDisable(true);
        port.setText("");
        port.setDisable(true);

        save.setDisable(false);
        connect.setVisible(false);

        connectString.setText("");

        configuration.getDatabaseDetails().setCustomDatabase(false);
        configuration.refresh();
    }

    public void chooseCustomDatabase()
    {
        databaseName.setDisable(false);
        host.setDisable(false);
        port.setDisable(false);
        userName.setDisable(false);
        password.setDisable(false);

        connect.setVisible(true);
        save.setDisable(false);
    }

}

package org.invoice.monkey.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.invoice.monkey.Database.database;
import org.invoice.monkey.model.Configurations.Configuration;
import org.invoice.monkey.utils.UIExceptions.DatabaseConnectionException;
import org.invoice.monkey.utils.Validator;

import java.sql.SQLException;

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
    private PasswordField password;

    @FXML
    private Button save;
    @FXML
    private Button cancel;
    @FXML
    private Button connect;


    private Configuration configuration;
    private Configuration newConfig;

    @FXML
    public void initialize()
    {
        configuration = new Configuration();
        if(configuration.getDatabaseDetails().isCustomDatabaseSet())
        {
            databaseName.setDisable(false);
            host.setDisable(false);
            port.setDisable(false);
            userName.setDisable(false);
            password.setDisable(false);

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
        connect.setDisable(true);
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
        connect.setVisible(true);
        connect.setDisable(false);

        connectString.setText("");
    }

    public void chooseCustomDatabase()
    {
        databaseName.setDisable(false);
        host.setDisable(false);
        port.setDisable(false);
        userName.setDisable(false);
        password.setDisable(false);
        connect.setVisible(true);

        save.setDisable(true);
    }

    public void saveSettings(ActionEvent event)
    {
            newConfig.refresh();

            //Setting up database
            database db = new database();
            db.configureAllTables();
            cancel(event);

    }

    public void connect()
    {
        newConfig = new Configuration();
        if (Database.getSelectedToggle() == customDatabase) {
            newConfig.getDatabaseDetails().setCustomDatabase(
                    host.getText(),
                    Long.parseLong(port.getText()),
                    databaseName.getText(),
                    userName.getText(),
                    password.getText()
            );

            newConfig.getDatabaseDetails().setCustomDatabase(true);
        } else {
            newConfig.getDatabaseDetails().setCustomDatabase(false);
        }

        try{
            database.checkConnection(newConfig);
            save.setDisable(false);

            connectString.setText(connectString.getText() +
                    newConfig.getDatabaseDetails().getFormattedURL() +
                    ": Connected");
        }catch(DatabaseConnectionException d)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, d.getMessage(),ButtonType.OK ,ButtonType.CLOSE);
            alert.showAndWait();

            if(alert.getResult() == ButtonType.CLOSE)
            {
                initialize();
            }
        }

    }

    public void cancel(ActionEvent event)
    {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    public void validateNonEmpty(KeyEvent ke)
    {
        Node node = (Node) ke.getSource();
        TextField tf = (TextField) node;
        if(Validator.isNonEmpty(tf.getText()) && Database.getSelectedToggle() == customDatabase)
        {
            connect.setDisable(false);
            tf.getStyleClass().removeAll("text-field-error");
        }
        else if(Validator.isNonEmpty(tf.getText()) && Database.getSelectedToggle() == localDatabase)
        {
            connect.setVisible(false);
            save.setDisable(false);
            tf.getStyleClass().removeAll("text-field-error");
        }
        else
        {
            connect.setDisable(true);
            save.setDisable(true);
            tf.getStyleClass().add("text-field-error");
        }
    }

}

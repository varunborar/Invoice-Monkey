package org.invoice.monkey.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.invoice.monkey.Database.database;
import org.invoice.monkey.model.Configurations.Configuration;
import org.invoice.monkey.utils.Validator;

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

        connectString.setText("");
    }

    public void chooseCustomDatabase()
    {
        databaseName.setDisable(false);
        host.setDisable(false);
        port.setDisable(false);
        userName.setDisable(false);
        password.setDisable(false);

        save.setDisable(false);
    }

    public void saveSettings(ActionEvent event)
    {
        if(Database.getSelectedToggle() == customDatabase)
        {
            configuration.getDatabaseDetails().setCustomDatabase(
                    host.getText(),
                    Long.parseLong(port.getText()),
                    databaseName.getText(),
                    userName.getText(),
                    password.getText()
            );

            configuration.getDatabaseDetails().setCustomDatabase(true);
        }
        else{
            configuration.getDatabaseDetails().setCustomDatabase(false);
        }

        configuration.refresh();

        //Setting up database
        database db = new database();
        db.configureAllTables();
        cancel(event);
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
        if(Validator.isNonEmpty(tf.getText()))
        {
            save.setDisable(false);
            tf.getStyleClass().removeAll("input-error");
        }
        else
        {
            save.setDisable(true);
            tf.getStyleClass().add("input-error");
        }
    }

}

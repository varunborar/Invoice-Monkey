package org.invoice.monkey.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.invoice.monkey.App;
import org.invoice.monkey.Database.database;
import org.invoice.monkey.utils.UIExceptions.DatabaseConnectionException;


import java.util.Objects;


public class homeScreen {

    // Home Screen
    @FXML
    private AnchorPane homeScreenArea;

    //Text Labels
    @FXML
    private TextFlow labelPanel;
    @FXML
    private Label labelA;
    @FXML
    private Label labelB;
    @FXML
    private Label labelC;
    @FXML
    private Label labelD;


    public void initialize() {
        try {
            AnchorPane test = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("defaultWidgetScreen.fxml")));
            setHomeScreenArea(test);

            labelA.setText("");
            labelB.setText("");
            labelC.setText("");
            labelD.setText("");

            database.checkConnection(App.getConfiguration());
        }catch(DatabaseConnectionException e)
        {
            labelA.setText("Database connection failed: " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println(e.getClass().getName() + ": " + e.getCause() + ", " + e.getMessage());
        }
    }

    public void setHomeScreenArea(Pane pane) {
        homeScreenArea.getChildren().clear();
        homeScreenArea.getChildren().add(pane);
    }

    public void openItemScreen(){
        try{
            AnchorPane itemScreen = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("itemScreen.fxml")));
            setHomeScreenArea(itemScreen);
        }catch(Exception e)
        {
            System.out.println(e.getClass().getName() + ": " + e.getCause() + ", " + e.getMessage());
        }
    }

    public void openCustomerScreen()
    {
        try{
            AnchorPane itemScreen = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("customerScreen.fxml")));
            setHomeScreenArea(itemScreen);
        }catch(Exception e)
        {
            System.out.println(e.getClass().getName() + ": " + e.getCause() + ", " + e.getMessage());
        }
    }


    public void openEditOrgDetails()
    {
        try{
            Stage secondaryStage = new Stage();

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("editCompanyDetails.fxml")));
            Scene scene = new Scene(root);
            secondaryStage.setTitle("Edit Organization Details");
            secondaryStage.setScene(scene);
            secondaryStage.setResizable(false);
            secondaryStage.show();

        }catch(Exception e)
        {
            System.out.println("Exception in home-screen:" + e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public void openSetupDatabase()
    {
        try {
            Stage secondaryStage = new Stage();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("setupDatabase.fxml")));
            Scene scene = new Scene(root);
            secondaryStage.setTitle("Setup Database");
            secondaryStage.setResizable(false);
            secondaryStage.setScene(scene);
            secondaryStage.show();

        }catch(Exception e)
        {
            System.out.println("Exception in home-screen:" + e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public void setLabelA(String labelText)
    {
        labelA.setText(labelText);
    }

    public void setLabelB(String labelText)
    {
        labelB.setText(labelText);
    }

    public void setLabelC(String labelText)
    {
        labelC.setText(labelText);
    }

    public void setLabelD(String labelText)
    {
        labelD.setText(labelText);
    }

}

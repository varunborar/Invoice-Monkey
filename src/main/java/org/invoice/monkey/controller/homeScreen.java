package org.invoice.monkey.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;


public class homeScreen {

    // Home Screen
    @FXML
    private  AnchorPane homeScreenArea;

    //Text Labels
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private Label label4;


    public void initialize()
    {
        try{
            AnchorPane test = FXMLLoader.load(getClass().getResource("test.fxml"));
            setHomeScreenArea(test);
        }catch(Exception e)
        {
            System.out.println(e.getClass().getName() + ": " + e.getCause() + ", " + e.getMessage());
        }
    }

    public void setHomeScreenArea(Pane pane)
    {
        homeScreenArea.getChildren().clear();
        homeScreenArea.getChildren().add(pane);
    }
}

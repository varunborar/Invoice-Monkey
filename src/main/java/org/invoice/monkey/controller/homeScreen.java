package org.invoice.monkey.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextFlow;


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

        } catch (Exception e) {
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

package org.invoice.monkey.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.invoice.monkey.App;
import org.invoice.monkey.Database.database;
import org.invoice.monkey.model.Configurations.Configuration;
import org.invoice.monkey.utils.Animation;
import org.invoice.monkey.utils.UIExceptions.DatabaseConnectionException;


import java.util.Objects;


public class homeScreen {

    private Configuration configuration;

    @FXML
    private AnchorPane workSpace;
    @FXML
    private AnchorPane container;
    @FXML
    private AnchorPane menuBar;

    @FXML
    private Button homeButton;
    @FXML
    private Button databaseButton;
    @FXML
    private Button orderButton;
    @FXML
    private Button analyticsButton;
    @FXML
    private Button accountButton;
    @FXML
    private Button settingsButton;

    @FXML
    private StackPane optionStack;
    @FXML
    private AnchorPane MenuContainer;
    @FXML
    private AnchorPane dataPane;
    @FXML
    private AnchorPane accountPane;
    @FXML
    private AnchorPane settingsPane;
    @FXML
    private AnchorPane orderPane;
    @FXML
    private AnchorPane analyticsPane;

    private static homeScreen HomeScreen;
    private static Double workSpaceAreaHeight;
    private static Double workSpaceAreaWidth;

    private Boolean isOptionStackVisible;

    public static void setWorkSpaceArea(String fxml)
    {

        try{
            AnchorPane test = FXMLLoader.load(Objects.requireNonNull(homeScreen.class.getResource(fxml)));
            HomeScreen.setWorkSpace(test);
        }catch(Exception e)
        {
            System.out.println(e.getClass().getName() + ": " + e.getMessage() + "(" + e.getCause() + ")");
        }
    }


    @FXML
    public void initialize() {

        isOptionStackVisible = true;
        configuration = new Configuration();
        HomeScreen = this;

        Screen primaryScreen = Screen.getPrimary();

        menuBar.setMinWidth(50);
        menuBar.setMaxWidth(50);

        optionStack.setMinWidth(260);
        optionStack.setMaxWidth(260);

        MenuContainer.setMinWidth(310);
        MenuContainer.setMaxWidth(310);

        container.setMinWidth(primaryScreen.getVisualBounds().getWidth());
        container.setMaxWidth(primaryScreen.getVisualBounds().getWidth());

        workSpace.setMinWidth(772);

        try {
            AnchorPane test = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("defaultWidgetScreen.fxml")));
            setWorkSpace(test);

            database.checkConnection(App.getConfiguration());
        }catch(DatabaseConnectionException e)
        {

        }
        catch (Exception e) {
            System.out.println(e.getClass().getName() + ": " + e.getCause() + ", " + e.getMessage());
        }
    }

    public void setWorkSpace(Parent pane) {
        resizeWorkSpaceArea();
        AnchorPane.setRightAnchor(pane, 0d);
        AnchorPane.setLeftAnchor(pane, 0d);
        AnchorPane.setTopAnchor(pane, 0d);
        AnchorPane.setBottomAnchor(pane, 0d);
        workSpace.getChildren().clear();
        workSpace.getChildren().add(pane);
    }

    public void resizeWorkSpaceArea()
    {
        double width = container.getWidth() - MenuContainer.getMaxWidth();
        workSpace.setMinWidth(width);

        workSpaceAreaHeight = container.getHeight();
        workSpaceAreaWidth = width;
    }

    public void menuButtonClicked(ActionEvent ae)
    {
        if(!isOptionStackVisible)
            showOptionsPane();

        databaseButton.getStyleClass().removeAll("menu-button-selected");
        orderButton.getStyleClass().removeAll("menu-button-selected");
        settingsButton.getStyleClass().removeAll("menu-button-selected");
        analyticsButton.getStyleClass().removeAll("menu-button-selected");
        accountButton.getStyleClass().removeAll("menu-button-selected");
        homeButton.getStyleClass().removeAll("menu-button-selected");

        Button source = (Button) ae.getSource();
        source.getStyleClass().add("menu-button-selected");
    }

    public void homeButton(ActionEvent event)
    {
        try{
            AnchorPane test = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("defaultWidgetScreen.fxml")));
            setWorkSpace(test);
        }
        catch(Exception e)
        {
            System.out.println(e.getClass().getName() + ": " + e.getMessage() + "(" + e.getCause() + ")");
        }
    }

    public void showDataPane(ActionEvent event)
    {
        menuButtonClicked(event);
        Animation animation = new Animation();
        animation.slideInLeftAnimation(optionStack, dataPane);
    }

    public void showOrderPane(ActionEvent event)
    {
        menuButtonClicked(event);
        Animation animation = new Animation();
        animation.slideInLeftAnimation(optionStack, orderPane);
    }

    public void showAnalyticsPane(ActionEvent event)
    {
        menuButtonClicked(event);
        Animation animation = new Animation();
        animation.slideInLeftAnimation(optionStack, analyticsPane);
    }

    public void showAccountPane(ActionEvent event)
    {
        menuButtonClicked(event);
        Animation animation = new Animation();
        animation.slideInLeftAnimation(optionStack, accountPane);
    }

    public void showSettingsPane(ActionEvent event)
    {
        menuButtonClicked(event);
        Animation animation = new Animation();
        animation.slideInLeftAnimation(optionStack, settingsPane);
    }

    public void minimizeOptionsPane()
    {
        optionStack.setVisible(false);
        MenuContainer.setMinWidth(menuBar.getWidth());
        MenuContainer.setMaxWidth(menuBar.getWidth());
        isOptionStackVisible = false;
    }

    public void showOptionsPane()
    {
        MenuContainer.setMinWidth(menuBar.getWidth());
        MenuContainer.setMaxWidth(menuBar.getWidth() + optionStack.getWidth());
        resizeWorkSpaceArea();
        Animation animation = new Animation();
        animation.menuBarOpenAnimation(MenuContainer, optionStack);

        isOptionStackVisible = true;
    }

    public void openItemScreen(){
        try{
            minimizeOptionsPane();
            AnchorPane itemScreen = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("itemScreen.fxml")));
            setWorkSpace(itemScreen);
        }catch(Exception e)
        {
            System.out.println(e.getClass().getName() + ": " + e.getCause() + ", " + e.getMessage());
        }
    }

    public void openCustomerScreen()
    {
        try{
            minimizeOptionsPane();
            AnchorPane customerScreen = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("customerScreen.fxml")));
            setWorkSpace(customerScreen);
        }catch(Exception e)
        {
            System.out.println(e.getClass().getName() + ": " + e.getCause() + ", " + e.getMessage());
        }
    }


    public void openEditOrgDetails()
    {
        try{
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("editCompanyDetails.fxml")));
            setWorkSpace(root);
        }catch(Exception e)
        {
            System.out.println("Exception in home-screen:" + e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public void openSetupDatabase()
    {
        try {
            Stage secondaryStage = new Stage();
            secondaryStage.initModality(Modality.APPLICATION_MODAL);
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


}

package org.invoice.monkey;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.invoice.monkey.mainApp.MainApplication;
import org.invoice.monkey.model.Configurations.Configuration;

import java.io.File;
import java.util.Objects;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Stage mainStage;
    private static Configuration configuration;


    @Override
    public void start(Stage stage){
        try {
            mainStage = stage;
            File appConfig = new File("org.data/appConfig.json");
            if(appConfig.exists())
            {
                System.out.println("File Exists");
                configuration = new Configuration();


                MainApplication app = new MainApplication();
            }
            else {
                Parent companyDetails = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("companyDetails.fxml")));
                scene = new Scene(companyDetails);
                stage.setTitle("Getting Started");
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();
            }
        }catch(Exception e)
        {
            System.out.println("App" + e.getClass().getName() +": " + e.getCause());
            e.printStackTrace();
        }
    }

    public static void changeScene(String fxml, String title)
    {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(org.invoice.monkey.App.class.getResource(fxml)));
            scene = new Scene(root);
            mainStage.setTitle(title);
            mainStage.setResizable(false);
            mainStage.setScene(scene);
//            mainStage.show();
        }catch(Exception e)
        {
            System.out.println(e.getClass().getName() + ": " + e.getCause());
            //e.printStackTrace();
        }
    }

    public static Stage getstage()
    {
        return mainStage;
    }

    public static Configuration getConfiguration()
    {
        return configuration;
    }

    public static void refreshConfiguration(){
        configuration = new Configuration();
    }

    public static void main(String[] args) {
        launch();
    }

}
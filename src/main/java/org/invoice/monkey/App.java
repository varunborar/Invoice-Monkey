package org.invoice.monkey;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.util.Objects;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Stage mainStage;

    @Override
    public void start(Stage stage){
        try {
            mainStage = stage;
            File appConfig = new File("Invoice-Monkey\\org.data\\app.json");
            if(appConfig.exists())
            {
                System.out.println("File Exists");
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
            System.out.println(e.getClass().getName() +": " + e.getCause());
        }
    }

    public static void changeScene(Parent root, String title)
    {
        scene = new Scene(root);
        mainStage.setTitle(title);
        mainStage.setResizable(false);
        mainStage.setScene(scene);
        mainStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
package org.invoice.monkey.mainApp;

import javafx.scene.image.Image;
import org.invoice.monkey.App;

import java.util.Objects;


public class MainApplication {

    public MainApplication()
    {
        try {
            App.changeScene("homeScreen.fxml", "Invoice Monkey");
            App.getstage().setResizable(false);
            App.getstage().setMaximized(true);
            App.getstage().show();
        }
        catch(Exception e)
        {
            System.out.println(e.getClass().getName() + ": " + e.getCause() + ", " + e.getMessage());
        }
    }


}

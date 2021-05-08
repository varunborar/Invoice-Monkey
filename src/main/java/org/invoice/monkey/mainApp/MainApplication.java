package org.invoice.monkey.mainApp;

import org.invoice.monkey.App;



public class MainApplication {

    public MainApplication()
    {
        App.changeScene("homeScreen.fxml", "Invoice Monkey");
        App.getstage().setResizable(false);
        App.getstage().setMaximized(true);
        App.getstage().show();
    }


}

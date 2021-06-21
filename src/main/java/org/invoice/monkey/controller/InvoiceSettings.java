package org.invoice.monkey.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.invoice.monkey.App;
import org.invoice.monkey.utils.invoicegenerator.background.BackgroundType;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

public class InvoiceSettings {

    @FXML
    private HBox backgroundCarouselContainer;

    public void initialize() throws FileNotFoundException {
        BackgroundType selectedBackgroundType = App.getConfiguration().getInvoiceDetails().getBackground();
        BackgroundType[] backgrounds = BackgroundType.values();

        for(BackgroundType b: backgrounds)
        {
            InputStream stream = new FileInputStream(b.getURL());
            Image image = new Image(stream);
            boolean checked;
            if(b == selectedBackgroundType)
                checked = true;
            else
                checked = false;

            AnchorPane item = getBackgroundCarousel(image, checked);
            backgroundCarouselContainer.getChildren().add(item);
        }




    }

    public AnchorPane getBackgroundCarousel(Image image, Boolean checked)
    {
        try{

            FXMLLoader loader = new FXMLLoader(getClass().getResource("carouselItem.fxml"));
            loader.setController(new CarouselItem());
            return loader.load();
        }catch(Exception e)
        {
            System.out.println(e.getClass().getName() + ": " + e.getMessage() + "during: Add Expense Item");
        }
        return null;
    }
}

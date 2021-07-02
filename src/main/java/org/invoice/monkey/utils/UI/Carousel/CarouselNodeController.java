package org.invoice.monkey.utils.UI.Carousel;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class CarouselNodeController implements Initializable {


    @FXML
    private ImageView ImageContainer;
    @FXML
    private JFXRadioButton CheckBox;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        ImageContainer.setPreserveRatio(true);
    }

    public ImageView getImageContainer()
    {
        return this.ImageContainer;
    }

    public JFXRadioButton getCheckBox()
    {
        return this.CheckBox;
    }


}


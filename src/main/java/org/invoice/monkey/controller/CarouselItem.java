package org.invoice.monkey.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CarouselItem {

    @FXML
    private ImageView imageView;
    @FXML
    private CheckBox checked;

    public void setImageView(Image image)
    {
        imageView.setImage(image);
    }

    public void setChecked(Boolean checked)
    {
        this.checked.selectedProperty().set(checked);
    }

    public Boolean isChecked()
    {
        return checked.isSelected();
    }
}

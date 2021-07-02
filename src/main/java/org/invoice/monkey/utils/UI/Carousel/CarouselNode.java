package org.invoice.monkey.utils.UI.Carousel;


import javafx.beans.property.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CarouselNode extends AnchorPane {


    private CarouselNodeController controller;

    private final ObjectProperty<Image> imageProperty = new ObjectPropertyBase<Image>() {
        @Override
        public Object getBean() {
            return null;
        }

        @Override
        public String getName() {
            return null;
        }
    };

    private final BooleanProperty selectedProperty = new BooleanPropertyBase() {
        @Override
        public Object getBean() {
            return null;
        }

        @Override
        public String getName() {
            return null;
        }
    };

    public CarouselNode()
    {
        super();

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CarouselNode.fxml"));
            controller = new CarouselNodeController();
            loader.setController(controller);
            Node n = loader.load();

            this.getChildren().add(n);

        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }


    public boolean isSelectedProperty() {
        return selectedProperty.get();
    }

    public void setSelectedProperty(boolean selectedProperty) {
        this.selectedProperty.set(selectedProperty);
        controller.getCheckBox().selectedProperty().bindBidirectional(this.selectedProperty);
    }

    public BooleanProperty getSelectedProperty()
    {
        return this.selectedProperty;
    }

    public void setImageProperty(Image imageProperty) {
        this.imageProperty.set(imageProperty);
        controller.getImageContainer().imageProperty().bind(this.imageProperty);
    }

    public ObjectProperty<Image> getImageProperty()
    {
        return this.imageProperty;
    }

    public CarouselNodeController getController(){
        return this.controller;
    }
}

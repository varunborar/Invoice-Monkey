package org.invoice.monkey.controller;


import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.invoice.monkey.App;
import org.invoice.monkey.utils.CurrencyType;
import org.invoice.monkey.utils.UI.Carousel.CarouselNode;
import org.invoice.monkey.utils.invoicegenerator.background.BackgroundType;
import org.invoice.monkey.utils.invoicegenerator.templates.TemplateType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class InvoiceSettings {

    @FXML
    private VBox SettingsContainer;
    @FXML
    private HBox background;
    @FXML
    private HBox template;
    @FXML
    private JFXComboBox<CurrencyType> currencyType;



    @FXML
    private final ToggleGroup Background = new ToggleGroup();
    @FXML
    private final ToggleGroup Template = new ToggleGroup();

    public void initialize()
    {
        BackgroundType[] backgrounds = BackgroundType.values();
        BackgroundType selectedType = App.getConfiguration().getInvoiceDetails().getBackground();

        for(BackgroundType backgroundType: backgrounds)
        {
            CarouselNode node = new CarouselNode();
            node.setImageProperty(new Image(backgroundType.getURL()));
            node.setSelectedProperty(backgroundType == selectedType);
            node.getController().getCheckBox().toggleGroupProperty().set(Background);

            background.getChildren().add(node);
        }

        Background.selectedToggleProperty().addListener((observableValue, oldValue, newValue)-> {

            JFXRadioButton selected = (JFXRadioButton) Background.getSelectedToggle();
            VBox selectedNode = (VBox) selected.getParent();
            ImageView imageContainer = (ImageView) selectedNode.getChildren().get(0);
            BackgroundType bgt = BackgroundType.getByURL(imageContainer.getImage().getUrl());
            App.getConfiguration().getInvoiceDetails().setBackground(bgt);
            App.getConfiguration().refresh();
        });

        TemplateType selectedTemplateType = App.getConfiguration().getInvoiceDetails().getTemplate();

        for(TemplateType templateType: TemplateType.values())
        {
            CarouselNode node = new CarouselNode();
            node.setImageProperty(new Image(templateType.getURL()));
            node.setSelectedProperty(templateType == selectedTemplateType);
            node.getController().getCheckBox().toggleGroupProperty().set(Template);

            template.getChildren().add(node);
        }

        Template.selectedToggleProperty().addListener((observableValue, oldValue, newValue)-> {

            JFXRadioButton selected = (JFXRadioButton) Template.getSelectedToggle();
            VBox selectedNode = (VBox) selected.getParent();
            ImageView imageContainer = (ImageView) selectedNode.getChildren().get(0);
            TemplateType bgt = TemplateType.getByURL(imageContainer.getImage().getUrl());
            App.getConfiguration().getInvoiceDetails().setTemplate(bgt);
            App.getConfiguration().refresh();
        });

        CurrencyType selectedCurr = App.getConfiguration().getInvoiceDetails().getCurrency();


        currencyType.getItems().addAll(CurrencyType.values());
        currencyType.setValue(selectedCurr);

        currencyType.valueProperty().addListener((observable, oldValue, newValue)->{
            App.getConfiguration().getInvoiceDetails().setCurrency(newValue);
            App.getConfiguration().refresh();
        });
    }




}



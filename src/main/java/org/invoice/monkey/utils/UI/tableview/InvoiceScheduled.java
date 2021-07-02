package org.invoice.monkey.utils.UI.tableview;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import org.invoice.monkey.Database.InvoiceDB;
import org.invoice.monkey.controller.Home;
import org.invoice.monkey.model.Invoice;
import org.invoice.monkey.utils.notification.ErrorNotification;
import org.invoice.monkey.utils.notification.Notification;

import java.sql.SQLException;

public class InvoiceScheduled {

    SimpleLongProperty Identifier;
    SimpleStringProperty CustomerName;
    SimpleStringProperty Date;
    SimpleObjectProperty<JFXButton> View;
    SimpleObjectProperty<JFXButton> Delivered;

    public InvoiceScheduled(Invoice invoice)
    {
        this.Identifier = new SimpleLongProperty(invoice.getRawInvoiceID());
        this.CustomerName = new SimpleStringProperty(invoice.getCustomer().getName());
        this.Date = new SimpleStringProperty(invoice.getDate().toString());

        Label icon = new Label();
        icon.setMinHeight(12);
        icon.setMinWidth(16);
        icon.getStyleClass().addAll("tick");

        this.Delivered = new SimpleObjectProperty<>(new JFXButton("", icon));

        Label icon2 = new Label();
        icon2.setMinHeight(4);
        icon2.setMinWidth(18);
        icon2.getStyleClass().addAll("eye");

        this.View = new SimpleObjectProperty<>(new JFXButton("", icon2));

        this.Delivered.get().setOnAction(e->{
            InvoiceDB idb = new InvoiceDB();
            try {
                idb.updateInvoiceSchedule(this.getIdentifier());
                Home.refresh();
            } catch (SQLException throwable) {
                Notification.sendErrorNotification(ErrorNotification.DatabaseConnectionError);
            }
        });

    }

    public long getIdentifier() {
        return Identifier.get();
    }

    public SimpleLongProperty identifierProperty() {
        return Identifier;
    }

    public String getCustomerName() {
        return CustomerName.get();
    }

    public SimpleStringProperty customerNameProperty() {
        return CustomerName;
    }

    public String getDate() {
        return Date.get();
    }

    public SimpleStringProperty dateProperty() {
        return Date;
    }

    public JFXButton getView() {
        return View.get();
    }

    public SimpleObjectProperty<JFXButton> viewProperty() {
        return View;
    }

    public JFXButton getDelivered() {
        return Delivered.get();
    }

    public SimpleObjectProperty<JFXButton> deliveredProperty() {
        return Delivered;
    }
}

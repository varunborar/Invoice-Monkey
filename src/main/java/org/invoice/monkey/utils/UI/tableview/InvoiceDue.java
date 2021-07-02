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

public class InvoiceDue{

        private SimpleLongProperty Identifier;
        private SimpleStringProperty Description;
        private SimpleStringProperty Amount;
        private SimpleObjectProperty<JFXButton> Button;
        private SimpleObjectProperty<JFXButton> View;

        public InvoiceDue(Invoice invoice)
        {
                this.Identifier = new SimpleLongProperty(invoice.getRawInvoiceID());
                this.Description = new SimpleStringProperty(invoice.getCustomer().getName());
                this.Amount = new SimpleStringProperty(String.format("%.2f", invoice.getTotal()));

                Label icon = new Label();
                icon.setMinWidth(16);
                icon.setMinHeight(12);
                icon.getStyleClass().addAll("tick");

                this.Button = new SimpleObjectProperty<>(new JFXButton("", icon));

                Label icon2 = new Label();
                icon2.setMinHeight(4);
                icon2.setMinWidth(18);
                icon2.getStyleClass().addAll("eye");

                this.View = new SimpleObjectProperty<>(new JFXButton("", icon2));

                this.Button.get().setOnAction(e->{
                        InvoiceDB idb = new InvoiceDB();
                        try {
                                idb.updateInvoiceDue(this.getIdentifier());
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

        public String getDescription() {
                return Description.get();
        }

        public SimpleStringProperty descriptionProperty() {
                return Description;
        }

        public String getAmount() {
                return Amount.get();
        }

        public SimpleStringProperty amountProperty() {
                return Amount;
        }

        public JFXButton getButton() {
                return Button.get();
        }

        public SimpleObjectProperty<JFXButton> buttonProperty() {
                return Button;
        }

        public JFXButton getView() {
                return View.get();
        }

        public SimpleObjectProperty<JFXButton> viewProperty() {
                return View;
        }

}
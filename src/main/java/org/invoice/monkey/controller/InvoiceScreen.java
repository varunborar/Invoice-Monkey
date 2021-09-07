package org.invoice.monkey.controller;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.invoice.monkey.App;
import org.invoice.monkey.Database.CustomerDB;
import org.invoice.monkey.Database.InvoiceDB;
import org.invoice.monkey.Database.ItemDB;
import org.invoice.monkey.model.Customer;
import org.invoice.monkey.model.Invoice;
import org.invoice.monkey.model.InvoiceItem;

import org.controlsfx.control.textfield.TextFields;
import org.invoice.monkey.model.Item;
import org.invoice.monkey.utils.invoicegenerator.InvoiceGenerator;
import org.invoice.monkey.utils.invoicegenerator.InvoiceGeneratorThread;

import java.sql.Date;
import java.sql.Time;
import java.util.Objects;
import java.util.Vector;


public class InvoiceScreen {

    private Invoice invoice;

    private InvoiceItem selectedItem;

    @FXML
    private JFXDatePicker date;
    @FXML
    private JFXTimePicker time;

    @FXML
    private JFXTextField customer;
    @FXML
    private JFXComboBox<String> due;
    @FXML
    private JFXComboBox<String> type;

    @FXML
    private JFXTextField itemDescription;
    @FXML
    private JFXTextField quantity;
    @FXML
    private JFXTextField rate;
    @FXML
    private JFXButton addItem;

    @FXML
    private JFXTextField discount;
    @FXML
    private JFXComboBox<String> discountType;

    @FXML
    private Label SubTotal;
    @FXML
    private Label Discount;
    @FXML
    private Label Total;


    @FXML
    private JFXTextArea notes;

    @FXML
    private JFXCheckBox sendEmail;

    @FXML
    private TableView<InvoiceItem> invoiceItemTable;
    @FXML
    private TableColumn<InvoiceItem, String> ITEM_DESCRIPTION;
    @FXML
    private TableColumn<InvoiceItem, Float> RATE;
    @FXML
    private TableColumn<InvoiceItem, Integer> QUANTITY;
    @FXML
    private TableColumn<InvoiceItem, Float> AMOUNT;


    @FXML
    public void initialize()
    {
        if(App.getConfiguration().getEmailDetails().isEmailServiceReady())
            sendEmail.setVisible(true);

        addItem.setDisable(true);

        invoice = new Invoice();

        invoiceItemTable.setPlaceholder(new Label(""));

        discountType.getItems().addAll("%", "INR");
        discountType.setValue("%");

        due.getItems().addAll("PAID", "DUE");

        type.getItems().addAll("Scheduled", "Receipt", "Credit Note");

        time.setValue(java.time.LocalTime.now());

        date.setValue(java.time.LocalDate.now());

        ITEM_DESCRIPTION.setCellValueFactory(new PropertyValueFactory<>("description"));
        RATE.setCellValueFactory(new PropertyValueFactory<>("price"));
        QUANTITY.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        AMOUNT.setCellValueFactory(new PropertyValueFactory<>("total"));


        CustomerDB cdb = new CustomerDB();
        Vector<Customer> customerList = cdb.getAllCustomers(-1);
        AutoCompletionBinding<Customer> customerBinding = TextFields.bindAutoCompletion(customer, customerList);
        customerBinding.setOnAutoCompleted(event -> invoice.setCustomer(event.getCompletion()));


        ItemDB idb = new ItemDB();
        Vector<Item> itemList = idb.getAllItems(-1);
        AutoCompletionBinding<Item> acb = TextFields.bindAutoCompletion(itemDescription, itemList);
        acb.setOnAutoCompleted(event -> {

            selectedItem = new InvoiceItem(event.getCompletion());
            rate.setText(selectedItem.getPrice().toString());
            quantity.setText(selectedItem.getQuantity().toString());
            addItem.setDisable(false);

        });


    }

    public void addItem()
    {

        selectedItem.setQuantity(Integer.parseInt(quantity.getText()));
        itemDescription.setText("");
        rate.setText("");
        quantity.setText("");
        addItem.setDisable(true);

        invoice.addItem(selectedItem);
        invoiceItemTable.getItems().add(selectedItem);

        SubTotal.setText(invoice.getSubTotal().toString());

    }

    public void addDiscount()
    {
        try {
            if (discountType.getValue().equals("%")) {
                float discount = invoice.getSubTotal() * Float.parseFloat(this.discount.getText()) * 0.01f;
                invoice.setDiscount(discount);
                Discount.setText(String.format("%f", discount));
            } else {
                invoice.setDiscount(Float.parseFloat(this.discount.getText()));
                Discount.setText(this.discount.getText());
            }
            invoice.calTotal();
            Total.setText(invoice.getTotal().toString());
        }catch(Exception e)
        {
            System.out.println(e.getClass().getName() + ": " + e.getMessage() + "(" + e.getCause() + ")");
        }
    }

    public void generateInvoice(ActionEvent event)
    {
        if (discount.getText().equals("")) {
            discount.setText("0.0");
            Total.setText(invoice.getTotal().toString());
        }

        invoice.setDue(due.getValue());
        invoice.setType(type.getValue());
        invoice.setTime(Time.valueOf(time.getValue()));
        invoice.setDate(Date.valueOf(date.getValue()));
        invoice.setDescription(notes.getText());

        InvoiceDB iidb = new InvoiceDB();
        iidb.addInvoice(invoice);

        InvoiceGenerator ig = new InvoiceGenerator(invoice);
        String invoiceLoc = ig.getDestination();
        InvoiceGeneratorThread igt = new InvoiceGeneratorThread(ig);
        igt.start();

        if(sendEmail.isSelected())
        {
            SendAttachment.attachment = invoiceLoc;
            SendAttachment.customerName = invoice.getCustomer().getName();

            try {
                Stage secondaryStage = new Stage();
                secondaryStage.initStyle(StageStyle.UNDECORATED);
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sendAttachment.fxml")));
                Scene scene = new Scene(root);

                if(App.getConfiguration().getAppConfigurations().getAppTheme().equals("Light"))
                    scene.getStylesheets().add(Objects.requireNonNull(org.invoice.monkey.App.class.getResource("mainLight.css").toString()));
                else
                    scene.getStylesheets().add(Objects.requireNonNull(org.invoice.monkey.App.class.getResource("mainDark.css").toString()));

                secondaryStage.setTitle("Setup Email");
                secondaryStage.setResizable(false);
                secondaryStage.setScene(scene);
                secondaryStage.show();

            }catch(Exception e)
            {
                //System.out.println("Exception in home-screen:" + e.getClass().getName() + ": " + e.getMessage());
                e.printStackTrace();
            }
        }

        cancel(event);
    }

    public void cancel(ActionEvent event)
    {
        customer.setText("");
        due.setValue("");
        type.setValue("");

        date.setValue(java.time.LocalDate.now());
        time.setValue(java.time.LocalTime.now());

        itemDescription.setText("");
        quantity.setText("");
        rate.setText("");

        discount.setText("");
        notes.setText("");

        SubTotal.setText("");
        Discount.setText("");
        Total.setText("");

        invoiceItemTable.getItems().clear();
        addItem.setDisable(true);
        invoice = new Invoice();
    }
}

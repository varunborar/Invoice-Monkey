package org.invoice.monkey.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.invoice.monkey.Database.InvoiceDB;
import org.invoice.monkey.model.Invoice;
import org.invoice.monkey.utils.UI.tableview.InvoiceDue;
import org.invoice.monkey.utils.UI.tableview.InvoiceScheduled;
import org.invoice.monkey.utils.notification.ErrorNotification;
import org.invoice.monkey.utils.notification.Notification;

import java.util.Vector;

public class Home {

    @FXML
    private TableView<InvoiceDue> InvoiceDueTable;
    @FXML
    private TableColumn<InvoiceDue, String> Invoice;
    @FXML
    private TableColumn<InvoiceDue, String> Amount;
    @FXML
    private TableColumn<InvoiceDue, JFXButton> Button;


    @FXML
    private TableView<InvoiceScheduled> InvoiceScheduledTable;
    @FXML
    private TableColumn<InvoiceScheduled, String> CustomerName;
    @FXML
    private TableColumn<InvoiceScheduled, String> DeliveryDate;
    @FXML
    private TableColumn<InvoiceScheduled, JFXButton> Delivered;
    @FXML
    private TableColumn<InvoiceScheduled, JFXButton> View;

    private Vector<InvoiceDue> InvoiceDueList;
    private Vector<InvoiceScheduled> InvoiceScheduledList;



    private static Home activeController;

    public static void refresh()
    {
        activeController.refreshInvoiceDue();
        activeController.refreshInvoiceSchedule();
    }

    public void refreshInvoiceDue() {
        try {
            InvoiceDB idb = new InvoiceDB();

            if(InvoiceDueList == null)
                InvoiceDueList = new Vector<>();

            for (Invoice i : idb.getInvoices(-1, true)) {
                InvoiceDueList.add(new InvoiceDue(i));
            }

            InvoiceDueTable.getItems().addAll(InvoiceDueList);

        } catch (Exception e) {
            Notification.sendErrorNotification(ErrorNotification.DatabaseConnectionError);
        }
    }

    public void refreshInvoiceSchedule()
    {
        try{

            InvoiceDB idb = new InvoiceDB();
            if(InvoiceScheduledList == null)
                InvoiceScheduledList = new Vector<>();

            for(Invoice i: idb.getScheduledInvoices(-1))
            {
                InvoiceScheduledList.add(new InvoiceScheduled(i));
            }

        InvoiceScheduledTable.getItems().addAll(InvoiceScheduledList);

        }catch(Exception e)
        {
            Notification.sendErrorNotification(ErrorNotification.DatabaseConnectionError);
        }
    }

    public void initialize(){

        activeController = this;

        Invoice.setCellValueFactory(new PropertyValueFactory<>("Description"));
        Amount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        Button.setCellValueFactory(new PropertyValueFactory<>("Button"));

        CustomerName.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
        DeliveryDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        Delivered.setCellValueFactory(new PropertyValueFactory<>("Delivered"));
        View.setCellValueFactory(new PropertyValueFactory<>("View"));

        try{
            InvoiceDB idb = new InvoiceDB();
            InvoiceDueList = new Vector<>();
            for(Invoice i: idb.getInvoices(-1, true))
            {
                InvoiceDueList.add(new InvoiceDue(i));
            }

            InvoiceDueTable.getItems().addAll(InvoiceDueList);

            InvoiceScheduledList = new Vector<>();
            for(Invoice i: idb.getScheduledInvoices(-1))
            {
                InvoiceScheduledList.add(new InvoiceScheduled(i));
            }

            InvoiceScheduledTable.getItems().addAll(InvoiceScheduledList);

        }catch(Exception e)
        {
            e.printStackTrace();
            Notification.sendErrorNotification(ErrorNotification.DatabaseConnectionError);
        }
    }

    public void openInvoiceScreen()
    {
        homeScreen.setWorkSpaceArea("invoice.fxml", true);
    }

    public void openCustomerScreen()
    {
        homeScreen.setWorkSpaceArea("customerScreen.fxml", true);
    }

    public void openItemScreen()
    {
        homeScreen.setWorkSpaceArea("itemScreen.fxml", true);
    }

    public void openExpenseScreen()
    {
        homeScreen.setWorkSpaceArea("expenseScreen.fxml", true);
    }
}

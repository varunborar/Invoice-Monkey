package org.invoice.monkey.controller;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import org.invoice.monkey.Database.CustomerDB;
import org.invoice.monkey.model.Customer;
import org.invoice.monkey.utils.Animation;
import org.invoice.monkey.utils.Validator;

import java.util.Vector;

public class CustomerScreen {

    private Vector<Customer> customerList;
    private Customer selectedCustomer;

    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer,String> CUSTOMER_ID;
    @FXML
    private TableColumn<Customer, String> CUSTOMER_NAME;
    @FXML
    private TableColumn<Customer, String> CUSTOMER_NUMBER;
    @FXML
    private TableColumn<Customer, String> CUSTOMER_EMAIL;
    @FXML
    private TableColumn<Customer, String> CUSTOMER_ADDRESS;

    @FXML
    private Button createButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button refresh;

    @FXML
    private TextField searchBar;
    @FXML
    private TextField Name;
    @FXML
    private TextField Number;
    @FXML
    private TextField Email;
    @FXML
    private TextArea Address;

    @FXML
    private Label infoLabel;

    @FXML
    public void initialize()
    {
        customerTable.setPlaceholder(new Label("Customers will show here"));
        refreshCustomerTable();

        customerTable.setRowFactory(
                tableView->{
                    final TableRow<Customer> row = new TableRow<>();
                    final ContextMenu rowMenu = new ContextMenu();
                    MenuItem updateCustomer = new MenuItem(("Update"));
                    updateCustomer.setOnAction(actionEvent->{
                        createButton.setVisible(false);
                        updateButton.setVisible(true);
                        updateButton.setDisable(false);

                        selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
                        Name.setText(selectedCustomer.getName());
                        Number.setText(selectedCustomer.getPhoneNumber());
                        Email.setText(selectedCustomer.getEmail());
                        Address.setText(selectedCustomer.getAddress());
                    });

                    MenuItem removeCustomer = new MenuItem("Delete");
                    removeCustomer.setOnAction(actionEvent->{

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete" +
                                customerTable.getSelectionModel().getSelectedItem().getName() + "?",
                                ButtonType.YES, ButtonType.CANCEL);
                        alert.setTitle("Confirm Delete");
                        alert.showAndWait();

                        if(alert.getResult() == ButtonType.YES)
                        {
                            CustomerDB cdb = new CustomerDB();
                            cdb.deleteCustomer(customerTable.getSelectionModel().getSelectedItem());
                            customerTable.getItems().remove(row.getItem());
                        }
                        else {
                            alert.close();
                        }
                    });

                    rowMenu.getItems().addAll(updateCustomer, removeCustomer);

                    row.contextMenuProperty().bind(
                            Bindings.when(Bindings.isNotNull((row.itemProperty())))
                            .then(rowMenu)
                            .otherwise((ContextMenu) null));

                    return row;
                }
        );

        CUSTOMER_ID.setCellValueFactory((new PropertyValueFactory<>("customerID")));
        CUSTOMER_NAME.setCellValueFactory(new PropertyValueFactory<>("name"));
        CUSTOMER_NUMBER.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        CUSTOMER_EMAIL.setCellValueFactory(new PropertyValueFactory<>("email"));
        CUSTOMER_ADDRESS.setCellValueFactory(new PropertyValueFactory<>("address"));

        createButton.setDisable(true);
        updateButton.setDisable(true);
        updateButton.setVisible(false);

        infoLabel.setText("");
    }

    public void refreshCustomerTable()
    {
        customerTable.getItems().clear();
        CustomerDB cdb = new CustomerDB();
        customerList = cdb.getAllCustomers(100);

        Animation animation = new Animation();
        animation.simpleRefreshAnimation(refresh);

        for(Customer c: customerList)
        {
            customerTable.getItems().add(c);
        }
    }

    public void refreshCustomerTable(Vector<Customer> customerList, Boolean Clear)
    {
        if(Clear)
            customerTable.getItems().clear();
        for(Customer c: customerList)
        {
            customerTable.getItems().add(c);
        }
    }

    private Customer getCustomer()
    {
        Customer customer = new Customer();

        try{
            customer.setName(Name.getText());

            if(!Number.getText().equals(""))
                customer.setPhoneNumber(Number.getText());
            if(!Email.getText().equals(("")))
                customer.setEmail(Email.getText());
            if(!Address.getText().equals(""))
                customer.setAddress(Address.getText());

            Name.setText("");
            Number.setText("");
            Email.setText("");
            Address.setText("");

        }catch(Exception e)
        {
            System.out.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return customer;
    }

    public void create()
    {
        CustomerDB cdb = new CustomerDB();
        cdb.addCustomer(getCustomer());
        infoLabel.setText("Customer Added Successfully!");
        refreshCustomerTable();
    }

    public void update()
    {
        CustomerDB cdb = new CustomerDB();
        Customer customer = getCustomer();
        customer.setCustomerID(selectedCustomer.getRawCustomerID());
        cdb.updateCustomer(customer);

        infoLabel.setText("Updated details!");
        refreshCustomerTable();
        updateButton.setVisible(false);
        createButton.setVisible(true);
    }

    public void performSearch()
    {
        CustomerDB cdb = new CustomerDB();
        String searchCriteria = searchBar.getText();
        searchBar.setText("");
        Vector<Customer> resultList = cdb.getSearchList(searchCriteria, 10);
        if(resultList.size() == 0)
            infoLabel.setText("No customers matched your search");
        else
            refreshCustomerTable(resultList, true);
    }

    public void searchEvent(KeyEvent k)
    {
        if(k.getCode() == KeyCode.ENTER)
        {
            performSearch();
        }
    }

    public void screenClosed()
    {
        homeScreen.setWorkSpaceArea("home.fxml", false);
    }

    public void scroll(ScrollEvent sc)
    {
        ScrollBar Sc = (ScrollBar) customerTable.lookup(".scroll-bar:vertical");
        if(Sc.getValue() == 1.0)
        {
            customerTable.getSelectionModel().selectLast();
            Customer customer = customerTable.getSelectionModel().getSelectedItem();
            CustomerDB cdb = new CustomerDB();
            Vector<Customer> result = cdb.getNext(customer.getRawCustomerID(), 25);
            refreshCustomerTable(result, false);
        }
    }

    public void validateNonEmpty(KeyEvent ke)
    {
        Node node = (Node) ke.getSource();
        TextField tf = (TextField) node;

        if(Validator.isNonEmpty(tf.getText()))
        {
            createButton.setDisable(false);
            updateButton.setDisable(false);
            tf.getStyleClass().removeAll("text-field-error");
        }
        else
        {
            createButton.setDisable(true);
            updateButton.setDisable(true);
            tf.getStyleClass().add("text-field-error");
        }
    }

    public void validateEmail(KeyEvent ke)
    {
        TextField tf = (TextField) ke.getSource();
        if(Validator.isEmailValid(tf.getText()))
        {
            createButton.setDisable(false);
            updateButton.setDisable(false);
            tf.getStyleClass().removeAll("text-field-error");
        }
        else
        {
            createButton.setDisable(true);
            updateButton.setDisable(true);
            tf.getStyleClass().add("text-field-error");
        }
    }

    public void validateNumber(KeyEvent ke)
    {
        TextField tf = (TextField) ke.getSource();
        if(Validator.isPhoneNumberValid(tf.getText()))
        {
            createButton.setDisable(false);
            updateButton.setDisable(false);
            tf.getStyleClass().removeAll("text-field-error");
        }
        else
        {
            createButton.setDisable(true);
            updateButton.setDisable(true);
            tf.getStyleClass().add("text-field-error");
        }
    }

}

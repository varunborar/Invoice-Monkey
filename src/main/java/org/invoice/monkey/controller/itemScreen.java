package org.invoice.monkey.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.invoice.monkey.Database.ItemDB;
import org.invoice.monkey.model.Item;

import java.util.Vector;

public class itemScreen {


    private Vector<Item> itemList;


    @FXML
    private TextField Name;
    @FXML
    private TextField Price;
    @FXML
    private TextField Inventory;
    @FXML
    private ToggleGroup Type;
    @FXML
    private RadioButton Product;
    @FXML
    private RadioButton Service;
    @FXML
    private RadioButton Other;
    @FXML
    private Label infoLabel;

    //TABLE
    @FXML
    private TableView<Item> itemTable;
    @FXML
    private TableColumn<Item, String> ITEM_ID;
    @FXML
    private TableColumn<Item, String> ITEM_NAME;
    @FXML
    private TableColumn<Item, Float> ITEM_PRICE;
    @FXML
    private TableColumn<Item, String> ITEM_TYPE;
    @FXML
    private TableColumn<Item, Integer> ITEM_INVENTORY;



    @FXML
    public void initialize()
    {
        try {
            ITEM_ID.setCellValueFactory(new PropertyValueFactory<>("itemID"));
            ITEM_NAME.setCellValueFactory(new PropertyValueFactory<>("name"));
            ITEM_PRICE.setCellValueFactory(new PropertyValueFactory<>("price"));
            ITEM_TYPE.setCellValueFactory(new PropertyValueFactory<>("type"));
            ITEM_INVENTORY.setCellValueFactory(new PropertyValueFactory<>("inventory"));

            infoLabel.setText("");
        }catch(Exception se)
        {
            System.out.println(se.getClass().getName() + ": " + se.getMessage());
        }
        refreshItemTable();
    }

    void refreshItemTable()
    {
        ItemDB idb = new ItemDB();
        itemList = idb.getAllItems();

        for (Item i: itemList)
        {
            itemTable.getItems().add(i);
        }
    }

    void refreshItemTable(Vector<Item> itemList)
    {
        for (Item i: itemList)
        {
            itemTable.getItems().add(i);
        }
    }

    public void itemSelected()
    {
        try {
            Item item = itemTable.getSelectionModel().getSelectedItem();
            System.out.println(item.getName());
        }catch(Exception se)
        {
            System.out.println(se.getClass().getName() + ": " + se.getMessage());
        }
    }

    public void createItem()
    {
        String itemName = Name.getText();
        String itemPrice = Price.getText();
        RadioButton rb = (RadioButton) Type.getSelectedToggle();
        String itemType = rb.getText();
        String itemInventory = Inventory.getText();
        //System.out.println(itemName + " " + itemPrice + " " + itemType + " " + itemInventory);

        Item item = new Item(itemName, Float.parseFloat(itemPrice), itemType);
        item.updateInventory(Integer.parseInt(itemInventory));

        ItemDB idb = new ItemDB();
        idb.addItem(item);

        Name.setText("");
        Price.setText("");
        Inventory.setText("");
        Type.selectToggle(Product);

        infoLabel.setText("Item Added Successfully!");
    }
}

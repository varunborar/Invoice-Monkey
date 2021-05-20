package org.invoice.monkey.controller;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import org.invoice.monkey.App;
import org.invoice.monkey.Database.ItemDB;
import org.invoice.monkey.model.Item;
import org.invoice.monkey.utils.Validator;


import java.util.Vector;

public class itemScreen {


    private Vector<Item> itemList;
    private Item selectedItem;


    @FXML
    private TextField Name;
    @FXML
    private TextField Price;
    @FXML
    private TextField Size;
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
    @FXML
    private ChoiceBox<String> SizeType;
    @FXML
    private Button createButton;
    @FXML
    private Button updateButton;

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
    private TableColumn<Item, Integer> ITEM_SIZE;

    @FXML
    private TextField searchBar;


    @FXML
    public void initialize()
    {
        try {

            refreshItemTable();
            //Context Menus
            itemTable.setRowFactory(
                    tableView -> {
                        final TableRow<Item> row = new TableRow<>();
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem updateItem = new MenuItem("Update");
                        updateItem.setOnAction(actionEvent -> {
                            createButton.setVisible(false);
                            updateButton.setVisible(true);

                            selectedItem = itemTable.getSelectionModel().getSelectedItem();
                            Name.setText(selectedItem.getName());
                            Price.setText(selectedItem.getPrice() + "");
                            Size.setText(selectedItem.getRawSize() + "");
                            SizeType.setValue(selectedItem.getRawSizeType());
                            if(selectedItem.getType().equals("Product"))
                            {
                                Type.selectToggle(Product);
                            }
                            else if(selectedItem.getType().equals("Service"))
                            {
                                Type.selectToggle(Service);
                            }
                            else{
                                Type.selectToggle(Other);
                            }
                        });
                        MenuItem removeItem = new MenuItem("Delete");
                        removeItem.setOnAction(actionEvent -> {

                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + itemTable.getSelectionModel().getSelectedItem().getName() + " ?", ButtonType.YES, ButtonType.CANCEL);
                            alert.setTitle("Confirm Delete");
                            alert.showAndWait();

                            if (alert.getResult() == ButtonType.YES) {
                                ItemDB idb = new ItemDB();
                                idb.deleteItem(itemTable.getSelectionModel().getSelectedItem());
                                infoLabel.setText("Item Deleted");
                                itemTable.getItems().remove(row.getItem());
                            }
                            else{
                                alert.close();
                            }

                        });
                        rowMenu.getItems().addAll(updateItem, removeItem);

                        // only display context menu for non-null items:
                        row.contextMenuProperty().bind(
                                Bindings.when(Bindings.isNotNull(row.itemProperty()))
                                        .then(rowMenu)
                                        .otherwise((ContextMenu)null));
                        return row;
                    });


            ITEM_ID.setCellValueFactory(new PropertyValueFactory<>("itemID"));
            ITEM_NAME.setCellValueFactory(new PropertyValueFactory<>("name"));
            ITEM_PRICE.setCellValueFactory(new PropertyValueFactory<>("price"));
            ITEM_TYPE.setCellValueFactory(new PropertyValueFactory<>("type"));
            ITEM_SIZE.setCellValueFactory(new PropertyValueFactory<>("size"));

            createButton.setDisable(true);
            updateButton.setDisable(true);
            updateButton.setVisible(false);

            SizeType.getItems().addAll("N/A","mL", "L", "g", "Kg", "cm", "m", "inch");
            SizeType.setValue("N/A");
            infoLabel.setText("");


        }catch(Exception se)
        {
            System.out.println(se.getClass().getName() + ": " + se.getMessage() + " " + se.getCause());
        }


    }

    public void refreshItemTable()
    {
        itemTable.getItems().clear();
        ItemDB idb = new ItemDB();
        itemList = idb.getAllItems(100);
        for (Item i: itemList)
        {
            itemTable.getItems().add(i);
        }
    }

    public void refreshItemTable(Vector<Item> itemList, Boolean Clear)
    {
        if(Clear)
            itemTable.getItems().clear();
        for (Item i: itemList)
        {
            itemTable.getItems().add(i);
        }
    }

    public void validatePrice()
    {
        if(!Validator.isFloatValid(Price.getText()))
        {
            Price.getStyleClass().add("input-error");
            createButton.setDisable(true);
            updateButton.setDisable(true);
        }
        else{
            Price.getStyleClass().removeAll("input-error");
            createButton.setDisable(false);
            updateButton.setDisable(false);
        }
    }


    private Item getItem() {
        Item item = new Item();
        try {
            String itemName = Name.getText();
            String itemPrice = Price.getText();
            RadioButton rb = (RadioButton) Type.getSelectedToggle();
            String itemType = rb.getText();
            String itemSize = Size.getText();
            String itemSizeType = SizeType.getValue();

            item.updateName(itemName);
            item.updatePrice(Float.parseFloat(itemPrice));
            item.updateType(itemType);
            if (!itemSize.equals(""))
            {   item.updateSize(itemSize);
                item.updateSizeType(itemSizeType);
            }


            Name.setText("");
            Price.setText("");
            Size.setText("");
            SizeType.setValue("N/A");
            Type.selectToggle(Product);

        }catch(Exception e)
        {
            System.out.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return item;
    }

    public void createItem()
    {
        ItemDB idb = new ItemDB();
        idb.addItem(getItem());
        infoLabel.setText("Item Added Successfully!");
        refreshItemTable();
    }

    public void updateItem()
    {
        ItemDB idb = new ItemDB();
        Item item = getItem();
        item.setItemID(selectedItem.getRawItemID());
        idb.updateItem(item);

        infoLabel.setText("Item Updated Successfully");
        refreshItemTable();
        updateButton.setVisible(false);
        createButton.setVisible(true);
    }

    public void performSearch()
    {
        ItemDB idb = new ItemDB();
        String searchCriteria = searchBar.getText();
        searchBar.setText("");
        Vector<Item> resultList = idb.getSearchList(searchCriteria, 10);
        if(resultList.size() == 0)
            infoLabel.setText("No items matched your search");
        else
            refreshItemTable(resultList, true);
    }

    public void searchEvent(KeyEvent k)
    {
        if(k.getCode() == KeyCode.ENTER)
        {
            performSearch();
        }
    }

    public void itemScreenClosed()
    {
        App.changeScene("homeScreen.fxml", "Invoice Monkey");
    }

    public void scroll(ScrollEvent sc)
    {
        ScrollBar Sc = (ScrollBar) itemTable.lookup(".scroll-bar:vertical");
        if(Sc.getValue() == 1.0)
        {

            itemTable.getSelectionModel().selectLast();
            Item item = itemTable.getSelectionModel().getSelectedItem();
            ItemDB idb = new ItemDB();
            Vector<Item> result = idb.getNext(item.getRawItemID(), 25);
            refreshItemTable(result, false);
            itemTable.scrollTo(item);
        }
    }
}

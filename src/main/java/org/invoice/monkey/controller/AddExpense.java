package org.invoice.monkey.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.invoice.monkey.Database.ExpenseDB;
import org.invoice.monkey.model.Expense;

import java.sql.Date;

public class AddExpense {

    @FXML
    private JFXDatePicker date;
    @FXML
    private JFXComboBox<String> category;
    @FXML
    private JFXTextField description;
    @FXML
    private JFXTextField amount;

    @FXML
    private JFXButton save;


    public void initialize()
    {
        category.getItems().addAll("RENT", "EQUIPMENT", "INVENTORY", "LICENSE", "INSURANCE", "MARKETING", "STAFF", "OTHERS");
    }


    public void checkFields()
    {
        if(date.getValue() != null && category.getValue() != null &&
                !description.getText().equals("") && !amount.getText().equals(""))
            save.setDisable(false);
    }


    public void addExpense(ActionEvent event)
    {
        Expense expense = new Expense();
        expense.setDate(Date.valueOf(date.getValue()));
        expense.setCategory(category.getValue());
        expense.setDescription(description.getText());
        expense.setAmount(Float.parseFloat(amount.getText()));

        ExpenseDB edb = new ExpenseDB();
        edb.addExpense(expense);

        cancelExpense(event);

    }

    public void cancelExpense(ActionEvent event)
    {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }
}

package org.invoice.monkey.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.invoice.monkey.App;
import org.invoice.monkey.Database.ExpenseDB;
import org.invoice.monkey.model.Expense;

import java.sql.Date;
import java.util.Objects;
import java.util.Vector;

public class ExpenseScreen {

    @FXML
    private VBox expenseTable;


    private Vector<Expense> expenseList;

    public void initialize()
    {
        refreshExpenseList();
    }

    public void refreshExpenseList()
    {
        expenseTable.getChildren().clear();
        ExpenseDB edb = new ExpenseDB();
        expenseList = edb.getAllExpense(-1);

        for(Expense e: expenseList)
            expenseTable.getChildren().add(createExpenseItem(e));
    }

    public void addButton()
    {

        try {
            Stage secondaryStage = new Stage();
            secondaryStage.initModality(Modality.APPLICATION_MODAL);
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("addExpense.fxml")));
            Scene scene = new Scene(root);

            if(App.getConfiguration().getAppConfigurations().getAppTheme().equals("Light"))
                scene.getStylesheets().add(Objects.requireNonNull(org.invoice.monkey.App.class.getResource("mainLight.css").toString()));
            else
                scene.getStylesheets().add(Objects.requireNonNull(org.invoice.monkey.App.class.getResource("mainDark.css").toString()));

            secondaryStage.setTitle("Expense Tracker");
            secondaryStage.setResizable(false);
            secondaryStage.setScene(scene);
            secondaryStage.showAndWait();

            refreshExpenseList();

        }catch(Exception e)
        {
            System.out.println("Exception in expense screen:" + e.getClass().getName() + ": " + e.getMessage());
        }
    }





    private HBox createExpenseItem(Expense expense)
    {
        try{
            HBox expenseItem = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("expenseItem.fxml")));
            ObservableList<Node> items = expenseItem.getChildren();

            ((Label)items.get(0)).setText(expense.getExpenseID().toString());
            ((Label)items.get(1)).setText(expense.getDate().toString());
            ((Label)items.get(2)).setText(expense.getDescription());
            ((Label)items.get(3)).setText(expense.getCategory());
            ((Label)items.get(4)).setText(expense.getAmount().toString());

            return expenseItem;
        }catch(Exception e)
        {
            System.out.println(e.getClass().getName() + ": " + e.getMessage() + "during: Add Expense Item");
        }
        return null;
    }

}

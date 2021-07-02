package org.invoice.monkey.controller;

import com.jfoenix.controls.JFXDatePicker;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import org.invoice.monkey.App;
import org.invoice.monkey.Database.Analytics.AnalyticsDB;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;

public class Analytics {


    @FXML
    private JFXDatePicker FROM;
    @FXML
    private JFXDatePicker TO;

    @FXML
    private Label TotalOrders;
    @FXML
    private Label Sales;
    @FXML
    private Label Expense;
    @FXML
    private Label Due;

    @FXML
    private PieChart ExpensesChart;
    @FXML
    private PieChart OrdersChart;
    @FXML
    private PieChart SalesChart;


    private String Currency;


    public void initialize() throws SQLException {
        Currency = App.getConfiguration().getInvoiceDetails().getCurrency().getCurrencySymbol();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));

        FROM.setValue(new Date(cal.getTimeInMillis()).toLocalDate());

        TO.valueProperty().addListener((observable, oldValue, newValue)->{

            AnalyticsDB adb = new AnalyticsDB();
            try {
                TotalOrders.setText(adb.getTotalOrders(Date.valueOf(FROM.getValue()), Date.valueOf(TO.getValue())));
                Sales.setText(Currency + adb.getSales(Date.valueOf(FROM.getValue()), Date.valueOf(TO.getValue())));
                Expense.setText(Currency + adb.getExpense(Date.valueOf(FROM.getValue()), Date.valueOf(TO.getValue())));
                Due.setText(Currency + adb.getDue(Date.valueOf(FROM.getValue()), Date.valueOf(TO.getValue())));

                ExpensesChart.setData(adb.getExpensesData(Date.valueOf(FROM.getValue()), Date.valueOf(TO.getValue())));
                ExpensesChart.setLabelsVisible(true);
                ExpensesChart.setLegendVisible(false);
                ExpensesChart.setTitle("Expense");

                OrdersChart.setData(adb.getOrdersData(Date.valueOf(FROM.getValue()), Date.valueOf(TO.getValue())));
                OrdersChart.setLabelsVisible(true);
                OrdersChart.setLegendVisible(false);
                OrdersChart.setTitle("Payment");

                SalesChart.setData(adb.getSalesData(Date.valueOf(FROM.getValue()), Date.valueOf(TO.getValue())));
                SalesChart.setLabelsVisible(true);
                SalesChart.setLegendVisible(false);
                SalesChart.setTitle("Sales");

            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        });

        TO.setValue(java.time.LocalDate.now());

        FROM.valueProperty().addListener((observable, oldValue, newValue)->{

            AnalyticsDB adb = new AnalyticsDB();
            try {
                TotalOrders.setText(adb.getTotalOrders(Date.valueOf(FROM.getValue()), Date.valueOf(TO.getValue())));
                Sales.setText(Currency + adb.getSales(Date.valueOf(FROM.getValue()), Date.valueOf(TO.getValue())));
                Expense.setText(Currency + adb.getExpense(Date.valueOf(FROM.getValue()), Date.valueOf(TO.getValue())));
                Due.setText(Currency + adb.getDue(Date.valueOf(FROM.getValue()), Date.valueOf(TO.getValue())));

                ExpensesChart.setData(adb.getExpensesData(Date.valueOf(FROM.getValue()), Date.valueOf(TO.getValue())));
                ExpensesChart.setLabelsVisible(true);
                ExpensesChart.setLegendVisible(false);
                ExpensesChart.setTitle("Expense");

                OrdersChart.setData(adb.getOrdersData(Date.valueOf(FROM.getValue()), Date.valueOf(TO.getValue())));
                OrdersChart.setLabelsVisible(true);
                OrdersChart.setLegendVisible(false);
                OrdersChart.setTitle("Payment");

                SalesChart.setData(adb.getSalesData(Date.valueOf(FROM.getValue()), Date.valueOf(TO.getValue())));
                SalesChart.setLabelsVisible(true);
                SalesChart.setLegendVisible(false);
                SalesChart.setTitle("Sales");


            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        });

    }

}

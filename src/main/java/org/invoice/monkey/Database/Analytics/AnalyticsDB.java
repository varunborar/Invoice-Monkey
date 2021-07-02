package org.invoice.monkey.Database.Analytics;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import org.invoice.monkey.Database.database;

import java.sql.*;

public class AnalyticsDB extends database {

    public AnalyticsDB()
    {
        super();
    }

    public String getTotalOrders(Date From, Date To) throws SQLException {

        String totalOrders = "";
        String orderQuery = "SELECT COUNT(*) AS COUNT" +
                " FROM invoice" +
                " WHERE Invoice_Date BETWEEN" +
                " ? AND ?";

        Connection con = getCon();
        PreparedStatement query = con.prepareStatement(orderQuery);
        query.setDate(1, From);
        query.setDate(2, To);

        ResultSet rs = query.executeQuery();
        while(rs.next())
        {
            totalOrders = String.format("%d", rs.getInt("COUNT"));
        }
        return totalOrders;
    }

    public String getSales(Date From, Date To) throws SQLException {
        String Sales = "";
        String salesQuery = "SELECT SUM(Sub_Total) AS SUBTOTAL, SUM(DISCOUNT) AS DISCOUNT" +
                " FROM invoice" +
                " WHERE Invoice_Date BETWEEN" +
                " ? AND ?";

        Connection con = getCon();
        PreparedStatement query = con.prepareStatement(salesQuery);
        query.setDate(1, From);
        query.setDate(2, To);

        ResultSet rs = query.executeQuery();
        while(rs.next())
        {
            float subtotal = rs.getFloat("SUBTOTAL");
            float discount = rs.getFloat("DISCOUNT");
            Sales = String.format("%d", (int) (subtotal - discount));
        }
        return Sales;
    }

    public String getExpense(Date From, Date To) throws SQLException {
        String Expense = "";
        String expenseQuery = "SELECT SUM(Expense_Amount) AS EXPENSE" +
                " FROM expense" +
                " WHERE Expense_Date BETWEEN" +
                " ? AND ?";

        Connection con = getCon();
        PreparedStatement query = con.prepareStatement(expenseQuery);
        query.setDate(1, From);
        query.setDate(2, To);

        ResultSet rs = query.executeQuery();
        while(rs.next())
        {
            Expense = String.format("%d", (int) (rs.getFloat("EXPENSE")));
        }
        return Expense;
    }

    public String getDue(Date From, Date To) throws SQLException{
        String Due = "";
        String dueQuery = "SELECT SUM(Sub_Total) AS SUBTOTAL, SUM(DISCOUNT) AS DISCOUNT" +
                " FROM invoice" +
                " WHERE Invoice_Date BETWEEN" +
                " ? AND ? " +
                "AND Invoice_Due = 'DUE'";

        Connection con = getCon();
        PreparedStatement query = con.prepareStatement(dueQuery);
        query.setDate(1, From);
        query.setDate(2, To);

        ResultSet rs = query.executeQuery();
        while(rs.next())
        {
            float subtotal = rs.getFloat("SUBTOTAL");
            float discount = rs.getFloat("DISCOUNT");
            Due = String.format("%d", (int) (subtotal - discount));
        }

        return Due;
    }


    public ObservableList<PieChart.Data> getExpensesData(Date From, Date To) throws SQLException
    {
        ObservableList<PieChart.Data> ExpenseData = FXCollections.observableArrayList();

        String ExpenseDataQuery = "SELECT Expense_Category AS Category, " +
                "SUM(Expense_Amount) AS Amount " +
                "FROM Expense " +
                "WHERE Expense_Date BETWEEN ? AND ? " +
                "GROUP BY Expense_Category";

        Connection con = getCon();
        PreparedStatement query = con.prepareStatement(ExpenseDataQuery);

        query.setDate(1, From);
        query.setDate(2, To);

        ResultSet rs = query.executeQuery();

        while(rs.next())
        {
            ExpenseData.add(new PieChart.Data(rs.getString("Category"), rs.getFloat("Amount")));
        }

        return ExpenseData;
    }

    public ObservableList<PieChart.Data> getOrdersData(Date From, Date To) throws SQLException
    {
        ObservableList<PieChart.Data> OrdersData = FXCollections.observableArrayList();

        String OrdersDataQuery = "SELECT Invoice_Due AS Category, " +
                "SUM(Discount) AS Discount, " +
                "SUM(Sub_Total) AS SubTotal " +
                "FROM Invoice " +
                "WHERE Invoice_Date BETWEEN ? AND ? " +
                "GROUP BY Invoice_Due;";

        Connection con = getCon();
        PreparedStatement query = con.prepareStatement(OrdersDataQuery);

        query.setDate(1, From);
        query.setDate(2, To);

        ResultSet rs = query.executeQuery();

        while(rs.next())
        {
            OrdersData.add(new PieChart.Data(rs.getString("Category"), (rs.getFloat("SubTotal") - rs.getFloat("Discount"))));
        }

        return OrdersData;
    }

    public ObservableList<PieChart.Data> getSalesData(Date From, Date To) throws SQLException
    {
        ObservableList<PieChart.Data> SalesData = FXCollections.observableArrayList();

        String SalesDataQuery = "SELECT Invoice_Type AS Category, " +
                "COUNT(Invoice_ID) AS Count " +
                "FROM Invoice " +
                "WHERE Invoice_Date BETWEEN ? AND ? " +
                "GROUP BY Invoice_Type;";

        Connection con = getCon();
        PreparedStatement query = con.prepareStatement(SalesDataQuery);

        query.setDate(1, From);
        query.setDate(2, To);

        ResultSet rs = query.executeQuery();

        while(rs.next())
        {
            SalesData.add(new PieChart.Data(rs.getString("Category"), rs.getInt("Count")));
        }

        return SalesData;
    }
}

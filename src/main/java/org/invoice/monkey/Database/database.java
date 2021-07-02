package org.invoice.monkey.Database;

import org.invoice.monkey.App;
import org.invoice.monkey.model.Configurations.Configuration;
import org.invoice.monkey.utils.UI.DatabaseConnectionException;


import java.sql.*;

public class database {



    public static Boolean checkConnection(Configuration config) throws DatabaseConnectionException
    {
        try {
            if (!config.getDatabaseDetails().isCustomDatabaseSet()) {
                String path = config.getDatabaseDetails().getLocalDatabasePath();
                DriverManager.getConnection("jdbc:sqlite:" + path);
            } else {
                String url = config.getDatabaseDetails().getFormattedURL();

                DriverManager.getConnection("jdbc:mysql:" + url,
                        config.getDatabaseDetails().getUserName(),
                        config.getDatabaseDetails().getPassword()
                );
            }
        }catch(SQLException se)
        {
            System.out.println(se.getClass().getName() + ": " + se.getMessage());
            throw new DatabaseConnectionException(se.getMessage());
        }
        return true;
    }

    protected Connection getCon()
    {
        try {
            if (!App.getConfiguration().getDatabaseDetails().isCustomDatabaseSet()) {
                String path = App.getConfiguration().getDatabaseDetails().getLocalDatabasePath();
                return DriverManager.getConnection("jdbc:sqlite:" + path);
            } else {
                String url = App.getConfiguration().getDatabaseDetails().getFormattedURL();
                return DriverManager.getConnection("jdbc:mysql:" + url,
                        App.getConfiguration().getDatabaseDetails().getUserName(),
                        App.getConfiguration().getDatabaseDetails().getPassword()
                );
            }
        }catch(SQLException se)
        {
            System.out.println(se.getClass().getName() + ": " + se.getMessage());
        }
        return null;
    }

    public void configureAllTables()
    {
        configureItemTable();
        configureCustomerTable();
        configureInvoiceTable();
        configureInvoiceItemTable();
        configureExpenseTable();
    }

    protected void configureItemTable()
    {
        Connection con;
        String itemTableSQLQuery;
        if(!App.getConfiguration().getDatabaseDetails().isCustomDatabaseSet()) {
            itemTableSQLQuery = "CREATE TABLE IF NOT EXISTS Item(" +
                    "Item_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Item_Name VARCHAR(50) NOT NULL," +
                    "Item_Price DECIMAL(9,2) NOT NULL," +
                    "Item_Size VARCHAR(8)," +
                    "Item_Size_Type VARCHAR(4));";
        }else
        {
            itemTableSQLQuery = "CREATE TABLE IF NOT EXISTS Item(" +
                    "Item_ID INTEGER PRIMARY KEY AUTO_INCREMENT," +
                    "Item_Name VARCHAR(50) NOT NULL," +
                    "Item_Price DECIMAL(9,2) NOT NULL," +
                    "Item_Size VARCHAR(8),"+
                    "Item_Size_Type VARCHAR(4));";
        }
        try {
            con = getCon();
            Statement createTable = con.createStatement();
            createTable.executeUpdate(itemTableSQLQuery);
            createTable.close();
            con.close();
        }catch(SQLException se)
        {
            System.out.println(se.getClass().getName() + ": " + se.getMessage());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    protected void configureCustomerTable()
    {
        Connection con;
        String customerTableSQLQuery;
        if(!App.getConfiguration().getDatabaseDetails().isCustomDatabaseSet()) {
            customerTableSQLQuery = "CREATE TABLE IF NOT EXISTS Customer(" +
                    "Customer_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Customer_Name VARCHAR(30) NOT NULL," +
                    "Customer_Email VARCHAR(50) UNIQUE," +
                    "Customer_Address VARCHAR(150) DEFAULT 'N/A'," +
                    "Customer_Phonenumber VARCHAR(12) );";
        }else{
            customerTableSQLQuery = "CREATE TABLE IF NOT EXISTS Customer(" +
                    "Customer_ID INTEGER PRIMARY KEY AUTO_INCREMENT," +
                    "Customer_Name VARCHAR(30) NOT NULL," +
                    "Customer_Email VARCHAR(50) UNIQUE," +
                    "Customer_Address VARCHAR(150) DEFAULT 'N/A'," +
                    "Customer_Phonenumber VARCHAR(12) );";
        }
        try {
            con = getCon();
            Statement createTable = con.createStatement();
            createTable.executeUpdate(customerTableSQLQuery);
            createTable.close();
            con.close();
        }catch(SQLException se)
        {
            System.out.println(se.getClass().getName() + ": " + se.getMessage());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    protected void configureInvoiceTable()
    {
        Connection con;
        String invoiceTableQuery = "CREATE TABLE IF NOT EXISTS Invoice(" +
                    "Invoice_ID DECIMAL(12,0) PRIMARY KEY," +
                    "Customer_ID INTEGER NOT NULL," +
                    "Invoice_Date DATE NOT NULL," +
                    "Invoice_Time TIME NOT NULL," +
                    "Invoice_Type VARCHAR(15) NOT NULL," +
                    "Invoice_Due VARCHAR(4) NOT NULL," +
                    "Invoice_Description VARCHAR(120)," +
                    "Sub_Total DECIMAL(9,2) NOT NULL," +
                    "Discount DECIMAL(7,2) NOT NULL," +
                    "FOREIGN KEY(Customer_ID) REFERENCES Customer(Customer_ID));";

        try{
            con = getCon();
            Statement createTable = con.createStatement();
            createTable.executeUpdate(invoiceTableQuery);
            createTable.close();
            con.close();

        }catch(SQLException se)
        {
            System.out.println("Invoice Item:" + se.getClass().getName() + ": " + se.getMessage());
        }
    }

    protected void configureInvoiceItemTable()
    {
        Connection con;
        String invoiceItemTableQuery = "CREATE TABLE IF NOT EXISTS InvoiceItem(" +
                "Invoice_ID DECIMAL(12,0) NOT NULL," +
                "Item_ID INTEGER NOT NULL," +
                "Item_Quantity INTEGER NOT NULL," +
                "FOREIGN KEY(Invoice_ID) REFERENCES Invoice(Invoice_ID), " +
                "FOREIGN KEY(Item_ID) REFERENCES Item(Item_ID)," +
                "PRIMARY KEY(Invoice_ID, Item_ID));";

        try{
            con = getCon();
            Statement createTable = con.createStatement();
            createTable.executeUpdate(invoiceItemTableQuery);
            createTable.close();
            con.close();

        }catch(SQLException se)
        {
            System.out.println("Invoice Item:" + se.getClass().getName() + ": " + se.getMessage());
        }
    }

    protected void configureExpenseTable()
    {
        Connection con;
        String expenseTableSQLQuery;
        if(!App.getConfiguration().getDatabaseDetails().isCustomDatabaseSet()) {
            expenseTableSQLQuery = "CREATE TABLE IF NOT EXISTS Expense(" +
                    "Expense_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Expense_Description VARCHAR(50) NOT NULL," +
                    "Expense_Category VARCHAR(15)," +
                    "Expense_Amount DECIMAL(10,2)," +
                    "Expense_Date DATE);";
        }else{
            expenseTableSQLQuery = "CREATE TABLE IF NOT EXISTS Expense(" +
                    "Expense_ID INTEGER PRIMARY KEY AUTO_INCREMENT," +
                    "Expense_Description VARCHAR(50) NOT NULL," +
                    "Expense_Category VARCHAR(15)," +
                    "Expense_Amount DECIMAL(10,2)," +
                    "Expense_Date DATE);";
        }
        try {
            con = getCon();
            Statement createTable = con.createStatement();
            createTable.executeUpdate(expenseTableSQLQuery);
            createTable.close();
            con.close();
        }catch(SQLException se)
        {
            System.out.println(se.getClass().getName() + ": " + se.getMessage());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}

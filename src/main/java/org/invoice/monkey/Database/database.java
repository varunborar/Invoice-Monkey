package org.invoice.monkey.Database;

import org.invoice.monkey.App;
import org.invoice.monkey.model.Configurations.Configuration;
import org.invoice.monkey.utils.UIExceptions.DatabaseConnectionException;


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
                        App.getConfiguration().getDatabaseDetails().getUserName(),
                        App.getConfiguration().getDatabaseDetails().getPassword()
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
    }

    public void configureItemTable()
    {
        Connection con;
        String itemTableSQLQuery;
        if(!App.getConfiguration().getDatabaseDetails().isCustomDatabaseSet()) {
            itemTableSQLQuery = "CREATE TABLE IF NOT EXISTS Item(" +
                    "Item_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Item_Name VARCHAR(50) NOT NULL," +
                    "Item_Price DECIMAL(9,2) NOT NULL," +
                    "Item_Type VARCHAR(10) NOT NULL," +
                    "Item_Size VARCHAR(8)," +
                    "Item_Size_Type VARCHAR(4));";
        }else
        {
            itemTableSQLQuery = "CREATE TABLE IF NOT EXISTS Item(" +
                    "Item_ID INTEGER PRIMARY KEY AUTO_INCREMENT," +
                    "Item_Name VARCHAR(50) NOT NULL," +
                    "Item_Price DECIMAL(9,2) NOT NULL," +
                    "Item_Type VARCHAR(10) NOT NULL," +
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

    public void configureCustomerTable()
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


}

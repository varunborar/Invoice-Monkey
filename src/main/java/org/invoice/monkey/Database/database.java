package org.invoice.monkey.Database;

import org.invoice.monkey.App;


import java.sql.*;

public class database {




    public database()
    {
        Connection con;
        con = null;
        String path;
        if(!App.getConfiguration().isCustomDatabaseSet()) {
            try {
                path = App.getConfiguration().getLocalDatabasePath();
                Class.forName("org.sqlite.JDBC");
                con = DriverManager.getConnection("jdbc:sqlite:" + path);

                con.close();

            } catch (Exception e) {
                System.out.println("Error Connecting to database\n" + e.getClass().getName() + ": " + e.getCause() + ", " + e.getMessage());
            }
        }else
        {
            // Code for MySQL Server
        }

    }

    protected Connection getCon() throws java.sql.SQLException
    {
        if(!App.getConfiguration().isCustomDatabaseSet()) {
            String path = App.getConfiguration().getLocalDatabasePath();
            return DriverManager.getConnection("jdbc:sqlite:" + path);
        }
        else
        {
            return null;
        }
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
        if(!App.getConfiguration().isCustomDatabaseSet()) {
            itemTableSQLQuery = "CREATE TABLE IF NOT EXISTS Item(" +
                    "Item_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Item_Name VARCHAR(30) NOT NULL," +
                    "Item_Price DECIMAL(9,2) NOT NULL," +
                    "Item_Type VARCHAR(10) NOT NULL," +
                    "Item_Size DECIMAL(9,2)," +
                    "Item_Size_Type VARCHAR(4));";
        }else
        {
            itemTableSQLQuery = "CREATE TABLE IF NOT EXISTS Item(" +
                    "Item_ID INTEGER PRIMARY KEY AUTO_INCREMENT," +
                    "Item_Name VARCHAR(30) NOT NULL," +
                    "Item_Price DECIMAL(9,2) NOT NULL," +
                    "Item_Type VARCHAR(10) NOT NULL," +
                    "Item_Size DECIMAL(9,2),"+
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
        if(!App.getConfiguration().isCustomDatabaseSet()) {
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



    public static void main(String[] Args)
    {
        database db = new database();
        db.configureItemTable();
        db.configureCustomerTable();
    }

}

package org.invoice.monkey.Database;

import org.invoice.monkey.model.Customer;
import org.invoice.monkey.model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class CustomerDB extends database{

    public void addCustomer(Customer customer)
    {
        String addCustomerQuery = "INSERT INTO Customer(Customer_Name, Customer_Email, Customer_Address, Customer_Phonenumber) VALUES (?,?,?,?);";

        try {
            Connection con = getCon();
            PreparedStatement insert = con.prepareStatement(addCustomerQuery);
            insert.setString(1, customer.getName());
            insert.setString(2, customer.getEmail());
            insert.setString(3, customer.getAddress());
            insert.setString(4, customer.getPhoneNumber());

            insert.executeUpdate();
            insert.close();
            con.close();
        }catch(SQLException se)
        {
            System.out.println(se.getClass().getName() + ": " + se.getMessage() + "(" + se.getCause() + ")");
        }
    }

    public Customer getCustomer(Long customerID)
    {
        Customer customer = new Customer();
        try {
            Connection con = getCon();
            String getCustomerQuery = "SELECT * FROM Customer " +
                    "WHERE Customer_ID = ?";
            PreparedStatement query = con.prepareStatement(getCustomerQuery);
            query.setLong(1,customerID);

            ResultSet customers = query.executeQuery();

            while(customers.next())
            {
                customer.setCustomerID(customers.getLong("Customer_ID"));
                customer.setName(customers.getString("Customer_Name"));
                customer.setEmail(customers.getString("Customer_Email"));
                customer.setAddress(customers.getString("Customer_Address"));
                customer.setPhoneNumber(customers.getString("Customer_Phonenumber"));
            }

            query.close();
            con.close();
        }catch(SQLException se)
        {
            System.out.println(se.getClass().getName() + ": " + se.getMessage());
        }
        return customer;
    }

    public Vector<Customer> getAllCustomers(Integer limit)
    {
        Vector<Customer> customerList = new Vector<Customer>();

        try{
            Connection con = getCon();
            String getCustomerQuery;
            if(limit == -1)
            {
                getCustomerQuery = "SELECT * FROM Customer;";
            }else
            {
                getCustomerQuery = "SELECT * FROM Customer " +
                        "LIMIT ?;";
            }

            PreparedStatement query = con.prepareStatement(getCustomerQuery);
            if(limit != -1)
                query.setLong(1, limit);

            ResultSet customers = query.executeQuery();
            while(customers.next())
            {
                Customer customer = new Customer();
                customer.setCustomerID(customers.getLong("Customer_ID"));
                customer.setName(customers.getString("Customer_Name"));
                customer.setEmail(customers.getString("Customer_Email"));
                customer.setAddress(customers.getString("Customer_Address"));
                customer.setPhoneNumber(customers.getString("Customer_Phonenumber"));
                customerList.add(customer);
            }
            query.close();
            con.close();
        }catch(SQLException se)
        {
            System.out.println("Get All Items:" + se.getClass().getName() + ": " + se.getMessage());
        }
        return customerList;
    }

    public Vector<Customer> getNext(Long start, Integer next)
    {
        Vector<Customer> customerList = new Vector<>();

        try{
            Connection con = getCon();

            String getCustomerQuery = "SELECT * FROM Customer " +
                    "WHERE Customer_ID > ? " +
                    "LIMIT ?;";

            PreparedStatement query = con.prepareStatement(getCustomerQuery);
            query.setLong(1, start);
            query.setInt(2, next);

            ResultSet customers = query.executeQuery();

            while(customers.next())
            {
                Customer customer = new Customer();
                customer.setCustomerID(customers.getLong("Customer_ID"));
                customer.setName(customers.getString("Customer_Name"));
                customer.setEmail(customers.getString("Customer_Email"));
                customer.setAddress(customers.getString("Customer_Address"));
                customer.setPhoneNumber(customers.getString("Customer_Phonenumber"));
                customerList.add(customer);
            }

            query.close();
            con.close();
        }catch(SQLException se)
        {
            System.out.println(se.getClass().getName() + ": " + se.getMessage() + "(" + se.getCause() + ")");
        }
    return customerList;
    }

    public Vector<Customer> getSearchList(String searchString, Integer limit)
    {
        Vector<Customer> customerList = new Vector<>();

        try {
            Connection con = getCon();
            String searchQuery = "SELECT * FROM Customer " +
                    "WHERE Customer_Name LIKE ? " +
                    "OR Customer_Phonenumber LIKE ? " +
                    "LIMIT ?;";

            PreparedStatement query = con.prepareStatement(searchQuery);
            query.setString(1, "%" + searchString + "%");
            query.setString(2, searchString + "%");
            query.setInt(3, limit);

            ResultSet customers = query.executeQuery();

            while(customers.next())
            {
                Customer customer = new Customer();
                customer.setCustomerID(customers.getLong("Customer_ID"));
                customer.setName(customers.getString("Customer_Name"));
                customer.setEmail(customers.getString("Customer_Email"));
                customer.setAddress(customers.getString("Customer_Address"));
                customer.setPhoneNumber(customers.getString("Customer_Phonenumber"));
                customerList.add(customer);
            }
            query.close();
            con.close();
        }catch(SQLException se)
        {
            System.out.println(se.getClass().getName() + ": " + se.getMessage() + "(" + se.getCause() + ")");
        }
        return customerList;
    }

    public void updateCustomer(Customer customer)
    {
        try{
            Connection con = getCon();

            String updateQuery = "UPDATE Customer " +
                    "SET Customer_Name = ?, " +
                    "Customer_Email = ?, " +
                    "Customer_Address = ?, " +
                    "Customer_Phonenumber = ? " +
                    "WHERE Customer_ID = ?;";

            PreparedStatement query = con.prepareStatement(updateQuery);
            query.setString(1, customer.getName());
            query.setString(2, customer.getEmail());
            query.setString(3, customer.getAddress());
            query.setString(4, customer.getPhoneNumber());
            query.setLong(5, customer.getRawCustomerID());

            query.executeUpdate();
            query.close();
            con.close();

        }catch(SQLException se)
        {
            System.out.println(se.getClass().getName() + ": " + se.getMessage() + "(" + se.getCause() + ")");
        }
    }

    public void deleteCustomer(Customer customer)
    {
        try{
            Connection con = getCon();

            String deleteQuery = "DELETE FROM Customer WHERE Customer_ID = ?;";
            PreparedStatement query = con.prepareStatement(deleteQuery);
            query.setLong(1, customer.getRawCustomerID());

            query.executeUpdate();
            query.close();
            con.close();

        }catch(SQLException se)
        {
            System.out.println(se.getClass().getName() + ": " + se.getMessage() + "(" + se.getCause() + ")");
        }
    }

}

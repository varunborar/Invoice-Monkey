package org.invoice.monkey.Database;

import org.invoice.monkey.model.Invoice;
import org.invoice.monkey.model.InvoiceItem;

import java.sql.*;
import java.util.Vector;

public class InvoiceDB extends database{


    public void addInvoice(Invoice invoice)
    {
        String insertQuery = "INSERT INTO Invoice " +
                "VALUES(?,?,?,?,?,?,?,?,?);";
        Connection con;

        try{
            con = getCon();
            PreparedStatement query = con.prepareStatement(insertQuery);

            query.setLong(1, invoice.getRawInvoiceID());
            query.setLong(2, invoice.getCustomer().getRawCustomerID());
            query.setDate(3, invoice.getDate());
            query.setTime(4, invoice.getTime());
            query.setString(5, invoice.getType());
            query.setString(6, invoice.getDue());
            query.setString(7, invoice.getDescription());
            query.setFloat(8, invoice.getSubTotal());
            query.setFloat(9, invoice.getDiscount());

            query.executeUpdate();
            query.close();
            con.close();

            InvoiceItemDB iidb = new InvoiceItemDB();
            iidb.insertItems(invoice.getItemList(), invoice.getRawInvoiceID());



        }catch(SQLException se)
        {
            System.out.println("InvoiceDB Add:" + se.getClass().getName() + ": " + se.getMessage() +
                    "(" + se.getCause() + ")");
        }
    }

    public Invoice getInvoice(Long invoiceID)
    {
        String selectQuery = "SELECT * FROM Invoice " +
                "WHERE Invoice_ID = ?";
        Invoice invoice = new Invoice();

        try {
            Connection con = getCon();
            CustomerDB cdb = new CustomerDB();
            InvoiceItemDB idb = new InvoiceItemDB();

            PreparedStatement query = con.prepareStatement(selectQuery);
            query.setLong(1, invoiceID);

            ResultSet invoiceList = query.executeQuery();

            while(invoiceList.next())
            {
                invoice.setInvoiceID(invoiceID);
                invoice.setCustomer(cdb.getCustomer(invoiceList.getLong("Customer_ID")));
                invoice.setDate(invoiceList.getDate("Invoice_Date"));
                invoice.setTime(invoiceList.getTime("Invoice_Time"));
                invoice.setType(invoiceList.getString("Invoice_Type"));
                invoice.setDue(invoiceList.getString("Invoice_Due"));
                invoice.setDescription(invoiceList.getString("Invoice_Description"));
                invoice.setSubTotal(invoiceList.getFloat("Sub_Total"));
                invoice.setDiscount(invoiceList.getFloat("Discount"));

                invoice.addAllItems(idb.getItems(invoiceID));
            }

            query.close();
            con.close();
        }catch(SQLException se)
        {
            System.out.println("InvoiceDB Item add:" + se.getClass().getName() + ": " + se.getMessage() +
                    "(" + se.getCause() + ")");
        }

        return invoice;
    }

    public void deleteInvoice(Long invoiceID)
    {
        InvoiceItemDB idb = new InvoiceItemDB();
        idb.deleteItems(invoiceID);

        String deleteQuery = "DELETE FROM Invoice " +
                "WHERE Invoice_ID = ?";

        try{
            Connection con = getCon();
            PreparedStatement query = con.prepareStatement(deleteQuery);
            query.executeUpdate();
            query.close();
            con.close();

        }catch(SQLException se)
        {
            System.out.println("InvoiceDB Add:" + se.getClass().getName() + ": " + se.getMessage() +
                "(" + se.getCause() + ")");
        }
    }

    public Vector<Invoice> getInvoices(Integer limit, Boolean dueType) throws SQLException
    {
        Vector<Invoice> invoiceList = new Vector<>();

        String getQuery;

        if (limit == -1 && dueType)
        {
            getQuery = "SELECT * FROM Invoice " +
                    "WHERE Invoice_Due = 'DUE';";
        }
        else if (limit != -1 && dueType)
        {
            getQuery = "SELECT * FROM Invoice " +
                    "WHERE Invoice_Due = 'DUE' " +
                    "LIMIT ?;";
        }
        else if (limit == -1)
        {
            getQuery = "SELECT * FROM Invoice ";
        }
        else
        {
            getQuery = "SELECT * FROM Invoice " +
                    "LIMIT ?";
        }

        Connection con = getCon();
        PreparedStatement query = con.prepareStatement(getQuery);

        if(limit != -1)
            query.setInt(1, limit);

        ResultSet rs = query.executeQuery();

        while(rs.next())
        {
            Invoice invoice = new Invoice();

            invoice.setInvoiceID(rs.getLong("Invoice_ID"));
            invoice.setCustomer(new CustomerDB().getCustomer(rs.getLong("Customer_ID")));
            invoice.setDate(rs.getDate("Invoice_Date"));
            invoice.setTime(rs.getTime("Invoice_Time"));
            invoice.setType(rs.getString("Invoice_Type"));
            invoice.setDue(rs.getString("Invoice_Due"));
            invoice.setSubTotal(rs.getFloat("Sub_Total"));
            invoice.setDiscount(rs.getFloat("Discount"));
            invoice.calTotal();
            invoice.addAllItems(new InvoiceItemDB().getItems(invoice.getRawInvoiceID()));

            invoiceList.add(invoice);
        }

        return invoiceList;
    }

    public Vector<Invoice> getScheduledInvoices(Integer limit) throws SQLException
    {
        Vector<Invoice> invoiceList = new Vector<>();

        String getQuery;

        if (limit == -1)
        {
            getQuery = "SELECT * FROM Invoice " +
                    "WHERE Invoice_Type = 'Scheduled';";
        }
        else {
            getQuery = "SELECT * FROM Invoice " +
                    "WHERE Invoice_Type = 'Scheduled' " +
                    "LIMIT ?;";
        }


        Connection con = getCon();
        PreparedStatement query = con.prepareStatement(getQuery);

        if(limit != -1)
            query.setInt(1, limit);

        ResultSet rs = query.executeQuery();

        while(rs.next())
        {
            Invoice invoice = new Invoice();

            invoice.setInvoiceID(rs.getLong("Invoice_ID"));
            invoice.setCustomer(new CustomerDB().getCustomer(rs.getLong("Customer_ID")));
            invoice.setDate(rs.getDate("Invoice_Date"));
            invoice.setTime(rs.getTime("Invoice_Time"));
            invoice.setType(rs.getString("Invoice_Type"));
            invoice.setDue(rs.getString("Invoice_Due"));
            invoice.setSubTotal(rs.getFloat("Sub_Total"));
            invoice.setDiscount(rs.getFloat("Discount"));
            invoice.calTotal();
            invoice.addAllItems(new InvoiceItemDB().getItems(invoice.getRawInvoiceID()));

            invoiceList.add(invoice);
        }

        return invoiceList;
    }

    public void updateInvoiceDue(Long invoiceID) throws SQLException
    {
        String updateQuery = "UPDATE Invoice " +
                "SET Invoice_Due = 'PAID' " +
                "WHERE Invoice_ID = ?";

        Connection con = getCon();
        PreparedStatement query = con.prepareStatement(updateQuery);
        query.setLong(1, invoiceID);
        query.executeUpdate();
        query.close();
        con.close();
    }

    public void updateInvoiceSchedule(Long invoiceID) throws SQLException
    {
        String updateQuery = "UPDATE Invoice " +
                "SET Invoice_Type = 'Receipt' " +
                "WHERE Invoice_ID = ?";

        Connection con = getCon();
        PreparedStatement query = con.prepareStatement(updateQuery);
        query.setLong(1, invoiceID);
        query.executeUpdate();
        query.close();
        con.close();
    }
}


class InvoiceItemDB extends database{

    public void insertItems(Vector<InvoiceItem> itemSet, Long invoiceID)
    {
        String insertQuery = "INSERT INTO InvoiceItem VALUES(?,?,?)";
        Connection con;

        try{
            con = getCon();
            for(InvoiceItem i: itemSet)
            {
                PreparedStatement query = con.prepareStatement(insertQuery);
                query.setLong(1,invoiceID);
                query.setLong(2, i.getRawItemID());
                query.setInt(3, i.getQuantity());
                query.executeUpdate();
                query.close();
            }
            con.close();

        }catch(SQLException se)
        {
            System.out.println("InvoiceDB Add:" + se.getClass().getName() + ": " + se.getMessage() +
                    "(" + se.getCause() + ")");
        }
    }

    public Vector<InvoiceItem> getItems(Long invoiceID)
    {
        Vector<InvoiceItem> itemSet = new Vector<>();

        String getQuery = "SELECT * FROM InvoiceItem " +
                "WHERE Invoice_ID = ?";

        Connection con;

        try{
            con = getCon();
            PreparedStatement query= con.prepareStatement(getQuery);

            query.setLong(1, invoiceID);

            ResultSet itemList = query.executeQuery();
            while(itemList.next())
            {
                Long itemID = itemList.getLong("Item_ID");
                ItemDB idb = new ItemDB();
                InvoiceItem item = new InvoiceItem(idb.getItem(itemID));
                item.setQuantity(itemList.getInt("Item_Quantity"));
                itemSet.add(item);
            }

        }catch(SQLException se)
        {
            System.out.println("InvoiceDB Add:" + se.getClass().getName() + ": " + se.getMessage() +
                    "(" + se.getCause() + ")");
        }
        return itemSet;
    }

    public void deleteItems(Long invoiceID)
    {
        String deleteQuery = "DELETE FROM InvoiceItem " +
                "WHERE Invoice_ID = ?";

        try{
            Connection con = getCon();
            PreparedStatement query = con.prepareStatement(deleteQuery);

            query.setLong(1, invoiceID);

            query.executeUpdate();
            query.close();
            con.close();

        }catch(SQLException se)
        {
            System.out.println("InvoiceDB Add:" + se.getClass().getName() + ": " + se.getMessage() +
                    "(" + se.getCause() + ")");
        }
    }

}
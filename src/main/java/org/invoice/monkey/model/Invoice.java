package org.invoice.monkey.model;

import org.invoice.monkey.utils.DateTime;

import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class Invoice {

    private long invoiceID;
    private Customer customer;
    private Vector<InvoiceItem> itemList;
    private String type;
    private String due;
    private Float subTotal;
    private Float discount;
    private Float total;
    private Date date;
    private Time time;
    private String description;

    public Invoice()
    {
        invoiceID = DateTime.getCurrentDateTimeStamp();
        customer = null;
        itemList = new Vector<>();
        type = null;
        due = null;
        subTotal = 0.0f;
        discount = 0.0f;
        total = 0.0f;
        date = null;
        time = null;
        description = "";
    }

    public void setInvoiceID(Long invoiceID)
    {
        this.invoiceID = invoiceID;
    }

    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public void setDue(String due)
    {
        this.due = due;
    }

    public void setDiscount(float discount)
    {
        this.discount = discount;
    }


    public void setDate(Date date)
    {
        this.date = date;
    }

    public void setTime(Time time)
    {
        this.time = time;
    }

    public void setSubTotal(float subTotal)
    {
        this.subTotal = subTotal;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }


    public Long getRawInvoiceID()
    {
        return invoiceID;
    }

    public Customer getCustomer()
    {
        return customer;
    }

    public String getType()
    {
        return type;
    }

    public String getDue()
    {
        return due;
    }

    public Date getDate()
    {
        return date;
    }

    public Time getTime()
    {
        return time;
    }

    public String getDescription()
    {
        return this.description;
    }

    public Float getDiscount()
    {
        return discount;
    }

    public Vector<InvoiceItem> getItemList()
    {
        return itemList;
    }


    public Float getSubTotal()
    {
        calcSubTotal();
        return this.subTotal;
    }

    public void calcSubTotal()
    {
        subTotal = 0.0f;
        for (InvoiceItem i: itemList)
        {
            subTotal += i.getTotal();
        }
    }

    public Float getTotal()
    {
        calTotal();
        return this.total;
    }

    public void calTotal()
    {
        total = subTotal - discount;
    }

    public void addItem(InvoiceItem item)
    {
        itemList.add(item);
    }

    public void addAllItems(Vector<InvoiceItem> itemList)
    {
        this.itemList = itemList;
    }

    public void removeItem(InvoiceItem item)
    {
        itemList.remove(item);
    }

    public void clearItemList()
    {
        for(InvoiceItem i: itemList)
            itemList.remove(i);
    }

    public String toString()
    {
        return String.format("Invoice ID: %d, \n" +
                        " Customer: %s,\n" +
                        " Type: %s, Due: %s,\n" +
                        " Sub Total: %f,\n" +
                        " Discount: %f,\n" +
                        " Total: %f",
                this.invoiceID,
                this.customer.getName(),
                this.type, this.due,
                this.subTotal,
                this.discount,
                this.total);
    }

}

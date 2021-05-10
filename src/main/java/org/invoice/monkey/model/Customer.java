package org.invoice.monkey.model;

import org.invoice.monkey.App;

public class Customer {

    private final Long customerID;
    private String name;
    private String phoneNumber;
    private String email;
    private String address;

    public Customer(String name)
    {
        this.name = name;
        this.phoneNumber = null;
        this.email = null;
        this.address = null;
        this.customerID = 0l;
    }

    // GETTERS

    public Long getCustomerID()
    {
        return this.customerID;
    }

    public String getName()
    {
        return this.name;
    }

    public String getPhoneNumber()
    {
        if (phoneNumber != null)
        {
            return this.phoneNumber;
        }
        return "";
    }

    public String getEmail()
    {
        if (email != null)
        {
            return this.email;
        }
        return "";
    }

    public String getAddress()
    {
        if (address != null)
        {
           return this.address;
        }
        return "";
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

}

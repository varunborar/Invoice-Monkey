package org.invoice.monkey.model;


public class Customer {

    private Long customerID;
    private String name;
    private String phoneNumber;
    private String email;
    private String address;

    public Customer()
    {
        this.name = null;
        this.phoneNumber = null;
        this.email = null;
        this.address = null;
        this.customerID = 0L;
    }


    public Customer(String name) {
        this.name = name;
        this.phoneNumber = null;
        this.email = null;
        this.address = null;
        this.customerID = 0L;
    }

    // GETTERS

    public String getCustomerID() {
        return String.format("CU%06d", this.customerID);
    }

    public Long getRawCustomerID()
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

    public void setName(String name)
    {
        this.name = name;
    }

    public void setCustomerID(Long customerID)
    {
        this.customerID = customerID;
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

    public String toString()
    {
        return String.format("%s", this.name);
    }

    public String getDescription()
    {
        return String.format("%d %s", this.customerID, this.name);
    }
}

package org.invoice.monkey.model;

import java.sql.Date;

public class Expense {

    private Long expenseID;
    private Date date;
    private String description;
    private String category;
    private Float amount;

    public Expense()
    {
        date = null;
        description = "";
        category = "OTHERS";
        amount = 0.0f;
    }

    public void setExpenseID(Long expenseID)
    {
        this.expenseID = expenseID;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public void setAmount(Float amount)
    {
        this.amount = amount;
    }

    public Long getExpenseID()
    {
        return this.expenseID;
    }

    public Date getDate()
    {
        return this.date;
    }

    public String getDescription()
    {
        return this.description;
    }

    public String getCategory()
    {
        return this.category;
    }

    public Float getAmount()
    {
        return  this.amount;
    }
}

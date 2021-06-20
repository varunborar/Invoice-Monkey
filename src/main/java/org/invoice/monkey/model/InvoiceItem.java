package org.invoice.monkey.model;

public class InvoiceItem extends Item{

    private Integer quantity;

    public InvoiceItem()
    {
        quantity = 1;
    }

    public InvoiceItem(Item item)
    {
        super(item);
        quantity = 1;
    }

    public InvoiceItem(String name, float price)
    {
        super(name, price);
        quantity = 1;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public Integer getQuantity()
    {
        return this.quantity;
    }


    public Float getTotal()
    {
        return super.getPrice() * this.quantity;
    }

    public String getDescription()
    {
        return String.format("%s %s", getName(), getSize());
    }

    public String toString()
    {
        return String.format("%s", super.getName());
    }
}

package org.invoice.monkey.model;



public class Item {


    private Long itemID;
    private String name;
    private float price;
    private int inventory;
    private String type;

    public Item()
    {
        this.name = null;
        this.price = 0.0f;
        this.inventory = 0;
        this.type = null;
        this.itemID = 0L;
    }
    public Item(String name, float price, String type)
    {
        this.name = name;
        this.price = price;
        this.inventory = 0;
        this.type = type;
        this.itemID = 0L;
    }

    //GETTERS

    public String getItemID()
    {
        return String.format("IT%010d", this.itemID);
    }

    public Long getRawItemID()
    {
        return this.itemID;
    }

    public String getName()
    {
        return this.name;
    }

    public Float getPrice()
    {
        return this.price;
    }

    public Integer getInventory()
    {
        return this.inventory;
    }

    public String getType()
    {
        return this.type;
    }


    public void setItemID(Long itemID)
    {
        this.itemID = itemID;
    }

    public void updateName(String name)
    {
        this.name = name;
    }

    public void updatePrice(Float newPrice)
    {
        this.price = newPrice;
    }

    public void updateInventory(Integer newInventory)
    {
        this.inventory = newInventory;
    }

    public void updateType(String type)
    {
        this.type = type;
    }
}

package org.invoice.monkey.model;

import org.invoice.monkey.App;

public class Item {


    private final String itemID;
    private final String name;
    private float price;


    public Item(String name, float price)
    {
        this.name = name;
        this.price = price;
        this.itemID = Character.toUpperCase(name.charAt(0)) + Character.toUpperCase(name.charAt(1)) + Integer.toString(10000 + App.getConfiguration().getLastItemCount());
        App.getConfiguration().increaseItemCount();
    }

    //GETTERS

    public String getItemID()
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


    public void updatePrice(Float newPrice)
    {
        this.price = newPrice;
    }

}

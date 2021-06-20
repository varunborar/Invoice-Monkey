package org.invoice.monkey.model;



public class Item {


    private long itemID;
    private String name;
    private float price;

    // Optional Data
    private String size;
    private String sizeType;

    public Item()
    {
        this.name = null;
        this.price = 0.0f;
        this.itemID = 0L;
        this.size = null;
        this.sizeType = null;
    }

    public Item(Item item)
    {
        this.itemID = item.itemID;
        this.name = item.name;
        this.price = item.price;
        this.size = item.size;
        this.sizeType = item.sizeType;
    }

    public Item(String name, float price)
    {
        this.name = name;
        this.price = price;
        this.itemID = 0L;
        this.size = null;
        this.sizeType = null;
    }

    //GETTERS

    public String getItemID()
    {
        return String.format("IT%08d", this.itemID);
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

    public String getSize()
    {
        if(this.sizeType == null || this.sizeType.equals("N/A"))
            return "";
        return String.format("%s%s",this.size, this.sizeType);
    }

    public String getRawSize()
    {
        if (this.size == null || this.sizeType.equals("N/A") || this.sizeType == null)
            return "";
        return this.size;
    }

    public String getRawSizeType()
    {
        return this.sizeType;
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

    public void updateSize(String newSize)
    {
        this.size = newSize;
    }

    public void updateSizeType(String newSizeType)
    {
        this.sizeType = newSizeType;
    }

    @Override
    public String toString()
    {
        //return this.name;
        return String.format("%s", getName());
    }
}

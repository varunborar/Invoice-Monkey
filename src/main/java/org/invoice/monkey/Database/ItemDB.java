package org.invoice.monkey.Database;

import org.invoice.monkey.model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ItemDB extends database{


    public void addItem(Item item)
    {
        try{

            Connection con = getCon();
            String addItemSQLQuery = "INSERT INTO Item(Item_Name,Item_Price,Item_Type,Item_Inventory) VALUES(?,?,?,?);";
            PreparedStatement insert = con.prepareStatement(addItemSQLQuery);
            insert.setString(1,item.getName());
            insert.setFloat(2,item.getPrice());
            insert.setString(3,item.getType());
            insert.setInt(4,item.getInventory());

            insert.executeUpdate();
            insert.close();
            con.close();

        }catch(SQLException se)
        {
            System.out.println(se.getClass().getName() + ": " + se.getMessage());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public Item getItem(Long itemID)
    {
        Item item = new Item();
        try {
            Connection con = getCon();
            String getItemQuery = "SELECT * FROM Item " +
                                    "WHERE Item_ID = ?";
            PreparedStatement query = con.prepareStatement(getItemQuery);
            query.setLong(1,itemID);

            ResultSet items = query.executeQuery();

            while(items.next())
            {
                item.setItemID(items.getLong("Item_ID"));
                item.updateName(items.getString("Item_Name"));
                item.updatePrice(items.getFloat("Item_Price"));
                item.updateType(items.getString("Item_Type"));
                item.updateInventory(items.getInt("Item_Inventory"));
            }




        }catch(SQLException se)
        {
            System.out.println(se.getClass().getName() + ": " + se.getMessage());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return item;
    }

    public Vector<Item> getAllItems()
    {
        Vector<Item> itemList = new Vector<Item>();

        try {
            Connection con = getCon();
            String getItemQuery = "SELECT * FROM Item " +
                    "LIMIT ?";
            PreparedStatement query = con.prepareStatement(getItemQuery);
            query.setLong(1,100);

            ResultSet items = query.executeQuery();


            while(items.next())
            {
                Item item = new Item();
                item.setItemID(items.getLong("Item_ID"));
                item.updateName(items.getString("Item_Name"));
                item.updatePrice(items.getFloat("Item_Price"));
                item.updateType(items.getString("Item_Type"));
                item.updateInventory(items.getInt("Item_Inventory"));
                itemList.add(item);
            }





        }catch(SQLException se)
        {
            System.out.println(se.getClass().getName() + ": " + se.getMessage());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return itemList;
    }
}

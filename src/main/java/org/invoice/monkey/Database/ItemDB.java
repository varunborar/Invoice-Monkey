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
            String addItemSQLQuery = "INSERT INTO Item(Item_Name,Item_Price,Item_Type,Item_Size,Item_Size_Type) VALUES(?,?,?,?,?);";
            PreparedStatement insert = con.prepareStatement(addItemSQLQuery);
            insert.setString(1,item.getName());
            insert.setFloat(2,item.getPrice());
            insert.setString(3,item.getType());
            insert.setString(4,item.getRawSize());
            insert.setString(5, item.getRawSizeType());

            insert.executeUpdate();
            insert.close();
            con.close();

        }catch(SQLException se)
        {
            System.out.println(se.getClass().getName() + ": " + se.getMessage());
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
                item.updateSize(items.getString("Item_Size"));
                item.updateSizeType(items.getString("Item_Size_Type"));
            }
            query.close();
            con.close();
        }catch(SQLException se)
        {
            System.out.println(se.getClass().getName() + ": " + se.getMessage());
        }
        return item;
    }

    public Vector<Item> getAllItems(Integer limit)
    {
        Vector<Item> itemList = new Vector<>();

        try {
            Connection con = getCon();
            String getItemQuery;
            if(limit == -1)
            {
                getItemQuery = "SELECT * FROM Item;";
            }else
            {
                getItemQuery = "SELECT * FROM Item " +
                        "LIMIT ?;";
            }

            PreparedStatement query = con.prepareStatement(getItemQuery);
            if(limit != -1)
                query.setLong(1, limit);

            ResultSet items = query.executeQuery();
            while(items.next())
            {
                Item item = new Item();
                item.setItemID(items.getLong("Item_ID"));
                item.updateName(items.getString("Item_Name"));
                item.updatePrice(items.getFloat("Item_Price"));
                item.updateType(items.getString("Item_Type"));
                item.updateSize(items.getString("Item_Size"));
                item.updateSizeType(items.getString("Item_Size_Type"));
                itemList.add(item);
            }
            con.close();
        }catch(SQLException se)
        {
            System.out.println("Get All Items:" + se.getClass().getName() + ": " + se.getMessage());
        }
        return itemList;
    }

    public Vector<Item> getNext(Long start, Integer next)
    {
        Vector<Item> itemList = new Vector<>();

        try {
            Connection con = getCon();
            String getItemQuery = "SELECT * FROM Item " +
                        "WHERE ITEM_ID > ? " +
                        "LIMIT ?;";
            PreparedStatement query = con.prepareStatement(getItemQuery);
            query.setLong(1, start);
            query.setLong(2, next);

            ResultSet items = query.executeQuery();
            while(items.next())
            {
                Item item = new Item();
                item.setItemID(items.getLong("Item_ID"));
                item.updateName(items.getString("Item_Name"));
                item.updatePrice(items.getFloat("Item_Price"));
                item.updateType(items.getString("Item_Type"));
                item.updateSize(items.getString("Item_Size"));
                item.updateSizeType(items.getString("Item_Size_Type"));
                itemList.add(item);
            }
            con.close();
        }catch(SQLException se)
        {
            System.out.println("Get All Items:" + se.getClass().getName() + ": " + se.getMessage());
        }
        return itemList;
    }

    public Vector<Item> getSearchList(String searchString, Integer limit)
    {
        Vector<Item> searchResultList = new Vector<>();

        try{
            Connection con = getCon();

            String searchQuery = "SELECT * FROM Item " +
                                    "WHERE Item_Name LIKE ?" +
                                    "LIMIT ?;";
            PreparedStatement search = con.prepareStatement(searchQuery);
            search.setString(1, "%" + searchString + "%");
            search.setInt(2,limit);

            ResultSet items = search.executeQuery();

            while(items.next())
            {
                Item item = new Item();
                item.setItemID(items.getLong("Item_ID"));
                item.updateName(items.getString("ITEM_NAME"));
                item.updatePrice(items.getFloat("Item_Price"));
                item.updateType(items.getString("Item_Type"));
                item.updateSize(items.getString("Item_Size"));
                item.updateSizeType(items.getString("Item_Size_Type"));
                searchResultList.add(item);
            }

            con.close();

        }catch(SQLException se)
        {
            System.out.println(se.getClass().getName() +": " + se.getMessage());
        }

        return  searchResultList;
    }

    public void updateItem(Item item)
    {
        try{
            Connection con = getCon();

            String updateQuery = "UPDATE Item " +
                                    "SET Item_Name = ?, "+
                                    "Item_Price = ?,"+
                                    "Item_Type = ?," +
                                    "Item_Size = ?," +
                                    "Item_Size_Type = ? " +
                                    "WHERE Item_ID = ?;";

            PreparedStatement update = con.prepareStatement(updateQuery);
            update.setString(1, item.getName());
            update.setFloat(2, item.getPrice());
            update.setString(3, item.getType());
            update.setString(4, item.getRawSize());
            update.setString(5,item.getRawSizeType());
            update.setLong(6, item.getRawItemID());
            update.executeUpdate();
            con.close();

        }catch(SQLException se)
        {
            System.out.println(se.getClass().getName() +": " + se.getMessage());
        }
    }

    public void deleteItem(Item item)
    {
        try{
            Connection con = getCon();

            String deleteQuery = "DELETE FROM Item WHERE Item_ID = ?";
            PreparedStatement delete = con.prepareStatement(deleteQuery);
            delete.setLong(1, item.getRawItemID());

            delete.executeUpdate();
            con.close();
        }catch(SQLException se)
        {
            System.out.println(se.getClass().getName() +": " + se.getMessage());
        }
    }

}

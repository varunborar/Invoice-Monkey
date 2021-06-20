package org.invoice.monkey.Database;

import org.invoice.monkey.model.Expense;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ExpenseDB extends database{

    //TODO
    // Add Delete and search options
    public void addExpense(Expense expense)
    {
        String addExpenseQuery = "INSERT INTO Expense(Expense_Description, Expense_Category, Expense_Amount, Expense_Date) VALUES(?,?,?,?);";

        try{
            Connection con = getCon();
            PreparedStatement insert = con.prepareStatement(addExpenseQuery);

            insert.setString(1, expense.getDescription());
            insert.setString(2, expense.getCategory());
            insert.setFloat(3,expense.getAmount() );
            insert.setDate(4, expense.getDate());

            insert.executeUpdate();
            insert.close();
            con.close();
        }catch(SQLException se)
        {
            System.out.println(se.getClass().getName() + ": " + se.getMessage() + "(" + se.getCause() + ")");
        }
    }

    public Vector<Expense> getAllExpense(Integer limit)
    {
        Vector<Expense> expenseList = new Vector<>();

        try{
            Connection con = getCon();
            String query;

            if(limit == -1)
            {
                query = "SELECT * FROM Expense";
            }else{
                query = "SELECT * FROM Expense " +
                        "LIMIT ?;";
            }

            PreparedStatement Query = con.prepareStatement(query);

            if(limit != -1)
                Query.setInt(1, limit);

            ResultSet expenses = Query.executeQuery();

            while(expenses.next())
            {
                Expense expense = new Expense();
                expense.setExpenseID(expenses.getLong("Expense_ID"));
                expense.setDescription(expenses.getString("Expense_Description"));
                expense.setCategory(expenses.getString("Expense_Category"));
                expense.setAmount(expenses.getFloat("Expense_Amount"));
                expense.setDate(expenses.getDate("Expense_Date"));
                expenseList.add(expense);
            }
            Query.close();
            con.close();

        }catch(SQLException se)
        {
            System.out.println(se.getClass().getName() + ": " + se.getMessage() + "(" + se.getCause() + ")");
        }
        return expenseList;
    }
}

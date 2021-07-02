package org.invoice.monkey.utils.UI;

public class DatabaseConnectionException extends Exception{
    private String msg;

    public DatabaseConnectionException(String msg)
    {
        this.msg = msg;
    }

    @Override
    public String getMessage()
    {
        return msg;
    }
}

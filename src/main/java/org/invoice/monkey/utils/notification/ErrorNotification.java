package org.invoice.monkey.utils.notification;

public enum ErrorNotification {

    DatabaseConnectionError("Error Connecting to database", "Please check if the database server is online or try again later");



    private final String Title;
    private final String Message;

    ErrorNotification(String Title, String Message)
    {
        this.Title = Title;
        this.Message = Message;
    }

    public String getTitle() {
        return Title;
    }

    public String getMessage() {
        return Message;
    }

}

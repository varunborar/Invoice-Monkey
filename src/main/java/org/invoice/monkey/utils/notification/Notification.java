package org.invoice.monkey.utils.notification;


import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;


public class Notification {

    private Notification()
    {

    }

    public static void sendNotification(String Title, String Message) {
        Notifications notificationBuilder = Notifications.create()
                .text(Message)
                .title(Title)
                .hideAfter(Duration.seconds(5))
                .position(Pos.BOTTOM_RIGHT);
        notificationBuilder.show();
    }

    public static void sendErrorNotification(String Title, String Message)
    {
        Notifications notificationBuilder = Notifications.create()
                .text(Message)
                .title(Title)
                .hideAfter(Duration.seconds(5))
                .position(Pos.BOTTOM_RIGHT);
        notificationBuilder.showError();
    }

    public static void sendErrorNotification(ErrorNotification errorNotification)
    {
        Notifications notificationBuilder = Notifications.create()
                .text(errorNotification.getMessage())
                .title(errorNotification.getTitle())
                .hideAfter(Duration.seconds(5))
                .position(Pos.BOTTOM_RIGHT);
        notificationBuilder.showError();
    }
}

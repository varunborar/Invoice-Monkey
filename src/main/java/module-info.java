module org.invoice.monkey {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires java.sql;
    requires sqlite.jdbc;
    requires org.controlsfx.controls;
    requires com.jfoenix;
    requires layout;
    requires kernel;
    requires io;
    requires google.api.client;
    requires google.http.client.jackson2;
    requires google.oauth.client;
    requires google.oauth.client.java6;
    requires google.oauth.client.jetty;
    requires google.http.client;
    requires google.api.services.gmail.v1.rev83;
    requires java.mail;
    requires activation;


    opens org.invoice.monkey to javafx.fxml;
    opens org.invoice.monkey.model to javafx.base;
    opens org.invoice.monkey.controller to javafx.fxml;
    opens org.invoice.monkey.model.Configurations to javafx.base;
    opens org.invoice.monkey.utils.UI.Carousel to javafx.fxml;

    exports org.invoice.monkey;
    exports org.invoice.monkey.controller;
    exports org.invoice.monkey.model;
    exports org.invoice.monkey.model.Configurations;
    exports org.invoice.monkey.utils;
    exports org.invoice.monkey.utils.invoicegenerator.templates;
    exports org.invoice.monkey.utils.invoicegenerator.background;
    exports org.invoice.monkey.utils.notification;
    exports org.invoice.monkey.utils.UI.tableview;
    opens org.invoice.monkey.utils.UI.tableview to javafx.base;


}
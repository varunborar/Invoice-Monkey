module org.invoice.monkey {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires java.sql;
    requires sqlite.jdbc;
    requires org.controlsfx.controls;

    opens org.invoice.monkey to javafx.fxml;
    opens org.invoice.monkey.model to javafx.base;
    exports org.invoice.monkey;
    exports org.invoice.monkey.controller;
    opens org.invoice.monkey.controller to javafx.fxml;
}
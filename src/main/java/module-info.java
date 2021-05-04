module org.invoice.monkey {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;

    opens org.invoice.monkey to javafx.fxml;
    exports org.invoice.monkey;
    exports org.invoice.monkey.controller;
    opens org.invoice.monkey.controller to javafx.fxml;
}
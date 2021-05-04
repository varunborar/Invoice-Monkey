module org.invoice.monkey {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.invoice.monkey to javafx.fxml;
    exports org.invoice.monkey;
}
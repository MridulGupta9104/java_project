module com.example.javaproject2024 {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires java.mail;
    requires activation;

    opens com.example.javaproject2024 to javafx.fxml;
    exports com.example.javaproject2024;

    opens com.example.javaproject2024.customer_enrollment to javafx.fxml;
    exports com.example.javaproject2024.customer_enrollment;

    opens com.example.javaproject2024.worker_console to javafx.fxml;
    exports com.example.javaproject2024.worker_console;

    opens com.example.javaproject2024.measurements to javafx.fxml;
    exports com.example.javaproject2024.measurements;

    opens com.example.javaproject2024.receive_allocate_orders to javafx.fxml;
    exports com.example.javaproject2024.receive_allocate_orders;

    opens com.example.javaproject2024.see_workers to javafx.fxml;
    exports com.example.javaproject2024.see_workers;

    opens com.example.javaproject2024.measurements_explorer to javafx.fxml;
    exports com.example.javaproject2024.measurements_explorer;

    opens com.example.javaproject2024.order_delivery_panel to javafx.fxml;
    exports com.example.javaproject2024.order_delivery_panel;

    opens com.example.javaproject2024.dashboard to javafx.fxml;
    exports com.example.javaproject2024.dashboard;
}
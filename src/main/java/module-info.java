module com.techformate {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;
    requires mysql.connector.j;

    opens com.techformate.gastos.view to javafx.fxml;

    exports com.techformate.gastos.view;
    exports com.techformate.gastos.controller;
    opens com.techformate.gastos.controller to javafx.base, javafx.fxml;
    exports com.techformate.gastos.model;
    opens com.techformate.gastos.model to javafx.base, javafx.fxml;
    exports com.techformate.gastos.controller.dao;
    opens com.techformate.gastos.controller.dao to javafx.base, javafx.fxml;

}
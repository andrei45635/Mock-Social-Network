module com.example.lab6_socialnetwork_gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.lab6_socialnetwork_gui to javafx.graphics, javafx.fxml, javafx.base;
    opens com.example.lab6_socialnetwork_gui.dto to javafx.graphics, javafx.fxml, javafx.base;
    opens com.example.lab6_socialnetwork_gui.controller to javafx.graphics, javafx.fxml, javafx.base;

    exports com.example.lab6_socialnetwork_gui;
    exports com.example.lab6_socialnetwork_gui.controller;
}
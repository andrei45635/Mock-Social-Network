module com.example.lab6_socialnetwork_gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.lab6_socialnetwork_gui to javafx.fxml;
    exports com.example.lab6_socialnetwork_gui;
    exports com.example.lab6_socialnetwork_gui.controller;
    opens com.example.lab6_socialnetwork_gui.controller to javafx.fxml;
}
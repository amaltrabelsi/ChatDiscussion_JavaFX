module com.example.chatdiscussion {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    opens com.example.chatdiscussion to javafx.fxml;
    opens com.example.chatdiscussion.model to javafx.fxml;
    opens com.example.chatdiscussion.controller to javafx.fxml;
    opens com.example.chatdiscussion.service to javafx.fxml;

    exports com.example.chatdiscussion.controller;
    exports com.example.chatdiscussion.model;
    exports com.example.chatdiscussion.service;

    exports com.example.chatdiscussion;
}
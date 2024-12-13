module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo.controller;
    exports com.example.demo;
    exports com.example.demo.EventHandler;
    opens com.example.demo.EventHandler to javafx.fxml;
    exports com.example.demo.Entity;
    opens com.example.demo.Entity to javafx.fxml;
    exports com.example.demo.Level;
    opens com.example.demo.Level to javafx.fxml;
    exports com.example.demo.Level.levels;
    opens com.example.demo.Level.levels to javafx.fxml;
    exports com.example.demo.Level.LevelView;
    opens com.example.demo.Level.LevelView to javafx.fxml;
    exports com.example.demo.Entity.Boss;
    opens com.example.demo.Entity.Boss to javafx.fxml;
    exports com.example.demo.Entity.Entities;
    opens com.example.demo.Entity.Entities to javafx.fxml;
    exports com.example.demo.Entity.Player;
    opens com.example.demo.Entity.Player to javafx.fxml;
    exports com.example.demo.Entity.Enemy;
    opens com.example.demo.Entity.Enemy to javafx.fxml;
    exports com.example.demo.UserInterface;
    opens com.example.demo.UserInterface to javafx.fxml;
    exports com.example.demo.Entity.PowerUp;
    opens com.example.demo.Entity.PowerUp to javafx.fxml;
    exports com.example.demo.CollisionHandler;
    opens com.example.demo.CollisionHandler to javafx.fxml;
}
module javafx {
    requires  javafx.base;
    requires  javafx.fxml;
    requires  javafx.controls;
    requires  javafx.graphics;
    requires  javafx.media;
    requires  javafx.web;
    requires  javafx.swt;

    opens controller to javafx.base, javafx.fxml, javafx.controls, javafx.graphics;
    opens view to javafx.base, javafx.fxml, javafx.controls, javafx.graphics;

    exports view;
    exports controller;
    exports model;


    opens sample;
}
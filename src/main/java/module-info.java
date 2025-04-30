module dev.finashkin {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires org.apache.pdfbox;

    opens dev.finashkin to javafx.graphics;

    exports dev.finashkin;
    exports dev.finashkin.ui;
    exports dev.finashkin.controller;
    exports dev.finashkin.model;
    exports dev.finashkin.utils;
}

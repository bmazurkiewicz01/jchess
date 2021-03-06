module com.bmazurkiewicz01.jchess {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.bmazurkiewicz01.jchess to javafx.fxml;
    opens com.bmazurkiewicz01.jchess.controller to javafx.fxml;
    exports com.bmazurkiewicz01.jchess;
    exports com.bmazurkiewicz01.jchess.controller;
    exports com.bmazurkiewicz01.jchess.engine.piece;
    exports com.bmazurkiewicz01.jchess.engine.tile;
}
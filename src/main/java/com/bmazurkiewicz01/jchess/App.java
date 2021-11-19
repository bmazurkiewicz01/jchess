package com.bmazurkiewicz01.jchess;

import com.bmazurkiewicz01.jchess.view.View;
import com.bmazurkiewicz01.jchess.view.ViewSwitcher;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Pane(), 800, 800);

        ViewSwitcher.getInstance().setScene(scene);
        ViewSwitcher.getInstance().switchView(View.MAIN);

        stage.setTitle("Jchess");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
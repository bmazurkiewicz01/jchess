package com.bmazurkiewicz01.jchess.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.Objects;

public final class ViewSwitcher {

    private static Scene scene;
    private static ViewSwitcher instance;

    private ViewSwitcher() {
        if (instance != null) throw new IllegalStateException("Cannot create a new instance.");
    }

    public static ViewSwitcher getInstance() {
        if (instance == null) instance = new ViewSwitcher();
        return instance;
    }

    public void setScene(Scene scene) {
        ViewSwitcher.scene = scene;
    }

    public void switchView(View view) {
        Parent root = null;

        try {
            root = FXMLLoader.load(Objects.requireNonNull(ViewSwitcher.class.getResource(view.getFileName())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (root != null) scene.setRoot(root);
        else System.out.println("ViewSwitcher: root was null");
    }
}

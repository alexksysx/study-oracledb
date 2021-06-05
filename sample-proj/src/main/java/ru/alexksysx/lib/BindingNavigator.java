package ru.alexksysx.lib;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class BindingNavigator extends AnchorPane {
    BindingNavigatorController controller;

    public BindingNavigator() {
        super();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("bindingNavigator.fxml"));
            controller = new BindingNavigatorController();
            loader.setController(controller);
            Node load = loader.load();
            this.getChildren().add(load);
        } catch (IOException e) {

        }
    }
}

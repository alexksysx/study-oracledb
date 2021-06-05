package ru.alexksysx.lib;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class BindingNavigatorController <T> implements Initializable {
    @FXML
    private Button addElem;
    @FXML
    private Button removeElem;
    @FXML
    private Button next;
    @FXML
    private Button prev;
    @FXML
    private Button firstElement;
    @FXML
    private Button lastElement;
    @FXML
    private Label elemCount;
    @FXML
    private TextField elemInput;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

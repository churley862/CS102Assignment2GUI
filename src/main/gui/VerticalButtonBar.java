package gui;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class VerticalButtonBar extends VBox {
    // Constructor
    public VerticalButtonBar() {
        setFillWidth(true);
    }
    // Function to add a button that is passed into it
    public void addButton(Button button) {
        button.setMaxWidth(Double.MAX_VALUE);
        getChildren().add(button);
    }
    // function to add all of the buttons to a pane at once
    public void addAll(Button... buttons) {
        for(Button b : buttons) {
            addButton(b);
        }
    }
}

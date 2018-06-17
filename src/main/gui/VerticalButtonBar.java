package gui;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class VerticalButtonBar extends VBox {

    public VerticalButtonBar() {
        setFillWidth(true);
    }

    public void addButton(Button button) {
        button.setMaxWidth(Double.MAX_VALUE);
        getChildren().add(button);
    }

    public void addAll(Button... buttons) {
        for(Button b : buttons) {
            addButton(b);
        }
    }
}

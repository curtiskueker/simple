package org.curtis.javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class TasksController {
    @FXML
    private void handleButtonAction(ActionEvent event) {
        TextArea statusTextArea = (TextArea)TasksApplication.scene.lookup("#statusTextArea");
        statusTextArea.appendText("Button pressed\n");
    }
}

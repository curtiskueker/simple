package org.curtis.javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class TasksController {
    @FXML
    private void saveSettings(ActionEvent event) {
        appendText("Database settings saved");
    }

    private void appendText(String text) {
        TextArea statusTextArea = (TextArea)TasksApplication.scene.lookup("#statusTextArea");
        statusTextArea.appendText(text);
        statusTextArea.appendText("\n");
    }
}

package org.curtis.javafx;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;

public class TasksController {
    @FXML
    private void saveSettings() {
        appendText("Database settings saved");
    }

    @FXML
    private void showPassword() {
        if (checkboxOn("showPassword")) appendText("Show password selected");
        else appendText("Show password deselected");
    }

    @FXML
    private void showSchemaFileLocation() {
        boolean showSchemaLocation = checkboxOn("generateSchema");
        Node chooseSchemaLocationLink = getNode("chooseSchemaLocationLink");
        chooseSchemaLocationLink.setVisible(showSchemaLocation);
        Node schemaFileLocation = getNode("schemaFileLocation");
        schemaFileLocation.setVisible(showSchemaLocation);
    }

    @FXML
    private void chooseSchemaLocation() {
        FileChooser schemaLocationFileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("SQL Files (*.sql)", "*.sql");
        schemaLocationFileChooser.getExtensionFilters().add(extFilter);
        File file = schemaLocationFileChooser.showSaveDialog(TasksApplication.stage);
        if(file != null){
            TextField schemaFileLocation = (TextField)getNode("schemaFileLocation");
            schemaFileLocation.setText(file.getAbsolutePath());
        }
    }

    @FXML
    private void executeTables() {
        appendText("Database action executed");
    }

    private boolean checkboxOn(String controlName) {
        CheckBox checkBox = (CheckBox)getNode(controlName);

        return checkBox.isSelected();
    }

    private Node getNode(String nodeName) {
        return TasksApplication.scene.lookup("#" + nodeName);
    }

    private void appendText(String text) {
        TextArea statusTextArea = (TextArea)getNode("statusTextArea");
        statusTextArea.appendText(text);
        statusTextArea.appendText("\n");
    }
}

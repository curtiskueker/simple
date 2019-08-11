package org.curtis.javafx;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TasksController {
    private static final List<String> fromBoxes = new ArrayList<>(Arrays.asList("musicXmlFromBox", "dbFromBox", "lyFromBox"));
    private static final List<String> toBoxes = new ArrayList<>(Arrays.asList("musicXmlToBox", "dbToBox", "lyToBox", "pdfToBox"));

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
    private void showMusicXmlFrom() {
        showFromBox("musicXmlFromBox");
    }

    @FXML
    private void showDbFrom() {
        showFromBox("dbFromBox");
    }

    @FXML
    private void showLyFrom() {
        showFromBox("lyFromBox");
    }

    @FXML
    private void showMusicXmlTo() {
        showToBox("musicXmlToBox");
    }

    @FXML
    private void showDbTo() {
        showToBox("dbToBox");
    }

    @FXML
    private void showLyTo() {
        showToBox("lyToBox");
    }

    @FXML
    private void showPdfTo() {
        showToBox("pdfToBox");
    }

    @FXML
    private void chooseMusicXmlFromFile() {
        setFileLocationInTextField("musicXmlFromFile");
    }

    @FXML
    private void chooseLyFromFile() {
        setFileLocationInTextField("lyFromFile");
    }

    @FXML
    private void chooseMusicXmlToFile() {
        setFileLocationInTextField("musicXmlToFile");
    }

    @FXML
    private void chooseLyToFile() {
        setFileLocationInTextField("lyToFile");
    }

    @FXML
    private void choosePdfToFile() {
        setFileLocationInTextField("pdfToFile");
    }

    @FXML
    private void executeConvert() {
        appendText("Convert action executed");
    }

    @FXML
    private void executeTables() {
        appendText("Database action executed");
    }

    @FXML
    private void setLilypondLocation() {
        setFileLocationInTextField("lilypondLocation");
    }

    @FXML
    private void setPdfLocation() {
        setFileLocationInTextField("pdfLocation");
    }

    @FXML
    private void executeLyPdf() {
        appendText("Lilypond/PDF action executed");
    }

    private void setFileLocationInTextField(String textFieldName) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(TasksApplication.stage);
        if (file == null) return;

        TextField textField = (TextField)getNode(textFieldName);
        textField.setText(file.getAbsolutePath());
    }

    private boolean checkboxOn(String controlName) {
        CheckBox checkBox = (CheckBox)getNode(controlName);

        return checkBox.isSelected();
    }

    private Node getNode(String nodeName) {
        return TasksApplication.scene.lookup("#" + nodeName);
    }

    private void showFromBox(String boxName) {
        showBox(fromBoxes, boxName);
    }

    private void showToBox(String boxName) {
        showBox(toBoxes, boxName);
    }

    private void showBox(List<String> boxes, String boxName) {
        for (String box : boxes) getNode(box).setVisible(box.equals(boxName));
    }

    private void appendText(String text) {
        TextArea statusTextArea = (TextArea)getNode("statusTextArea");
        statusTextArea.appendText(text);
        statusTextArea.appendText("\n");
    }
}

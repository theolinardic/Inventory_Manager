/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Theodore Linardic
 */

package ucf.assignments;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class SaveFileWindowController {

    @FXML
    private ChoiceBox saveOptionBox;

    @FXML
    private TextField fileNameInput;

    @FXML
    private TextField saveLocationInput;

    @FXML
    public void initialize(){
        saveOptionBox.getItems().addAll("TSV", "HTML", "JSON");
        saveOptionBox.getSelectionModel().select("TSV");
    }

    @FXML
    public void saveButtonClicked(){
        InventoryManagerController.saveFile(saveLocationInput.getText(), fileNameInput.getText(), saveOptionBox.getSelectionModel().getSelectedItem().toString());

        Stage stage = (Stage) saveOptionBox.getScene().getWindow();

        stage.close();

    }
}

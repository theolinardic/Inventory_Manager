/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Theodore Linardic
 */

package ucf.assignments;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.util.ArrayList;

public class SaveFileWindowController {

    @FXML
    private ChoiceBox saveOptionBox;

    @FXML
    public void initialize(){
        saveOptionBox.getItems().addAll("TSV", "HTML", "JSON");
    }
}

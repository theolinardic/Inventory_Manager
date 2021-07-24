/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Theodore Linardic
 */

package ucf.assignments;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class EditItemValuesController {

    @FXML
    private TextField newItemNameField;

    @FXML
    private TextField newItemValueField;

    @FXML
    private TextField newItemSerialField;

    @FXML
    public void closeButtonClicked(ActionEvent actionEvent){
        Stage stage = (Stage) newItemNameField.getScene().getWindow();

        stage.close();

    }

    @FXML
    public void saveChangesButtonClicked(ActionEvent actionEvent){
        boolean isInList = false;
        ArrayList<String> AllSerialNumbers = InventoryManagerController.GetAllSerialNumbers();

        for(int i = 0; i < AllSerialNumbers.size(); i++){
            if(AllSerialNumbers.get(i).equals(newItemSerialField.getText())){
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("ErrorExistingSerialWindow.fxml"));

                    Scene scene = new Scene(root);

                    Stage stage = new Stage();

                    stage.setScene(scene);
                    stage.setTitle("Error!");
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                isInList = true;
            }
        }

        if(isInList == false){
            InventoryManagerController.editItem(newItemNameField.getText(), Double.parseDouble(newItemValueField.getText()), newItemSerialField.getText());
        }


    }
}

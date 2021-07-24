/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Theodore Linardic
 */

package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class InventoryManagerController {

    private static ArrayList<String> AllSerialNumbers;

    @FXML
    private TextField newItemNameField;

    @FXML
    private TextField newItemValueField;

    @FXML
    private TextField newItemSerialField;

    @FXML
    private TableView<ItemData> itemListDisplay;

    @FXML
    private TableColumn<ItemData, String> itemNameColumn;

    @FXML
    private TableColumn<ItemData, Double> itemValueColumn;

    @FXML
    private TableColumn<ItemData, String> itemSerialColumn;

    public static String currentlySelected, currentSerial;

    public static ArrayList<String> GetAllSerialNumbers(){
        return AllSerialNumbers;
    }

    public static void SetAllSerialNumbers(ArrayList<String> newList){
        AllSerialNumbers = newList;
    }

    public InventoryManagerController() {
        AllSerialNumbers = new ArrayList<String>();

    }

    @FXML
    public void closeButtonClicked(ActionEvent actionEvent) {
        //This function will close the program.
        System.exit(0);

    }

    @FXML
    public void addItemButtonClicked(ActionEvent actionEvent) {
        if(AllSerialNumbers.contains(newItemSerialField.getText())){
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
        }
        else{
            AllSerialNumbers.add(newItemSerialField.getText());

            //ObservableList<ItemData> items = FXCollections.observableArrayList();
            //items = itemListDisplay.getItems();

            ItemData newItem = new ItemData(newItemNameField.getText(), Double.parseDouble(newItemValueField.getText()), newItemSerialField.getText());
            itemListDisplay.getItems().add(newItem);
        }

    }

    @FXML
    public void editItemButtonClicked(ActionEvent actionEvent){
        ObservableList<ItemData> selectedItem = FXCollections.observableArrayList();
        selectedItem = itemListDisplay.getSelectionModel().getSelectedItems();

        currentlySelected = selectedItem.get(0).getItemName().toString();
        currentSerial = selectedItem.get(0).getItemSerial().toString();

        try {
            Parent root = FXMLLoader.load(getClass().getResource("EditItemValues.fxml"));

            Scene scene = new Scene(root);

            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setTitle("Edit Item Values");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteItemButtonClicked(ActionEvent actionEvent){
        ObservableList<ItemData> selectedItem = FXCollections.observableArrayList();
        selectedItem = itemListDisplay.getSelectionModel().getSelectedItems();
        String serialNumber = selectedItem.get(0).getItemSerial();

        for(int i = 0; i < itemListDisplay.getItems().size(); i++){
            if (itemListDisplay.getItems().get(i).getItemSerial().equals(serialNumber)){

                itemListDisplay.getItems().remove(i);
                AllSerialNumbers.remove(serialNumber);
                break;
            }
        }
    }

    @FXML
    public void saveButtonClicked(ActionEvent actionEvent){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("SaveFileWindow.fxml"));

            Scene scene = new Scene(root);

            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setTitle("Save File");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void editItem(String newItemName, double newItemValue, String newItemSerial){
        AllSerialNumbers.add(newItemSerial);
        AllSerialNumbers.remove(currentSerial);



    }


    @FXML
    public void initialize(){
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<ItemData, String>("itemName"));
        itemValueColumn.setCellValueFactory(new PropertyValueFactory<ItemData, Double>("itemValue"));
        itemSerialColumn.setCellValueFactory(new PropertyValueFactory<ItemData, String>("itemSerial"));
    }

}

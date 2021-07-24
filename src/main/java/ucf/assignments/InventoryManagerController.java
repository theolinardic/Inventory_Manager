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

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class InventoryManagerController {

    private static ArrayList<String> AllSerialNumbers, AllItemNames;
    private static ArrayList<Double> AllValues;

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
    public static double currentValue;

    public static ArrayList<String> GetAllSerialNumbers(){
        return AllSerialNumbers;
    }

    public static void SetAllSerialNumbers(ArrayList<String> newList){
        AllSerialNumbers = newList;
    }

    public InventoryManagerController() {
        AllSerialNumbers = new ArrayList<String>();
        AllItemNames = new ArrayList<String>();
        AllValues = new ArrayList<Double>();

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
            AllItemNames.add(newItemNameField.getText());
            AllValues.add(Double.parseDouble(newItemValueField.getText()));

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
        currentValue = selectedItem.get(0).getItemValue();

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

    @FXML
    public void loadButtonClicked(ActionEvent actionEvent){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("LoadFileWindow.fxml"));

            Scene scene = new Scene(root);

            Stage stage = new Stage();

            stage.setScene(scene);
            stage.setTitle("Load File");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void sortHighToLowButtonClicked(ActionEvent actionEvent){


        for (int i = 0; i < AllValues.size(); i++){
            ItemData newItem = new ItemData(AllItemNames.get(i), AllValues.get(i), AllSerialNumbers.get(i));
            itemListDisplay.getItems().add(newItem);
        }

        itemListDisplay.refresh();

    }


    public static void editItem(String newItemName, double newItemValue, String newItemSerial){
        AllSerialNumbers.remove(currentSerial);
        AllValues.remove(currentValue);
        AllItemNames.remove(currentlySelected);

        AllItemNames.add(newItemName);
        AllValues.add(newItemValue);
        AllSerialNumbers.add(newItemSerial);
    }

    public static void saveFile(String saveLocation, String fileName, String fileFormat){

        String finalSaveLocation = saveLocation + "\\" + fileName + ".";
        if(fileFormat.equals("JSON")){
            finalSaveLocation += "json";

            try{
                PrintWriter fileWriter = new PrintWriter(finalSaveLocation, "UTF-8");

                fileWriter.println("{");
                fileWriter.println("\t\"Inventory Items\": [");


                for(int i = 0; i < AllItemNames.size();i++){
                    fileWriter.println("{");
                    fileWriter.println("\"itemName\": \"" + AllItemNames.get(i).toString() + "\",");
                    fileWriter.println("\"itemValue\": " + AllValues.get(i).toString() + ",");
                    fileWriter.println("\"itemSerialNumber\": \"" + AllSerialNumbers.get(i).toString() + "\"");
                    fileWriter.print("}");
                    if(i != AllItemNames.size()-1){
                        fileWriter.println(",\n");
                    }
                }
                fileWriter.println("\n]\n}");

                fileWriter.close();
            }
            catch (IOException e){

                System.out.print("Error saving file.");
            }

        }
        else if(fileFormat.equals("HTML")){
            finalSaveLocation += "html";

            try{
                PrintWriter fileWriter = new PrintWriter(finalSaveLocation, "UTF-8");

                fileWriter.println("<table border=\"1\" style=\"background-color:#FFFFFF;border-collapse:collapse;border:1px solid FFCC00;color:000000;width:100%\" cellpadding=\"3\" cellspacing=\"3\">");
                fileWriter.println("\t<tr>");
                fileWriter.println("\t\t<td>Item Names</td>");
                fileWriter.println("\t\t<td>Item Values</td>");
                fileWriter.println("\t\t<td>Item Serial Numbers</td>");
                fileWriter.println("\t</tr>");

                for(int i = 0; i < AllItemNames.size();i++){
                    fileWriter.println("\t<tr>");
                    fileWriter.println("<td>" + AllItemNames.get(i).toString() + "</td>");
                    fileWriter.println("<td>" + AllValues.get(i).toString() + "</td>");
                    fileWriter.println("<td>" + AllSerialNumbers.get(i).toString() + "</td>");
                    fileWriter.println("\t</tr>");
                }
                fileWriter.println("</table>");

                fileWriter.close();
            }
            catch (IOException e){

                System.out.print("Error saving file.");
            }

        }
        else if(fileFormat.equals("TSV")){
            finalSaveLocation += "txt";

            try{
                PrintWriter fileWriter = new PrintWriter(finalSaveLocation, "UTF-8");

                for(int i = 0; i < AllItemNames.size();i++){
                    fileWriter.println(AllItemNames.get(i) + "\t" + AllValues.get(i) + "\t" + AllSerialNumbers.get(i));
                }

                fileWriter.close();
            }
            catch (IOException e){

                System.out.print("Error saving file.");
            }
        }

    }

    public static void loadFile(String fileName){

        if(fileName.contains(".html")){
            try{
                File loadedFile = new File(fileName);
                Scanner fileScanner = new Scanner(loadedFile);

                AllItemNames.clear();
                AllValues.clear();
                AllSerialNumbers.clear();

                fileScanner.nextLine();
                fileScanner.nextLine();
                fileScanner.nextLine();
                fileScanner.nextLine();
                fileScanner.nextLine();
                fileScanner.nextLine();
                fileScanner.nextLine();

                while(fileScanner.hasNextLine()){

                    String nextLine = fileScanner.nextLine();
                    String[] partsOfLine = nextLine.split("\t", 3);
                    AllItemNames.add(partsOfLine[0]);
                    AllValues.add(Double.parseDouble(partsOfLine[1]));
                    AllSerialNumbers.add(partsOfLine[2]);

                }

            }
            catch(Exception e){

            }
        }
        else if(fileName.contains(".json")){

        }
        else if(fileName.contains(".txt")){
            try{
                File loadedFile = new File(fileName);
                Scanner fileScanner = new Scanner(loadedFile);

                AllItemNames.clear();
                AllValues.clear();
                AllSerialNumbers.clear();

                while(fileScanner.hasNextLine()){

                    String nextLine = fileScanner.nextLine();
                    String[] partsOfLine = nextLine.split("\t", 3);
                    AllItemNames.add(partsOfLine[0]);
                    AllValues.add(Double.parseDouble(partsOfLine[1]));
                    AllSerialNumbers.add(partsOfLine[2]);

                }

            }
            catch(Exception e){

            }

        }
    }



    @FXML
    public void initialize(){
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<ItemData, String>("itemName"));
        itemValueColumn.setCellValueFactory(new PropertyValueFactory<ItemData, Double>("itemValue"));
        itemSerialColumn.setCellValueFactory(new PropertyValueFactory<ItemData, String>("itemSerial"));
    }

}

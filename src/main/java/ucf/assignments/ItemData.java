/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Theodore Linardic
 */

package ucf.assignments;

import javafx.beans.property.SimpleStringProperty;

public class ItemData {
    private SimpleStringProperty itemName, itemSerial;
    private double itemValue;

    public ItemData(String itemName, double itemValue, String itemSerial){
        this.itemName = new SimpleStringProperty(itemName);
        this.itemValue = itemValue;
        this.itemSerial = new SimpleStringProperty(itemSerial);
    }

    public String getItemName() {
        return itemName.get();
    }

    public void setItemName(String itemName) {
        this.itemName.set(itemName);
    }

    public String getItemSerial() {
        return itemSerial.get();
    }

    public void setItemSerial(String itemSerial) {
        this.itemSerial.set(itemSerial);
    }

    public double getItemValue() {
        return itemValue;
    }

    public void setItemValue(double itemValue) {
        this.itemValue = itemValue;
    }
}

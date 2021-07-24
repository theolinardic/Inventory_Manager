package ucf.assignments;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoadFileWindowController {

    @FXML
    private TextField fileLocationInput;

    @FXML
    public void loadButtonClicked(ActionEvent actionEvent){
        InventoryManagerController.loadFile(fileLocationInput.getText());

        Stage stage = (Stage) fileLocationInput.getScene().getWindow();

        stage.close();

    }
}

package View;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import java.util.Map;

import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;

public class ControllerGUI {
    @FXML
    private ListView<String> examplesList;
    @FXML
    private Label label;
    @FXML
    void initializeExamplesList(MouseEvent event){
        Map<Integer,Command> menu = Interpreter.getMenu().getMenu();
        ObservableList<String> examples = FXCollections.observableArrayList();
        for(Integer key:menu.keySet()){
            examples.add(menu.get(key).getDescription());
        }
        examplesList.setItems(examples);
        examplesList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        examplesList.getFocusModel().focus(2);
    }

    @FXML
    void execute(MouseEvent event){
        label.setText(examplesList.getSelectionModel().getSelectedItem());
    }
}

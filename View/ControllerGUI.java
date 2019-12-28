package View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.Map;

public class ControllerGUI {
    @FXML
    private ListView examplesList;

    public void initializeExamplesList(TextMenu menuFromUser){
        Map<String,Command> menu = menuFromUser.getMenu();
        ObservableList<String> examples = FXCollections.observableArrayList();
        for(String key:menu.keySet()){
            examples.add(menu.get(key).getDescription());
        }
        examplesList.setItems(examples);
    }
}

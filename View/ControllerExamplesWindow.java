package View;

import Controller.Controller;
import Domain.ProgramState.ProgramState;
import Exceptions.MyException;
import Interfaces.RepositoryInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ControllerExamplesWindow implements Initializable {
    @FXML
    private ListView<String> examplesList;
    @FXML
    private Button selectButton;
    private int currentIndex;
    private Controller controllerOfCurrentExample;
    private ControllerGUI mainController;

    @FXML
    void initializeExamplesList(){
        Map<Integer,Command> menu = Interpreter.getMenu().getMenu();
        ObservableList<String> examples = FXCollections.observableArrayList();
        for(Integer key:menu.keySet()){
            examples.add(menu.get(key).getDescription());
        }
        examplesList.setItems(examples);
        examplesList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        examplesList.getFocusModel().focus(2);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeExamplesList();
        currentIndex=-1;
    }

    public void setMainController(ControllerGUI controllerFromUser){
        mainController=controllerFromUser;
    }
    public void execute(javafx.scene.input.MouseEvent mouseEvent) {
        int key = examplesList.getSelectionModel().getSelectedIndex()+1;
        try {
            if (key != currentIndex) {
                RunExample exampleToRun = (RunExample) Interpreter.getMenu().getCommand(key); //we added no exit command so we know for sure that this will be a RunExample
                controllerOfCurrentExample = exampleToRun.getController();
                controllerOfCurrentExample.typecheck();
                currentIndex=key;
                mainController.setControllerExecution(controllerOfCurrentExample);
                mainController.execute();
            }
        }catch(MyException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}

package View;

import Controller.Controller;
import Domain.ProgramState.ProgramState;
import Domain.Values.StringValue;
import Exceptions.MyException;
import Interfaces.RepositoryInterface;
import Interfaces.Value;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.BufferedReader;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.scene.control.cell.PropertyValueFactory;

public class ControllerGUI implements Initializable {
    private Controller controllerOfCurrentExample;
    private List<ProgramState> programStates;
    @FXML
    private ListView<String> outputList;
    @FXML
    private ListView<String> fileTable;
    @FXML
    private ListView<String> executionStackList;
    @FXML
    private ListView<String> threadIDList;
    @FXML
    private TextField noOfProgramStatesField;
    @FXML
    private TableView<TableValue<String,Value>> symbolTableView;
    @FXML
    private TableColumn<TableValue<String,Value>,String> name;
    @FXML
    private TableColumn<TableValue<String,Value>,String> variableValue;
    @FXML
    private TableView<TableValue<Integer,Value>> heapTableView;
    @FXML
    private TableView<TableValue<Integer,Integer>> lockTableView;
    @FXML
    private TableColumn<TableValue<Integer,Value>,String> address;
    @FXML
    private TableColumn<TableValue<Integer,Value>,String> value;
    @FXML
    private TableColumn<TableValue<Integer,Integer>,String> location;
    @FXML
    private TableColumn<TableValue<Integer,Integer>,String> valueLocation;

    public void setControllerExecution(Controller controllerFromUser){
        controllerOfCurrentExample=controllerFromUser;
    }
    @FXML
    void displayStateOfSelectedProgramState(){
        int index = threadIDList.getSelectionModel().getSelectedIndex();
        if(index >=0) {
            ProgramState state = programStates.get(index);
            //display execution stack
            executionStackList.setItems(state.getStack().getExecutionStackItems());
            //display symbol table
            name.setCellValueFactory(new PropertyValueFactory<TableValue<String, Value>, String>("name"));
            variableValue.setCellValueFactory(new PropertyValueFactory<TableValue<String, Value>, String>("value"));
            symbolTableView.setItems(state.getSymbolTable().getTableValues());
        }
    }
    public ObservableList<String> getFileTableValues(ProgramState state) {
        Map<StringValue,BufferedReader> fileTableItems = state.getFileTable().getContent();
        ObservableList<String> list = FXCollections.observableArrayList();
        for(StringValue key: fileTableItems.keySet()){
            list.add(key.getValue());
        }
        return list;
    }
    @FXML
    void  execute( ){
        try {
            //one step execution
            ExecutorService executor = Executors.newFixedThreadPool(2);
            controllerOfCurrentExample.setExecutor(executor);
            RepositoryInterface repository = controllerOfCurrentExample.getRepository();
            List<ProgramState> programList = controllerOfCurrentExample.removeCompletedPrograms(repository.getProgramsList());
            if (programList.size() > 0) {
                controllerOfCurrentExample.oneStepForAllPrograms(programList);
                //programList = controllerOfCurrentExample.removeCompletedPrograms(repository.getProgramsList());
            }
            if(programList.size()==0){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Execution of this example is finished!");
                alert.showAndWait();
            }
            executor.shutdownNow();
            repository.setProgramsList(programList);
            displayCurrentState(repository);
        }catch(MyException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    private void displayCurrentState(RepositoryInterface repository) {
        programStates = repository.getProgramsList();
        int noOfProgramStates = programStates.size();
        //noOfProgramStatesField.setText(Integer.toString(noOfProgramStates));
        //display program states
        ObservableList<String> examples = FXCollections.observableArrayList();
        for (ProgramState state : programStates) {
            examples.add("Thread ID: "+Integer.toString(state.getID().intValue()));
        }
        threadIDList.setItems(examples);
        threadIDList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ProgramState state = programStates.get(0);
        //display heap table
        address.setCellValueFactory(new PropertyValueFactory<TableValue<Integer,Value>,String>("name"));
        value.setCellValueFactory(new PropertyValueFactory<TableValue<Integer,Value>,String>("value"));
        heapTableView.setItems(state.getHeap().getHeapValues());
        //display output
        outputList.setItems(state.getOutput().getOutputItems());
        //display file table
        fileTable.setItems(getFileTableValues(state));
        //display lock table
        location.setCellValueFactory(new PropertyValueFactory<TableValue<Integer,Integer>,String>("name"));
        valueLocation.setCellValueFactory(new PropertyValueFactory<TableValue<Integer,Integer>,String>("value"));
        lockTableView.setItems(state.getLockTable().getLockValues());
        displayStateOfSelectedProgramState();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

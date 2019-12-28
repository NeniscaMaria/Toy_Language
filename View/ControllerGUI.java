package View;

import Controller.Controller;
import Domain.ProgramState.ExecutionStack;
import Domain.ProgramState.ProgramState;
import Domain.ProgramState.SymbolTable;
import Domain.Values.StringValue;
import Exceptions.MyException;
import Interfaces.RepositoryInterface;
import Interfaces.StatementInterface;
import Interfaces.Value;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class ControllerGUI {
    private ExecutorService executor;
    private Controller controllerOfCurrentExample;
    private int currentIndex;
    private List<ProgramState> programStates;
    @FXML
    private ListView<String> examplesList;
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
    private TableColumn<TableValue<Integer,Value>,String> address;
    @FXML
    private TableColumn<TableValue<Integer,Value>,String> value;
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
    void displayStateOfSelectedProgramState(){
        int index = threadIDList.getSelectionModel().getSelectedIndex();
        ProgramState state = programStates.get(index);
        //display heap table
        address.setCellValueFactory(new PropertyValueFactory<TableValue<Integer,Value>,String>("name"));
        value.setCellValueFactory(new PropertyValueFactory<TableValue<Integer,Value>,String>("value"));
        heapTableView.setItems(state.getHeap().getHeapValues());
        //display output
        outputList.setItems(state.getOutput().getOutputItems());
        //display file table
        fileTable.setItems(getFileTableValues(state));
        //display execution stack
        executionStackList.setItems(state.getStack().getExecutionStackItems());
        //display symbol table
        name.setCellValueFactory(new PropertyValueFactory<TableValue<String,Value>,String>("name"));
        variableValue.setCellValueFactory(new PropertyValueFactory<TableValue<String,Value>,String>("value"));
        symbolTableView.setItems(state.getSymbolTable().getTableValues());
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
    void execute(MouseEvent event){
        int key = examplesList.getSelectionModel().getSelectedIndex()+1;
        try {
            if (key != currentIndex) {
                RunExample exampleToRun = (RunExample) Interpreter.getMenu().getCommand(key); //we added no exit command so we know for sure that this will be a RunExample
                controllerOfCurrentExample = exampleToRun.getController();
                controllerOfCurrentExample.typecheck();
                currentIndex=key;
            }

            //one step execution
            executor = Executors.newFixedThreadPool(2);
            controllerOfCurrentExample.setExecutor(executor);
            RepositoryInterface repository = controllerOfCurrentExample.getRepository();
            List<ProgramState> programList = controllerOfCurrentExample.removeCompletedPrograms(repository.getProgramsList());
            if (programList.size() > 0) {
                controllerOfCurrentExample.oneStepForAllPrograms(programList);
                programList = controllerOfCurrentExample.removeCompletedPrograms(repository.getProgramsList());
            }
            executor.shutdownNow();
            //Here the repository still contains at least one Completed Prg
            // and its List<PrgState> is not empty. Note that oneStepForAllPrg calls the method
            //setPrgList of repository in order to change the repository
            // update the repository state
            repository.setProgramsList(programList);
            displayCurrentState(repository);
            //check why it doesn;t work at last execution step (displaying)
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
        noOfProgramStatesField.setText(Integer.toString(noOfProgramStates));
        //display program states
        ObservableList<String> examples = FXCollections.observableArrayList();
        for (ProgramState state : programStates) {
            examples.add("Thread ID: "+Integer.toString(state.getID()));
        }
        threadIDList.setItems(examples);
        threadIDList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }
}

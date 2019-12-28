package Interfaces;

import Domain.ProgramState.ProgramState;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public interface MyListInterface<T> {
    void add(Value valueFromUser);
    ArrayList<T> getList();
    ObservableList<String> getOutputItems();
}

package Interfaces;

import javafx.collections.ObservableList;

public interface MyStackInterface<T> {
    T pop();
    void push(T v);
    boolean isEmpty();
    ObservableList<String> getExecutionStackItems();
}

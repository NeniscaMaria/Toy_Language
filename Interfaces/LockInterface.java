package Interfaces;

import View.TableValue;
import javafx.collections.ObservableList;

public interface LockInterface<A,B> {
    int add(B value);
    B getValue(A address);
    boolean isAddress(A address);
    void update(A address, B valueOfExpression);
    ObservableList<TableValue<A, B>> getLockValues();
    B lookUp(A index);
}

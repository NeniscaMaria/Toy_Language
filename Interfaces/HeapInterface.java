package Interfaces;

import View.TableValue;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Map;

public interface HeapInterface<A,B> {
    int add(B value);
    B getValue(A address);
    boolean isAddress(A address);
    void update(A address, B valueOfExpression);
    void setContent(Map<A,B> newContent);
    Map<A,B> getContent();
    List<A> getInAddresses();
    ObservableList<TableValue<A, B>> getHeapValues();
}

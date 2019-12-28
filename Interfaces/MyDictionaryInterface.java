package Interfaces;

import View.TableValue;
import javafx.collections.ObservableList;

import java.util.concurrent.ConcurrentHashMap;

public interface MyDictionaryInterface<A,B> {
    boolean isDefined(A id);
    B getValue(A id);
    void update(A id,B val);
    B lookup(A id);
    void delete(A id);
    ConcurrentHashMap<A,B> getContent();
    MyDictionaryInterface<A,B> clone();
    ObservableList<TableValue<A,B>> getTableValues();
}

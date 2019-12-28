package Domain.ProgramState;
import Domain.Values.StringValue;
import Exceptions.MyException;
import Interfaces.MyDictionaryInterface;
import View.TableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FileTable implements MyDictionaryInterface<StringValue,BufferedReader> {
    private ConcurrentHashMap<StringValue, BufferedReader> fileTable;
    public FileTable(){
        fileTable=new ConcurrentHashMap<>();
    }
    public void update(StringValue id, BufferedReader val){
        fileTable.put(id,val);
    }
    public boolean isDefined(StringValue id){
        return fileTable.containsKey(id);
    }
    public BufferedReader getValue(StringValue id){
        return fileTable.get(id);
    }
    public BufferedReader lookup(StringValue id){
        if(isDefined(id))
            return fileTable.get(id);
        else
            throw new MyException("File "+id+" is not opened!");
    }
    public void delete(StringValue id){
        fileTable.remove(id);
    }
    @Override
    public String toString(){
        StringBuilder result = new StringBuilder("FileTable:" + System.lineSeparator());
        for(StringValue key:fileTable.keySet()){
            result.append(key.getValue()).append(System.lineSeparator());
        }
        return result.toString();
    }
    public ConcurrentHashMap<StringValue, BufferedReader> getContent(){
        return fileTable;
    }
    public MyDictionaryInterface<StringValue,BufferedReader> clone(){
        return new FileTable();
    }

    @Override
    public ObservableList<TableValue<StringValue, BufferedReader>> getTableValues() {
        ObservableList<TableValue<StringValue,BufferedReader>> list = FXCollections.observableArrayList();
        for(StringValue key: fileTable.keySet()){
            list.add(new TableValue<StringValue,BufferedReader>(key,fileTable.get(key)));
        }
        return list;
    }
}

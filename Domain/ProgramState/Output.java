package Domain.ProgramState;
import Interfaces.MyListInterface;
import Interfaces.Value;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Output implements MyListInterface<Value> {
    private ArrayList<Value> list;
    public Output(){
        list=new ArrayList<Value>();
    }
    public ArrayList<Value> getList(){
        return list;
    }
    public ObservableList<String> getOutputItems(){
        ObservableList<String> listItems = FXCollections.observableArrayList();
        for(Value val: list){
            listItems.add(val.toString());
        }
        return listItems;
    }
    public void add(Value valueFromUser){
        list.add(valueFromUser);
    }
    @Override
    public String toString(){
        StringBuilder result= new StringBuilder("Output:"+System.lineSeparator());
        for(Value val : list)
            result.append(val.toString()).append(System.lineSeparator());
        return result.toString();
    }
}

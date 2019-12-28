package Domain;

import Exceptions.MyException;
import Interfaces.MyDictionaryInterface;
import Interfaces.Type;
import View.TableValue;
import javafx.collections.ObservableList;

import java.util.concurrent.ConcurrentHashMap;

public class TypeChecker implements MyDictionaryInterface<String , Type> {
    private ConcurrentHashMap<String, Type> table;
    public TypeChecker(){
        table=new ConcurrentHashMap<>();
    }
    public TypeChecker(ConcurrentHashMap<String, Type> typeEnvironment){
        table=typeEnvironment;
    }
    public boolean isDefined(String id){
        return table.containsKey(id);
    }
    public Type getValue(String id){
        return table.get(id);
    }
    public void update(String id,Type val){
        table.put(id,val);
    }
    public Type lookup(String id){
        if(isDefined(id))
            return table.get(id);
        else
            throw new MyException("Variable "+id+" is not defined!");
    }
    public void delete(String id){
        table.remove(id);
    }
    @Override
    public String toString(){
        StringBuilder result= new StringBuilder("Type Environment:"+System.lineSeparator());
        for(String key:table.keySet())
            result.append(key).append("-->").append(table.get(key).toString()).append(System.lineSeparator());
        return result.toString();
    }
    public ConcurrentHashMap<String,Type> getContent(){
        return table;
    }
    public MyDictionaryInterface<String,Type> clone(){
        ConcurrentHashMap<String,Type> clone = new ConcurrentHashMap<>();
        for(String key : table.keySet())
            clone.put(key, table.get(key));
        return new TypeChecker(clone);
    }

    @Override
    public ObservableList<TableValue<String, Type>> getTableValues() {
        return null;
    }
}

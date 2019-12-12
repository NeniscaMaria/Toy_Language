package Domain;

import Domain.ProgramState.SymbolTable;
import Exceptions.MyException;
import Interfaces.MyDictionaryInterface;
import Interfaces.Type;
import Interfaces.Value;

import java.util.HashMap;

public class TypeChecker implements MyDictionaryInterface<String , Type> {
    private HashMap<String, Type> table;
    public TypeChecker(){
        table=new HashMap<String,Type>();
    }
    public TypeChecker(HashMap<String,Type> typeEnvironment){
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
        StringBuilder result= new StringBuilder("Symbol table:"+System.lineSeparator());
        for(String key:table.keySet())
            result.append(key).append("-->").append(table.get(key).toString()).append(System.lineSeparator());
        return result.toString();
    }
    public HashMap<String,Type> getContent(){
        return table;
    }
    public MyDictionaryInterface<String,Type> clone(){
        HashMap<String,Type> clone = new HashMap<String,Type>();
        for(String key : table.keySet())
            clone.put(key, table.get(key));
        return new TypeChecker(clone);
    }
}

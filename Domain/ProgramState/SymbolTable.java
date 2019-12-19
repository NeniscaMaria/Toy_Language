package Domain.ProgramState;
import Exceptions.MyException;
import Interfaces.MyDictionaryInterface;
import Interfaces.Value;
import java.util.concurrent.ConcurrentHashMap;


public class SymbolTable implements MyDictionaryInterface<String,Value>{
    private ConcurrentHashMap<String,Value> table;
    public SymbolTable(){
        table=new ConcurrentHashMap<>();
    }
    private SymbolTable(ConcurrentHashMap<String,Value> valuesFromUser){
        table=valuesFromUser;
    }
    public boolean isDefined(String id){
        return table.containsKey(id);
    }
    public Value getValue(String id){
        return table.get(id);
    }
    public void update(String id,Value val){
        table.put(id,val);
    }
    public Value lookup(String id){
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
    public ConcurrentHashMap<String,Value> getContent(){
        return table;
    }
    public MyDictionaryInterface<String,Value> clone(){
        ConcurrentHashMap<String,Value> clone = new ConcurrentHashMap<>();
        for(String key : table.keySet())
            clone.put(key, table.get(key));
        return new SymbolTable(clone);
    }
}

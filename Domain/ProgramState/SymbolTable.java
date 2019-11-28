package Domain.ProgramState;
import Domain.Types.ReferenceType;
import Domain.Values.ReferenceValue;
import Exceptions.MyException;
import Interfaces.MyDictionaryInterface;
import Interfaces.Type;
import Interfaces.Value;
import java.util.HashMap;
import java.util.stream.Collectors;


public class SymbolTable implements MyDictionaryInterface<String,Value>{
    private HashMap<String,Value> table;
    public SymbolTable(){
        table=new HashMap<String,Value>();
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
    public HashMap<String,Value> getContent(){
        return table;
    }
}

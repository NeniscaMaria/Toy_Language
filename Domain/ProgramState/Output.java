package Domain.ProgramState;
import Interfaces.MyListInterface;
import Interfaces.Value;
import java.util.ArrayList;

public class Output implements MyListInterface<Value> {
    private ArrayList<Value> list;
    public Output(){
        list=new ArrayList<Value>();
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

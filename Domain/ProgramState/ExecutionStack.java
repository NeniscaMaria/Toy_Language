package Domain.ProgramState;
import Interfaces.MyStackInterface;
import Interfaces.StatementInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Stack;

public class ExecutionStack implements MyStackInterface<StatementInterface> {
    private Stack<StatementInterface> stack;
    public ExecutionStack(){
        stack=new Stack<StatementInterface>();
    }
    public StatementInterface pop(){
        return stack.pop();
    }
    public void push(StatementInterface v){
        stack.push(v);
    }
    public Stack<StatementInterface> getStack(){
        return stack;
    }
    public boolean isEmpty(){
        return stack.isEmpty();
    }
    @Override
    public String toString(){
        StringBuilder result= new StringBuilder("Execution stack:"+System.lineSeparator());
        Stack<StatementInterface> copyOfStack=(Stack<StatementInterface>)stack.clone();
        while(!copyOfStack.isEmpty()){
            result.append(copyOfStack.pop().toString()).append(System.lineSeparator());
        }
        return result.toString();
    }
    public ObservableList<String> getExecutionStackItems(){
        ObservableList<String> list = FXCollections.observableArrayList();
        Stack<StatementInterface> copyOfStack=(Stack<StatementInterface>)stack.clone();
        while(!copyOfStack.isEmpty()){
            list.add(copyOfStack.pop().toString());
        }
        return list;
    }
}

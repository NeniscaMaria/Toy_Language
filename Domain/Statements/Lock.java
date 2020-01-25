package Domain.Statements;

import Domain.ProgramState.ProgramState;
import Domain.Types.IntType;
import Domain.Values.IntValue;
import Exceptions.MyException;
import Interfaces.MyDictionaryInterface;
import Interfaces.StatementInterface;
import Interfaces.Type;
import Interfaces.Value;

public class Lock implements StatementInterface {
    private String variableName;
    public Lock(String variableFromUser){
        variableName=variableFromUser;
    }
    public ProgramState execute(ProgramState state){
        Value foundIndex = state.getSymbolTable().lookup(variableName);
        IntValue valueOfIndex = (IntValue)foundIndex;
        int index = valueOfIndex.getValue();
        if(state.getLockTable().isAddress(index)){
            int valueInLockTable = state.getLockTable().lookUp(index);
            if(valueInLockTable==-1)
                state.getLockTable().update(index,state.getID().intValue());
            else
                state.getStack().push(this);
        }else
            throw new MyException(Integer.toString(index)+" is not and index in the lockTable");
        return null;
    }
   public  MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String,Type> typeEnvironment){
       Type foundIndex = typeEnvironment.lookup(variableName);
       if(foundIndex.equals(new IntType())){
           return typeEnvironment;
       }
       else
           throw new MyException(variableName+" is not of IntType");
   }
   @Override
   public String toString(){
       return "lock("+variableName+")";
   }
    public String getText(){
        return System.lineSeparator()+"lock("+variableName+")";
    }
}

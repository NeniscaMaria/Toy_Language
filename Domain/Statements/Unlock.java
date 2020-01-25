package Domain.Statements;

import Domain.ProgramState.ProgramState;
import Domain.Types.IntType;
import Domain.Values.IntValue;
import Exceptions.MyException;
import Interfaces.MyDictionaryInterface;
import Interfaces.StatementInterface;
import Interfaces.Type;
import Interfaces.Value;

public class Unlock implements StatementInterface {
    private String variableName;
    public Unlock(String variableFromUser){
        variableName=variableFromUser;
    }
    public ProgramState execute(ProgramState state){
        Value foundIndex = state.getSymbolTable().lookup(variableName);
        System.out.println(state.getLockTable()+foundIndex.toString());
        IntValue valueOfIndex = (IntValue)foundIndex;
        int index = valueOfIndex.getValue();
        if(state.getLockTable().isAddress(index)){
            int valueInLockTable = state.getLockTable().lookUp(index);
            if(valueInLockTable==state.getID().intValue())
                state.getLockTable().update(index,-1);
        }else
            throw new MyException(Integer.toString(index)+" is not and index in the lockTable");
        return null;
    }
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String,Type> typeEnvironment){
        Type foundIndex = typeEnvironment.lookup(variableName);
        if(foundIndex.equals(new IntType())){
            return typeEnvironment;
        }
        else
            throw new MyException(variableName+" is not of IntType");
    }
    @Override
    public String toString(){
        return "unlock("+variableName+")";
    }
    public String getText(){
        return System.lineSeparator()+"unlock("+variableName+")";
    }
}

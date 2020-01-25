package Domain.Statements;

import Domain.ProgramState.ProgramState;
import Domain.Types.IntType;
import Domain.Values.IntValue;
import Exceptions.MyException;
import Interfaces.MyDictionaryInterface;
import Interfaces.StatementInterface;
import Interfaces.Type;
import Interfaces.Value;

public class NewLock implements StatementInterface {
    private String variableName;
    public NewLock(String variableFromUser){
        variableName=variableFromUser;
    }
    public ProgramState execute(ProgramState state){
        int address = state.getLockTable().add(-1);
        state.getSymbolTable().update(variableName,new IntValue(address));
        return null;
    }
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String,Type> typeEnvironment){
        Type type = typeEnvironment.lookup(variableName);
        if(type.equals(new IntType()))
            return typeEnvironment;
        else
            throw new MyException(variableName+" is not of IntType.");
    }
    @Override
    public String toString(){
        return  "newLock("+variableName+")";
    }
    public String getText(){
        return System.lineSeparator()+"newLock("+variableName+")";
    }
}

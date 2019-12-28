package Domain.Statements;

import Domain.ProgramState.ProgramState;
import Domain.Types.ReferenceType;
import Domain.Values.ReferenceValue;
import Exceptions.MyException;
import Interfaces.*;

public class HeapAllocation implements StatementInterface {
    private String variableName;
    private ExpressionInterface expression;
    public HeapAllocation(String variableNameFromUser, ExpressionInterface expressionFromUser){
        variableName=variableNameFromUser;
        expression=expressionFromUser;
    }
    @Override
    public String toString(){
        return "new("+variableName+","+expression.toString()+")";
    }
    public String getText(){
        return System.lineSeparator()+"new("+variableName+","+expression.toString()+")";
    }
    public ProgramState execute(ProgramState state){
        MyDictionaryInterface<String, Value> table = state.getSymbolTable();
        if(table.isDefined(variableName)) {
            Value valueOfVariable = table.lookup(variableName);
            Value value = expression.evaluate(table, state.getHeap());
            ReferenceValue valueOfVar = (ReferenceValue) valueOfVariable;
            HeapInterface heap = state.getHeap();
            int address = heap.add(value);
                    /*in SymbolTable update the ReferenceValue associated to the variableName such that the new ReferenceValue
                    has the same locationType as before and the address is equal to the new key generated in the Heap at
                    the previous step*/
            ReferenceValue newValue = new ReferenceValue(address, valueOfVar.getLocationType());
            table.update(variableName, newValue);
        }else
            throw new MyException(variableName+" is not defined");
        return null;
    }
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String,Type> typeEnvironment){
        Type typeVariable=typeEnvironment.lookup(variableName);
        Type typeExpression=expression.typecheck(typeEnvironment);
        if(typeVariable.equals(new ReferenceType(typeExpression)))
            return typeEnvironment;
        else
            throw new MyException(this.toString()+": right hand side and left hand side have different types");
    }
}

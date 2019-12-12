package Domain.Statements;

import Domain.ProgramState.ProgramState;
import Domain.Types.ReferenceType;
import Domain.Values.ReferenceValue;
import Exceptions.MyException;
import Interfaces.*;
import org.w3c.dom.events.MutationEvent;

public class WriteHeap implements StatementInterface {
    private String variableName; //-->contains the heap address
    private ExpressionInterface expression;//-->the new value to be stored in the new heap address
    public WriteHeap(String variableNameFromUser,ExpressionInterface expressionfromUser){
        variableName=variableNameFromUser;
        expression=expressionfromUser;
    }
    @Override
    public String toString(){
        return "wH("+variableName+","+expression.toString()+")";
    }
    public ProgramState execute(ProgramState state){
        MyDictionaryInterface<String, Value> symbolTable=state.getSymbolTable();
        HeapInterface<Integer,Value> heap=state.getHeap();
        if(symbolTable.isDefined(variableName)) {
            Value valueOfVariable = symbolTable.lookup(variableName);
            Value valueOfExpression = expression.evaluate(symbolTable, heap);
            ReferenceValue value = (ReferenceValue) valueOfVariable;
            int address = value.getAddress();
            if (heap.isAddress(address)) {
                if (valueOfExpression.getType().equals(value.getLocationType())) {
                    heap.update(address, valueOfExpression);
                } else
                    throw new MyException(expression.toString() + " not the same type as the location pointed to by " + variableName);
            } else
                throw new MyException(variableName + " does not have a valid address");
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
            throw new MyException(this.toString()+": right hand side and left hand side of have different types");
    }
}
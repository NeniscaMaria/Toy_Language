package Domain.Expressions;

import Domain.Types.ReferenceType;
import Domain.Values.ReferenceValue;
import Exceptions.MyException;
import Interfaces.*;


public class ReadHeap implements ExpressionInterface {
    private ExpressionInterface expression;
    public ReadHeap(ExpressionInterface expressionFromUser){
        expression=expressionFromUser;
    }
    @Override
    public  String toString(){
        return "rH("+expression.toString()+")";
    }
    public Value evaluate(MyDictionaryInterface<String,Value> table, HeapInterface<Integer,Value> heap){
        Value value = expression.evaluate(table,heap);
        if(value instanceof ReferenceValue){
            ReferenceValue valueOfExpression = (ReferenceValue)value;
            int address=valueOfExpression.getAddress();
            Value valueFromHeap=heap.getValue(address); //the getValue will throw a runtime exception if the address does not exist, so we needn't worry about that here
            return valueFromHeap;
        }else
            throw new MyException(expression.toString()+" does not evaluate as a ReferenceValue");
    }
    public Type typecheck (MyDictionaryInterface<String,Type> typeEnvironment){
        Type type=expression.typecheck(typeEnvironment);
        if(type instanceof ReferenceType){
            ReferenceType refType=(ReferenceType)type;
            return refType.getInner();
        }else
            throw new MyException(expression.toString()+" does not evaluate as ReferenceType");
    }
}
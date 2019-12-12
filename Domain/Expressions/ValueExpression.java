package Domain.Expressions;
import Interfaces.*;

public class ValueExpression implements ExpressionInterface{
    private Value e;
    public ValueExpression(Value valueFromUser){e=valueFromUser;}
    public Value evaluate(MyDictionaryInterface<String,Value>table, HeapInterface<Integer,Value> heap){
        return e;
    }
    @Override
    public String toString(){
        return e.toString();
    }
    public Type typecheck(MyDictionaryInterface<String,Type> typeEnvironment){
        return e.getType();
    }
}

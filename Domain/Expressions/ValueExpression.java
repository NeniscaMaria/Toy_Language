package Domain.Expressions;
import Interfaces.ExpressionInterface;
import Interfaces.HeapInterface;
import Interfaces.Value;
import Interfaces.MyDictionaryInterface;

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
}

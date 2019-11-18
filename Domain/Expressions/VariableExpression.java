package Domain.Expressions;
import Interfaces.*;
import Interfaces.MyDictionaryInterface;

public class VariableExpression implements ExpressionInterface{
    private String id;
    public VariableExpression(String idFromUser){
        id=idFromUser;
    }
    public Value evaluate(MyDictionaryInterface<String,Value> table){
        return table.lookup(id);
    }
    @Override
    public String toString(){
        return id;
    }
}

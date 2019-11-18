package Domain.Expressions;
import Domain.Types.IntType;
import Domain.Values.BoolValue;
import Domain.Values.IntValue;
import Exceptions.MyException;
import Interfaces.ExpressionInterface;
import Interfaces.MyDictionaryInterface;
import Interfaces.Value;

public class RelationalExpression implements ExpressionInterface {
    private ExpressionInterface expression1;
    private ExpressionInterface expression2;
    private String relation;
    public RelationalExpression(ExpressionInterface expression1FromUser,ExpressionInterface expression2fromUser,String relationFromUser){
        expression1=expression1FromUser;
        expression2=expression2fromUser;
        relation=relationFromUser;
    }
    @Override
    public String toString(){
        return expression1.toString()+relation+expression2.toString();
    }
    public Value evaluate(MyDictionaryInterface<String,Value> table){
        Value value1 = expression1.evaluate(table);
        if(value1.getType().equals(new IntType())){
            IntValue v1 = (IntValue) value1;
            Value value2 = expression2.evaluate(table);
            if(value2.getType().equals(new IntType())){
                IntValue v2 = (IntValue) value2;
                switch(relation){
                    case "<":
                        return new BoolValue(v1.getValue()<v2.getValue());
                    case "<=":
                        return new BoolValue(v1.getValue()<=v2.getValue());
                    case "==":
                        return new BoolValue(v1.getValue()==v2.getValue());
                    case "!=":
                        return new BoolValue(v1.getValue()!=v2.getValue());
                    case ">":
                        return new BoolValue(v1.getValue()>v2.getValue());
                    case ">=":
                        return new BoolValue(v1.getValue()>=v2.getValue());
                    default:
                        throw new MyException(relation+" is not a supported relation in "+toString());
                }
            }else
                throw new MyException(toString()+": Second expression does not evaluate as an int expression");
        }else
            throw new MyException(toString()+": First expression does not evaluate as an int expression");
    }
}

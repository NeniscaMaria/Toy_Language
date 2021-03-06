package Domain.Expressions;
import Domain.Types.BoolType;
import Domain.Types.IntType;
import Domain.Values.BoolValue;
import Domain.Values.IntValue;
import Exceptions.MyException;
import Interfaces.*;

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
    public Value evaluate(MyDictionaryInterface<String,Value> table, HeapInterface<Integer,Value> heap){
        Value value1 = expression1.evaluate(table,heap);
        IntValue v1 = (IntValue) value1;
        Value value2 = expression2.evaluate(table,heap);
        IntValue v2 = (IntValue) value2;
        switch(relation) {
            case "<":
                return new BoolValue(v1.getValue() < v2.getValue());
            case "<=":
                return new BoolValue(v1.getValue() <= v2.getValue());
            case "==":
                return new BoolValue(v1.getValue() == v2.getValue());
            case "!=":
                return new BoolValue(v1.getValue() != v2.getValue());
            case ">":
                return new BoolValue(v1.getValue() > v2.getValue());
            case ">=":
                return new BoolValue(v1.getValue() >= v2.getValue());
            default:
                throw new MyException(relation + " is not a supported relation in " + toString());
        }
    }
    public Type typecheck (MyDictionaryInterface<String,Type> typeEnvironment){
        Type type1,type2;
        type1=expression1.typecheck(typeEnvironment);
        type2=expression2.typecheck(typeEnvironment);
        if(type1.equals(new IntType())){
            if (type2.equals(new IntType()))
                return new BoolType();
            else
                throw new MyException(this.toString()+": second expression does not evaluate as IntType");
        }else
            throw new MyException(this.toString()+": first expression does not evaluate as IntType");
    }
}

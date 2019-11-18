package Domain.Expressions;
import Domain.Types.BoolType;
import Domain.Values.BoolValue;
import Exceptions.MyException;
import Interfaces.ExpressionInterface;
import Interfaces.MyDictionaryInterface;
import Interfaces.Value;

public class LogicExpression {
    private ExpressionInterface expression1;
    private ExpressionInterface expression2;
    private String operation;
    public Value evaluate(MyDictionaryInterface<String,Value> table) throws MyException {
        Value v1,v2;
        v1= expression1.evaluate(table);
        if (v1.getType().equals(new BoolType())) {
            v2 = expression2.evaluate(table);
            if (v2.getType().equals(new BoolType())) {
                BoolValue i1 = (BoolValue) v1;
                BoolValue i2 = (BoolValue)v2;
                boolean n1,n2;
                n1= i1.getValue();
                n2 = i2.getValue();
                if (operation.equals("and")) return new BoolValue(n1&&n2);
                if (operation.equals("or")) return new BoolValue(n1||n2);
            }else
                throw new MyException("second operand is not a boolean");
        }else throw new MyException("first operand is not a boolean");
        return new BoolValue(false);
    }
    @Override
    public String toString(){
        return expression1.toString()+" "+operation+" "+expression2.toString();
    }
}

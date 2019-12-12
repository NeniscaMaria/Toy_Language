package Domain.Expressions;
import Domain.Types.BoolType;
import Domain.Types.IntType;
import Domain.Values.BoolValue;
import Exceptions.MyException;
import Interfaces.*;

public class LogicExpression {
    private ExpressionInterface expression1;
    private ExpressionInterface expression2;
    private String operation;
    public Value evaluate(MyDictionaryInterface<String,Value> table, HeapInterface<Integer,Value> heap) throws MyException {
        Value v1,v2;
        v1= expression1.evaluate(table,heap);
        v2 = expression2.evaluate(table,heap);
        BoolValue i1 = (BoolValue) v1;
        BoolValue i2 = (BoolValue)v2;
        boolean n1,n2;
        n1= i1.getValue();
        n2 = i2.getValue();
        if (operation.equals("and")) return new BoolValue(n1&&n2);
        if (operation.equals("or")) return new BoolValue(n1||n2);
        return new BoolValue(false);
    }
    @Override
    public String toString(){
        return expression1.toString()+" "+operation+" "+expression2.toString();
    }
    public Type typecheck (MyDictionaryInterface<String,Type> typeEnvironment){
        Type type1,type2;
        type1=expression1.typecheck(typeEnvironment);
        type2=expression2.typecheck(typeEnvironment);
        if(type1.equals(new BoolType())){
            if (type2.equals(new BoolType()))
                return new IntType();
            else
                throw new MyException(this.toString()+": second expression does not evaluate as BoolType");
        }else
            throw new MyException(this.toString()+": first expression does not evaluate as BoolType");
    }
}

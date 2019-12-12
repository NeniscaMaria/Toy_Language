package Domain.Expressions;
import Domain.Values.BoolValue;
import Interfaces.*;
import Domain.Types.IntType;
import Domain.Values.IntValue;
import Exceptions.MyException;

public class ArithmeticExpression implements ExpressionInterface{
    private ExpressionInterface expression1;
    private ExpressionInterface expression2;
    private int operation;//1-plus, 2-minus,3-multiplication,4-divide
    //override
    public ArithmeticExpression(String operationFromUser,ExpressionInterface expression1FromUser, ExpressionInterface expression2FromUser){
        expression1=expression1FromUser;
        expression2=expression2FromUser;
        switch(operationFromUser) {
            case "+":
                operation = 1;
                break;
            case "-":
                operation = 2;
                break;
            case "*":
                operation = 3;
                break;
            case "/":
                operation = 4;
                break;
            default:
                throw new MyException("Invalid operation in ArthmeticExpression constructor\n");
        }
    }
    public Value evaluate(MyDictionaryInterface<String,Value> table, HeapInterface<Integer,Value> heap) {
        Value v1,v2;
        v1= expression1.evaluate(table,heap);
        if (v1.getType().equals(new IntType())) {
            v2 = expression2.evaluate(table,heap);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue)v1;
                IntValue i2 = (IntValue)v2;
                int n1,n2;
                n1= i1.getValue();
                n2 = i2.getValue();
                switch(operation){
                    case 1:
                        return new IntValue(n1+n2);
                    case 2:
                        return new IntValue(n1-n2);
                    case 3:
                        return new IntValue(n1*n2);
                    case 4:
                        if(n2==0)
                            throw new MyException("division by zero");
                        else
                            return new IntValue(n1/n2);
                    default: throw new MyException("Wrong operation");
                }
            }else
                throw new MyException("second operand is not an integer");
        }else
            throw new MyException("first operand is not an integer");
    }
    @Override
    public String toString(){
        String result=expression1.toString();
        switch(operation) {
            case 1:
                result += " + ";
                break;
            case 2:
                result += " - ";
                break;
            case 3:
                result += " * ";
                break;
            default:
                result += " / ";
        }
        result+=expression2.toString();
        return result;
    }
    public Type typecheck (MyDictionaryInterface<String,Type> typeEnvironment){
        Type type1,type2;
        type1=expression1.typecheck(typeEnvironment);
        type2=expression2.typecheck(typeEnvironment);
        if(type1.equals(new IntType())){
            if (type2.equals(new IntType()))
                return new IntType();
            else
                throw new MyException(this.toString()+": second expression does not evaluate as IntType");
        }else
            throw new MyException(this.toString()+": first expression does not evaluate as IntType");
    }
}


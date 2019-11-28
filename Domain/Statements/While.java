package Domain.Statements;

import Domain.ProgramState.ProgramState;
import Domain.Types.BoolType;
import Domain.Values.BoolValue;
import Exceptions.MyException;
import Interfaces.*;

public class While implements StatementInterface {
    private ExpressionInterface condition;
    private StatementInterface statement;
    public While(ExpressionInterface conditionFromUser, StatementInterface statementFromUser){
        condition=conditionFromUser;
        statement=statementFromUser;
    }
    @Override
    public String toString(){
        return "while("+condition.toString()+")"+System.lineSeparator()+statement.toString()+System.lineSeparator();
    }
    public ProgramState execute(ProgramState state){
        MyDictionaryInterface<String,Value> table=state.getSymbolTable();
        HeapInterface heap =state.getHeap();
        MyStackInterface<StatementInterface> stack=state.getStack();
        Value val = condition.evaluate(table,heap);
        if(val.getType().equals(new BoolType())){
            BoolValue cond = (BoolValue)val;
            if(!cond.getValue()){
                return state;
            }else{
                stack.push(this);
                stack.push(statement);
            }
        }else
            throw new MyException(condition+" does not evaluate as boolean");
        return null;
    }
}

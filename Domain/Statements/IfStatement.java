package Domain.Statements;
import Domain.ProgramState.ProgramState;
import Domain.Types.BoolType;
import Domain.Values.BoolValue;
import Exceptions.MyException;
import Interfaces.MyStackInterface;
import Interfaces.StatementInterface;
import Interfaces.ExpressionInterface;
import Interfaces.Value;

public class IfStatement implements StatementInterface{
    private ExpressionInterface expression;
    private StatementInterface thenStatement;
    private StatementInterface elseStatement;

    public IfStatement(ExpressionInterface expressionFromUser, StatementInterface thenStatementFromUser, StatementInterface elseStatementFromUser) {
        expression = expressionFromUser;
        thenStatement=thenStatementFromUser;
        elseStatement=elseStatementFromUser;
    }
    @Override
    public String toString(){
        return "IF( "+expression.toString()+" )THEN"+System.lineSeparator()+"\t( "+thenStatement.toString()+" )ELSE"+System.lineSeparator()+"\t( "+elseStatement.toString()+" )";
    }
    public ProgramState execute(ProgramState state){
        MyStackInterface<StatementInterface> stack = state.getStack();
        Value condition = expression.evaluate(state.getSymbolTable());
        if(!condition.getType().equals(new BoolType())){
            throw new MyException("Conditional expression is not a boolean");
        }else{
            if(condition.isEqual(new BoolValue(true)))
                stack.push(thenStatement);
            else
                stack.push(elseStatement);
        }
        return state;
    }
}

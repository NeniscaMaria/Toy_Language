package Domain.Statements;
import Domain.ProgramState.ProgramState;
import Domain.Types.BoolType;
import Domain.Values.BoolValue;
import Exceptions.MyException;
import Interfaces.*;

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
        Value condition = expression.evaluate(state.getSymbolTable(),state.getHeap());
        if(condition.isEqual(new BoolValue(true)))
            stack.push(thenStatement);
        else
            stack.push(elseStatement);
        return null;
    }
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String,Type> typeEnvironment){
        Type typeExpression=expression.typecheck(typeEnvironment);
        if(typeExpression.equals(new BoolType())){
            thenStatement.typecheck(typeEnvironment.clone());
            elseStatement.typecheck(typeEnvironment.clone());
            return typeEnvironment;
        }else
            throw new MyException("The condition "+expression.toString()+" of IF does not have BoolType");
    }
}

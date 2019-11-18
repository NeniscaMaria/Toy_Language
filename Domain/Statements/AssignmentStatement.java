package Domain.Statements;
import Domain.ProgramState.ProgramState;
import Interfaces.StatementInterface;
import Interfaces.ExpressionInterface;
import Interfaces.Value;
import Interfaces.Type;
import Exceptions.MyException;
import Interfaces.MyDictionaryInterface;

public class AssignmentStatement implements StatementInterface{
    private String id;
    private ExpressionInterface expression;
    public AssignmentStatement(String idFromUser,ExpressionInterface expressionFromUser) {
        id = idFromUser;
        expression = expressionFromUser;
    }
    @Override
    public String toString(){
        return id+" = "+expression.toString();
    }
    public ProgramState execute(ProgramState state){
        MyDictionaryInterface<String,Value> symbolsTable = state.getSymbolTable();
        if (symbolsTable.isDefined(id)){
            Value val = expression.evaluate(symbolsTable);
            Type typeID = (symbolsTable.getValue(id)).getType();
            if (val.getType().equals(typeID))
                symbolsTable.update(id, val);
            else
                throw new MyException("declared type of variable " + id + " and type of the assigned expression do not match");
        }else
            throw new MyException("the used variable "+id+" was not declared before");
        return state;
        }
}

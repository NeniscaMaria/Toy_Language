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
            Value val = expression.evaluate(symbolsTable,state.getHeap());
            symbolsTable.update(id, val);
        }else
            throw new MyException("the used variable "+id+" was not declared before");
        return null;
    }
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String,Type> typeEnvironment){
        Type typeVariable=typeEnvironment.lookup(id);
        Type typeExpression=expression.typecheck(typeEnvironment);
        if(typeVariable.equals(typeExpression))
            return typeEnvironment;
        else
            throw new MyException(this.toString()+": right hand side and left hand side of the assignment have different types");
    }
}


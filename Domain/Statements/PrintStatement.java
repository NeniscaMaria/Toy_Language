package Domain.Statements;
import Domain.ProgramState.ProgramState;
import Interfaces.*;
import org.jetbrains.annotations.NotNull;

public class PrintStatement implements StatementInterface{
    private ExpressionInterface exp;
    public PrintStatement(ExpressionInterface expFromUser){exp=expFromUser;}
    @Override
    public String toString() {
        return "print("+exp.toString()+")";
    }
    public ProgramState execute(@NotNull ProgramState state){
        MyListInterface<Value> output = state.getOutput();
        output.add(exp.evaluate(state.getSymbolTable(),state.getHeap()));
        return null;
    }
}

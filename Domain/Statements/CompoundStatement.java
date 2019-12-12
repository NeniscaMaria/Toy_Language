package Domain.Statements;
import Domain.ProgramState.ProgramState;
import Interfaces.MyDictionaryInterface;
import Interfaces.StatementInterface;
import Interfaces.Type;
import org.jetbrains.annotations.NotNull;
import Interfaces.MyStackInterface;

public class CompoundStatement implements StatementInterface{
    private StatementInterface first;
    private StatementInterface second;
    public CompoundStatement(StatementInterface firstFromUser,StatementInterface secondFromUser){
        first=firstFromUser;
        second=secondFromUser;
    }


    @Override
    public String toString(){
        return "("+first.toString()+";"+second.toString()+")";
    }

    public ProgramState execute(@NotNull ProgramState state){
        MyStackInterface<StatementInterface> stack = state.getStack();
        stack.push(second);
        stack.push(first);
        return null;
    }
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String,Type> typeEnvironment){
        return second.typecheck(first.typecheck(typeEnvironment));
    }
}

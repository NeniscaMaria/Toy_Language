package Domain.Statements;
import Domain.ProgramState.ProgramState;
import Interfaces.MyDictionaryInterface;
import Interfaces.StatementInterface;
import Interfaces.Type;

public class NoOperationStatement implements StatementInterface{
    public ProgramState execute(ProgramState state){
        return null;
    }
    @Override
    public String toString(){
        return "No Operation";
    }
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String,Type> typeEnvironment){
        return typeEnvironment;
    }
}

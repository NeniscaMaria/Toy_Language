package Domain.Statements;
import Domain.ProgramState.ProgramState;
import Interfaces.MyDictionaryInterface;
import Interfaces.StatementInterface;
import Interfaces.Type;

public class NoOperationStatement implements StatementInterface{
    private boolean toPrint;
    public NoOperationStatement(boolean b){
        toPrint=b;
    }
    public ProgramState execute(ProgramState state){
        return null;
    }
    @Override
    public String toString(){
        if (toPrint)
            return "No Operation";
        else
            return "";
    }
    public String getText(){
        if(toPrint)
            return System.lineSeparator()+"No Operation";
        else
            return "";
    }
    public MyDictionaryInterface<String, Type> typecheck(MyDictionaryInterface<String,Type> typeEnvironment){
        return typeEnvironment;
    }
}

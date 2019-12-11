package Domain.Statements;
import Domain.ProgramState.ProgramState;
import Interfaces.StatementInterface;

public class NoOperationStatement implements StatementInterface{
    public ProgramState execute(ProgramState state){
        return null;
    }
    @Override
    public String toString(){
        return "No Operation";
    }
}

package Domain.Statements;

import Domain.ProgramState.ExecutionStack;
import Domain.ProgramState.ProgramState;
import Interfaces.MyStackInterface;
import Interfaces.StatementInterface;

public class ForkStatement implements StatementInterface {
    private StatementInterface statement;
    public ForkStatement(StatementInterface statementFromUser){
        statement=statementFromUser;
    }
    @Override
    public String toString(){
        return "fork("+statement+")";
    }
    public ProgramState execute(ProgramState state){
        MyStackInterface<StatementInterface> newExecutionStack=new ExecutionStack();
        ProgramState newProgramState = new ProgramState(newExecutionStack, state.getSymbolTable().clone(),state.getOutput(),
                    statement,state.getFileTable(),state.getHeap(),state.generateNewID());
        return newProgramState;

    }

}

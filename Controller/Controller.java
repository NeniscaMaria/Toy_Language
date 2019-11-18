package Controller;

import Domain.ProgramState.ProgramState;
import Exceptions.MyException;
import Interfaces.ControllerInterface;
import Interfaces.MyStackInterface;
import Interfaces.RepositoryInterface;
import Interfaces.StatementInterface;

import java.io.IOException;

public class Controller implements ControllerInterface {
    private RepositoryInterface repository;
    private boolean display;
    public Controller(RepositoryInterface repoFromUser){
        repository=repoFromUser;
        display=false;
    }
    public Controller(RepositoryInterface repositoryFromUser,boolean displayFromUSer){
        repository=repositoryFromUser;
        display=displayFromUSer;
    }
    public void oneStepExecution(ProgramState state) throws MyException, IOException {
        MyStackInterface<StatementInterface> stack = state.getStack();
        if(stack.isEmpty())
            throw new MyException("Execution stack is empty!");
        StatementInterface currentStatement=stack.pop();
        currentStatement.execute(state);
        repository.logProgramStateExecution();
    }
    public void allStepExecution() throws IOException {
        ProgramState program = repository.getCurrentProgramState();
        repository.logProgramStateExecution();
        if(display)
            displayCurrentState();
        while(!program.getStack().isEmpty()){
            oneStepExecution(program);
            repository.logProgramStateExecution();
            if(display)
                displayCurrentState();
        }
    }
    public void displayCurrentState(){
            System.out.println(repository.getCurrentProgramState());
    }
}

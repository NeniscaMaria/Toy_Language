package Repository;
import Domain.ProgramState.ProgramState;
import Exceptions.MyException;
import Interfaces.RepositoryInterface;
import java.io.*;
import java.util.*;

public class Repository implements RepositoryInterface {
    private List<ProgramState> states;
    private String logFilePath;
    public Repository(ProgramState stateFromUser,String logFilePathFromUser ) {
        ArrayList<ProgramState> s= new ArrayList<ProgramState>();
        s.add(stateFromUser);
        logFilePath=logFilePathFromUser;
        states=s;
    }
    public void typecheck(){
        for(ProgramState state: states)
            state.typecheck();

    }
    public List<ProgramState> getProgramsList(){
        return states;
    }
    public void setProgramsList(List<ProgramState> listFromUser){
        states=listFromUser;
    }
    public ProgramState getCurrentProgramState(){
        return states.get(states.size()-1);
    }
    public void logProgramStateExecution(ProgramState state){
        //logs the execution of a single program state (given by the user)
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)))) {
            logFile.println(state);
            logFile.println(System.lineSeparator());

        } catch (IOException e) {
            throw new MyException(e.getMessage());
        }
    }
    public void logProgramStatesExecution(){
        //logs the execution of all the program states currently existing in the repository
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)))) {
            for (ProgramState state : states) {
                logFile.println("Thread ID " + Integer.toString(state.getID()) + System.lineSeparator() + state.getTypeChecker() +System.lineSeparator() + state.getStack().toString()
                        + state.getSymbolTable().toString());
            }
            logFile.println(states.get(0).getHeap().toString() + states.get(0).getOutput().toString());
        } catch (IOException e) {
            throw new MyException(e.getMessage());
        }
    }
}

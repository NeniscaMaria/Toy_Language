package Repository;
import Domain.ProgramState.ProgramState;
import Exceptions.MyException;
import Interfaces.RepositoryInterface;

import java.io.*;
import java.nio.Buffer;
import java.util.*;

public class Repository implements RepositoryInterface {
    private List<ProgramState> states;
    String logFilePath;
    public Repository(ProgramState stateFromUser,String logFilePathFromUser ) {
        ArrayList<ProgramState> s= new ArrayList<ProgramState>();
        s.add(stateFromUser);
        logFilePath=logFilePathFromUser;
        states=s;
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
        PrintWriter logFile=null;
        try {
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            logFile.println(state);
            logFile.println(System.lineSeparator());

        }catch(IOException e){
            System.err.println(e.getMessage());
        }finally{
            if(logFile!=null)
                logFile.close();
        }
    }
    public void logProgramStatesExecution(){
        PrintWriter logFile=null;
        try {
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            for(ProgramState state: states){
                logFile.println("Thread ID "+Integer.toString(state.getID())+System.lineSeparator()+state.getStack().toString()
                        +state.getSymbolTable().toString());
            }
            logFile.println(states.get(0).getHeap().toString()+states.get(0).getOutput().toString());
        }catch(IOException e){
            System.err.println(e.getMessage());
        }finally{
            if(logFile!=null)
                logFile.close();
        }
    }
}

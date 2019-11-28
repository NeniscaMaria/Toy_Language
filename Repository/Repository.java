package Repository;
import Domain.ProgramState.ProgramState;
import Exceptions.MyException;
import Interfaces.RepositoryInterface;

import java.io.*;
import java.nio.Buffer;
import java.util.*;

public class Repository implements RepositoryInterface {
    private ArrayList<ProgramState> states;
    String logFilePath;
    public Repository(ArrayList<ProgramState> stateFromUser,String logFilePathFromUser){
        states=stateFromUser;
        logFilePath=logFilePathFromUser;
    }
    public Repository(ProgramState stateFromUser,String logFilePathFromUser ) {
        ArrayList<ProgramState> s= new ArrayList<ProgramState>();
        s.add(stateFromUser);
        logFilePath=logFilePathFromUser;
        states=s;
    }
    public ArrayList<ProgramState> getProgramsList(){
        return states;
    }
    public void setProgramsList(ArrayList<ProgramState> listFromUser){
        states=listFromUser;
    }
    public ProgramState getCurrentProgramState(){
        return states.get(states.size()-1);
    }
    public void logProgramStateExecution(ProgramState state) throws IOException {
        PrintWriter logFile=null;
        try {
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            logFile.println(state);
            logFile.println(System.lineSeparator());

        }catch(FileNotFoundException e){
            System.err.println(e.getMessage());
        }finally{
            if(logFile!=null)
                logFile.close();
        }
    }
}

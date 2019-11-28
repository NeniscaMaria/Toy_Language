package Interfaces;

import Domain.ProgramState.ProgramState;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public interface RepositoryInterface {
    ProgramState getCurrentProgramState();
    void logProgramStateExecution(ProgramState state) throws IOException;
    ArrayList<ProgramState> getProgramsList();
    void setProgramsList(ArrayList<ProgramState> listFromUser);
}

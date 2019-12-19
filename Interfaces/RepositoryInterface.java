package Interfaces;

import Domain.ProgramState.ProgramState;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public interface RepositoryInterface {
    ProgramState getCurrentProgramState();
    void logProgramStateExecution(ProgramState state);
    List<ProgramState> getProgramsList();
    void setProgramsList(List<ProgramState> listFromUser);
    void logProgramStatesExecution();
    void typecheck();
}

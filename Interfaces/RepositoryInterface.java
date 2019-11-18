package Interfaces;

import Domain.ProgramState.ProgramState;

import java.io.IOException;

public interface RepositoryInterface {
    ProgramState getCurrentProgramState();
    void logProgramStateExecution() throws IOException;
}

package Interfaces;

import Domain.ProgramState.ProgramState;
import Exceptions.MyException;

import java.io.IOException;

public interface ControllerInterface {
    void oneStepExecution(ProgramState state) throws MyException, IOException;
    void allStepExecution() throws IOException;
    void displayCurrentState();
}

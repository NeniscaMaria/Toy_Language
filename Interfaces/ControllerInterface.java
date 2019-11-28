package Interfaces;

import Domain.ProgramState.ProgramState;
import Exceptions.MyException;

import java.io.IOException;

public interface ControllerInterface {
    void allStepExecution() throws IOException;
    void displayCurrentState();
}

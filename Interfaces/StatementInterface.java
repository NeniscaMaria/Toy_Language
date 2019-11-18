package Interfaces;
import Domain.ProgramState.ProgramState;

import java.io.FileNotFoundException;

public interface StatementInterface {
    public ProgramState execute(ProgramState state);
}

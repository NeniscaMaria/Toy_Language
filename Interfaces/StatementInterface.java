package Interfaces;
import Domain.ProgramState.ProgramState;

import java.io.FileNotFoundException;

public interface StatementInterface {
    ProgramState execute(ProgramState state);
}

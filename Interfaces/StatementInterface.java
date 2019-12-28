package Interfaces;
import Domain.ProgramState.ProgramState;

import java.io.FileNotFoundException;

public interface StatementInterface {
    ProgramState execute(ProgramState state);
    MyDictionaryInterface<String,Type> typecheck(MyDictionaryInterface<String,Type> typeEnvironment);
    String getText();
}

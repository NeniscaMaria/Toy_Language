package Domain.Statements;
import Domain.ProgramState.ProgramState;
import Exceptions.MyException;
import Interfaces.*;

public class VariableDeclarationStatement implements StatementInterface{
    private String name;
    private Type type;
    public VariableDeclarationStatement(String nameFromUser,Type typeFromUser){
        name=nameFromUser;
        type=typeFromUser;
    }
    @Override
    public String toString(){
        return type.toString()+" "+name;
    }
    public ProgramState execute(ProgramState state){
        MyStackInterface<StatementInterface> stack=state.getStack();
        MyDictionaryInterface<String,Value> symbolTable=state.getSymbolTable();
        if(symbolTable.isDefined(name)){
            throw new MyException("Variable "+name+" is already declared!");
        }else{
            symbolTable.update(name,type.getDefaultValue());
        }
        return null;
    }
}
